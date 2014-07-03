package br.org.fieb.heijunka.model;

import java.util.List;
import java.util.Vector;

//Considering in one day
public class WorkSchedule {
	
	private List<TimeSlot> workTimeSlots;
	
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

	

}
