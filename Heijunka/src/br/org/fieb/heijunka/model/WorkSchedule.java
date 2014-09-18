package br.org.fieb.heijunka.model;

import java.util.List;
import java.util.Vector;

//Considering time slots in one single day
//Then, we replicate time slots for the number of production days
public class WorkSchedule {
	
	private List<TimeSlot> workTimeSlots;
	private int productiveDays;
	
	public WorkSchedule(){
		setWorkTimeSlots(new Vector<TimeSlot>());
		
	}
	
	public WorkSchedule(List<TimeSlot> workTimeSlots){
		setWorkTimeSlots(workTimeSlots);
		
	}	
	
	public List<TimeSlot> getWorkTimeSlots() {
		return workTimeSlots;
	}

	public void setWorkTimeSlots(List<TimeSlot> workTimeSlots) {
		this.workTimeSlots = workTimeSlots;
	}
	
	//Maybe improve here throwing exceptions
	public boolean addWorkTimeSlot(TimeSlot newTimeSlot){
		
		TimeSlot lastTimeSlot = workTimeSlots.get(workTimeSlots.size()-1);
		
		if (lastTimeSlot.getEndTime() <= newTimeSlot.getStartTime())
			return workTimeSlots.add(newTimeSlot);
		
		return false;
	}

	public int productionMinutesDay(){
		int minutes = 0;
		for (TimeSlot ts : workTimeSlots) {
			minutes += ts.getTimeIntervalInMinutes();
		}
		return minutes;
	}

	public int getProductionDays() {
		return productiveDays;
	}

	public void setProductionDays(int productionDays) {
		this.productiveDays = productionDays;
	}

}
