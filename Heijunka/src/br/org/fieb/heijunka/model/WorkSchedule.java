package br.org.fieb.heijunka.model;

import java.util.HashMap;
import java.util.Map;

//Considering in one day
public class WorkSchedule {
	
	private Map<Integer, TimeSlot> shifts;
	
	public WorkSchedule(){
		setShifts(new HashMap<Integer, TimeSlot>());
		
	}
	
	public WorkSchedule(HashMap<Integer, TimeSlot> shifts){
		setShifts(shifts);
		
	}	
	
	public Map<Integer, TimeSlot> getShifts() {
		return shifts;
	}

	public void setShifts(Map<Integer, TimeSlot> shifts) {
		this.shifts = shifts;
	}

	

}
