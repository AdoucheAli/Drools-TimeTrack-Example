[condition][com.jboss.example.Employee]There is an employee=$employee: Employee( $schedule:schedule )
[condition][com.jboss.example.WeeklyAccumulator]There is a WeeklyAccumulator=WeeklyAccumulator($OT: weeklyHoursTowardsOT, $FLSA: weeklyHoursTowardsFLSA)
[condition][com.jboss.example.WeeklyAccumulator]- that belongs to the employee=this == $schedule
[condition][com.jboss.example.TimeSlice]There is a Time Slice=$slice:TimeSlice()
[condition][com.jboss.example.TimeSlice]- that belongs to the employee=employee== $employee, $elapsed: elapsed
[consequence][]Set focuse on working group {working_group}=drools.getWorkingMemory().setFocus("{working_group}");
