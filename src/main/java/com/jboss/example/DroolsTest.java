package com.jboss.example;

import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderError;
import org.drools.builder.KnowledgeBuilderErrors;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.io.ResourceFactory;
import org.drools.logger.KnowledgeRuntimeLogger;
import org.drools.logger.KnowledgeRuntimeLoggerFactory;
import org.drools.runtime.StatefulKnowledgeSession;

import static com.jboss.example.TimeSliceClassifiers.*;

/**
 * This is a sample class to launch a rule.
 */
public class DroolsTest {

	public static final void main(String[] args) {
		try {

			// load up the knowledge base
			KnowledgeBase kbase = readKnowledgeBase();
			StatefulKnowledgeSession ksession = kbase.newStatefulKnowledgeSession();
			KnowledgeRuntimeLogger logger = KnowledgeRuntimeLoggerFactory.newFileLogger(ksession, "test");


			// go !
			Employee emp = buildEmployee();
			ksession.insert(emp);
			ksession.insert(emp.getSchedule());
			for (BusinessDay day: emp.getSchedule().getDaysWorked()){
				ksession.insert(day);
				for (TimeSlice slice: day.getSlices()){
					ksession.insert(slice);
				}			
			}
			ksession.fireAllRules();
			logger.close();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	private static KnowledgeBase readKnowledgeBase() throws Exception {
		KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
		kbuilder.add(ResourceFactory.newClassPathResource("com/jboss/example/InsideScheduleRules.drl"), ResourceType.DRL);
		kbuilder.add(ResourceFactory.newClassPathResource("com/jboss/example/OutsideScheduleRules.drl"), ResourceType.DRL);
//		kbuilder.add(ResourceFactory.newClassPathResource("com/jboss/example/Accumulators.drl"),ResourceType.DRL);
		KnowledgeBuilderErrors errors = kbuilder.getErrors();
		if (errors.size() > 0) {
			for (KnowledgeBuilderError error: errors) {
				System.err.println(error);
			}
			throw new IllegalArgumentException("Could not parse knowledge.");
		}
		KnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
		kbase.addKnowledgePackages(kbuilder.getKnowledgePackages());
		return kbase;
	}

	
	public static Employee buildEmployee() {
		
		Employee emp = new Employee();		
		emp.setEmployeeNumber(123);
		WeeklyAccumulator schedule = new WeeklyAccumulator();
		BusinessDay businessDay = new BusinessDay();
		
		schedule.setDailyHours(8);
		schedule.setWeeklyHours(40);
		schedule.addClassifier(ScheduleClassifiers.STANDARD);
		emp.setSchedule(schedule);		
		
		// June 26, 2006 - 5 inside working hours + 3 approved inside absence hours
		businessDay.setDate(2006,06, 26);		
		businessDay.setEmployee(emp);
		
		schedule.addDayWorked(businessDay);	
		
		TimeSlice timeSlice = new TimeSlice();
		timeSlice.addClassifier(WORKED,INSIDE_SCHEDULE);
		timeSlice.setStart(businessDay.getDate());
		timeSlice.setEnd(businessDay.getDate());
		timeSlice.setElapsed(5);
//		timeSlice.setEmployee(emp);
		businessDay.addTimeSlice(timeSlice);
		timeSlice = new TimeSlice();
		timeSlice.addClassifier(ABSENCE,APPROVED, INSIDE_SCHEDULE);
		timeSlice.setElapsed(3);
		businessDay.addTimeSlice(timeSlice);
		
		// June 27, 2006 - 5 inside working hours + 5 outside working hours (2 OT)
		businessDay = new BusinessDay();
		businessDay.setDate(2006, 06, 27);
		businessDay.setEmployee(emp);
		
		timeSlice = new TimeSlice();
		timeSlice.addClassifier(INSIDE_SCHEDULE,WORKED);
		timeSlice.setElapsed(5);
		businessDay.addTimeSlice(timeSlice);

		timeSlice = new TimeSlice();
		timeSlice.addClassifier(OUTSIDE_SCHEDULE,WORKED,APPROVED,OVERTIME_REQUEST,CASH,INVOLUNTARY);
		timeSlice.setElapsed(5);
		businessDay.addTimeSlice(timeSlice);
		
		schedule.addDayWorked(businessDay);


		// June 28, 2006 - 8 inside working hours
		businessDay = new BusinessDay();
		businessDay.setDate(2006, 06, 28);
		businessDay.setEmployee(emp);
		
		timeSlice = new TimeSlice();
		timeSlice.addClassifier(INSIDE_SCHEDULE,WORKED);
		timeSlice.setElapsed(5);
		businessDay.addTimeSlice(timeSlice);
		
		timeSlice = new TimeSlice();
//		timeSlice.addClassifier(INSIDE_SCHEDULE, WORKED);
		timeSlice.addClassifier(ABSENCE,APPROVED, INSIDE_SCHEDULE);
		timeSlice.setElapsed(3);
		businessDay.addTimeSlice(timeSlice);
			
		schedule.addDayWorked(businessDay);
		
	
		// June 29, 2006 - 8 inside working hours
		businessDay = new BusinessDay();
		businessDay.setDate(2006, 06, 29);
		businessDay.setEmployee(emp);
		
		timeSlice = new TimeSlice();
		timeSlice.addClassifier(INSIDE_SCHEDULE,WORKED);
		timeSlice.setElapsed(5);
		businessDay.addTimeSlice(timeSlice);
		
		timeSlice = new TimeSlice();
//		timeSlice.addClassifier(INSIDE_SCHEDULE, WORKED);
		timeSlice.addClassifier(ABSENCE,APPROVED, INSIDE_SCHEDULE);
		timeSlice.setElapsed(3);
		businessDay.addTimeSlice(timeSlice);
			
		schedule.addDayWorked(businessDay);
		
		
		// June 30, 2006 - 8 inside working hours
		businessDay = new BusinessDay();
		businessDay.setDate(2006, 06, 30);
		businessDay.setEmployee(emp);
		
		timeSlice = new TimeSlice();
		timeSlice.addClassifier(INSIDE_SCHEDULE,WORKED);
		timeSlice.setElapsed(5);
		businessDay.addTimeSlice(timeSlice);
		
		timeSlice = new TimeSlice();
//		timeSlice.addClassifier(INSIDE_SCHEDULE, WORKED);
		timeSlice.addClassifier(ABSENCE,APPROVED, INSIDE_SCHEDULE);
		timeSlice.setElapsed(3);
		businessDay.addTimeSlice(timeSlice);
			
		schedule.addDayWorked(businessDay);
		
		return emp;
	}

}