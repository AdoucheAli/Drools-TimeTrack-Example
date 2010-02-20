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

	
	private static Employee buildEmployee() {
		
		Employee emp = new Employee();		
		emp.setEmployeeNumber(123);
		WeeklyAccumulator schedule = new WeeklyAccumulator();
		BusinessDay businessDay = new BusinessDay();
		
		schedule.setDailyHours(8);
		schedule.setWeeklyHours(40);
		schedule.addClassifier(ScheduleClassifiers.STANDARD);
		emp.setSchedule(schedule);	
		
		addInsideSlices(emp, businessDay, schedule);
		addOutsideSlices(emp, businessDay, schedule);
		
		return emp;
	}
	
	private static void addInsideSlices(Employee emp, BusinessDay businessDay, WeeklyAccumulator schedule) {
		
		// June 26, 2006
		businessDay.setDate(2006,06, 26);		
		businessDay.setEmployee(emp);
		
		schedule.addDayWorked(businessDay);	
		
		// Trigger InsideSchedule_worked_Accumulation_Rule
		TimeSlice timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(WORKED);
		timeSlice.setStart(businessDay.getDate());
		timeSlice.setEnd(businessDay.getDate());
		timeSlice.setElapsed(8);
		businessDay.addTimeSlice(timeSlice);
		
		
		// June 27, 2006
		businessDay = new BusinessDay();
		businessDay.setDate(2006, 06, 27);
		businessDay.setEmployee(emp);
		
		// Trigger InsideSchedule_worked_Accumulation_Rule
		timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(WORKED);
		timeSlice.setElapsed(8);
		businessDay.addTimeSlice(timeSlice);

		// No rule trigger - no absence
		timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(WORKED,APPROVED,OVERTIME_REQUEST,CASH,INVOLUNTARY,COUNT_TO_FLSA);
		timeSlice.setElapsed(3);
		businessDay.addTimeSlice(timeSlice);
		
		schedule.addDayWorked(businessDay);
		

		// June 28, 2006
		businessDay = new BusinessDay();
		businessDay.setDate(2006, 06, 28);
		businessDay.setEmployee(emp);
		
		// Trigger InsideSchedule_worked_Accumulation_Rule
		timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(WORKED);
		timeSlice.setElapsed(5);
		businessDay.addTimeSlice(timeSlice);
		
		// No rule trigger - no OT
		timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(ABSENCE, APPROVED, COUNT_TO_FLSA);
		timeSlice.setElapsed(3);
		businessDay.addTimeSlice(timeSlice);
			
		schedule.addDayWorked(businessDay);
		
	
		// June 29, 2006
		businessDay = new BusinessDay();
		businessDay.setDate(2006, 06, 29);
		businessDay.setEmployee(emp);
		
		// Trigger InsideSchedule_worked_Accumulation_Rule
		timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(WORKED);
		timeSlice.setElapsed(5);
		businessDay.addTimeSlice(timeSlice);
		
		// Trigger InsideSchedule_ExcusedAbsence_Accumulation_Rule
		timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(ABSENCE, APPROVED, OT, COUNT_TO_FLSA);
		timeSlice.setElapsed(3);
		businessDay.addTimeSlice(timeSlice);
			
		schedule.addDayWorked(businessDay);
		
		
		// June 30, 2006
		businessDay = new BusinessDay();
		businessDay.setDate(2006, 06, 30);
		businessDay.setEmployee(emp);
		
		// Trigger InsideSchedule_worked_Accumulation_Rule
		timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(WORKED);
		timeSlice.setElapsed(5);
		businessDay.addTimeSlice(timeSlice);
		
		// Trigger InsideSchedule_Absence_Accumulation_Rule
		timeSlice = createInsideScheduleBaseTimeSlice();
		timeSlice.addClassifier(ABSENCE, APPROVED, OT);
		timeSlice.setElapsed(3);
		businessDay.addTimeSlice(timeSlice);
			
		schedule.addDayWorked(businessDay);
		
	}
	
	private static void addOutsideSlices(Employee emp, BusinessDay businessDay, WeeklyAccumulator schedule) {
		
		// July 1, 2006
		businessDay.setDate(2006, 07, 01);		
		businessDay.setEmployee(emp);
		
		schedule.addDayWorked(businessDay);	
		
		// Trigger JTP_Overtime_RuleSet
		TimeSlice timeSlice = createOutsideScheduleBaseTimeSlice();
		timeSlice.setStart(businessDay.getDate());
		timeSlice.setEnd(businessDay.getDate());
		timeSlice.setElapsed(8);
		businessDay.addTimeSlice(timeSlice);
	}
	
	private static TimeSlice createInsideScheduleBaseTimeSlice() {
		TimeSlice timeSlice = new TimeSlice();
		timeSlice.addClassifier(INSIDE_SCHEDULE);
		return timeSlice;
	}
	
	private static TimeSlice createOutsideScheduleBaseTimeSlice() {
		TimeSlice timeSlice = new TimeSlice();
		timeSlice.addClassifier(OUTSIDE_SCHEDULE, WORKED, INVOLUNTARY, OVERTIME_REQUEST, CASH, APPROVED);
		return timeSlice;
	}

}