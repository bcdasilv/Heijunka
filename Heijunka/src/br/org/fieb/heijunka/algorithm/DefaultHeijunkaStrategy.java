package br.org.fieb.heijunka.algorithm;

import java.util.List;
import java.util.Set;

import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.ItemContainer;
import br.org.fieb.heijunka.model.TimeSlot;
import br.org.fieb.heijunka.model.WorkSchedule;

//Not considering items sharing production processes

public class DefaultHeijunkaStrategy implements IHeijunkaStrategy{

	@Override
	public int[][] leveling(Demand demandMap, WorkSchedule workSchedule) throws InvalidHeijunkaInputException {
				
		if (demandMap == null && workSchedule ==null)
			throw new InvalidHeijunkaInputException("Demand or work schedule is null!");
		
		else if (demandMap.getDemand().isEmpty() || workSchedule.getWorkTimeSlots().isEmpty()){
			throw new InvalidHeijunkaInputException("Demand or work schedule is empty!");
		}
		
		Set<ItemContainer> conteiners = demandMap.getDemand().keySet();
		
		int numberOfItems = conteiners.size();
		int  numberOfSlots = workSchedule.getWorkTimeSlots().size();
		
		int[][] box = new int[numberOfItems][numberOfSlots];

		//Object[] shiftNumbersArray = shiftNumbers.toArray();
		Object[] conteinersArray = conteiners.toArray();
		
		for (int i = 0; i < conteinersArray.length; i++) {
			
			if (conteinersArray[i] != null){
				ItemContainer conteiner = (ItemContainer)conteinersArray[i];
				
				int pitch = conteiner.getPitch();
				
				box[i] = fitConteinerInTimeSlots(pitch, workSchedule);
			}
			
		}	
			
		return box;
	}
	
	private int[] fitConteinerInTimeSlots(int pitch, WorkSchedule workSchedule){
		
		List<TimeSlot> workTimeSlots = workSchedule.getWorkTimeSlots();
		
		Object[] workTimeSlotsArray = workTimeSlots.toArray();
		
		int[] boxRow = new int[workTimeSlots.size()];
		
		int sumTimeIntervals = 0; //In minutes. It'll be used when the container doesn't fit in a single timeSlot.
		
		for (int j = 0; j < workTimeSlotsArray.length; ) {
			
			TimeSlot timeSlot = (TimeSlot)workTimeSlotsArray[j];
			
			if (timeSlot!= null){
			
				int timeInterval = timeSlot.getTimeIntervalInMinutes();
				int fitness = timeInterval / pitch;
				
				sumTimeIntervals += timeInterval;

				if ( fitness >= 1){
					boxRow[j] = fitness;
					sumTimeIntervals = 0;
					j++;
				}
				else {
					int timeSlotFitted = fitContainerInMultipleTimeSlots(pitch, workTimeSlots, j+1, sumTimeIntervals);
					if(timeSlotFitted != -1){
						boxRow[timeSlotFitted] = 1;
						j = timeSlotFitted + 1;
						sumTimeIntervals = 0;
					}
					else
						break;
				}
			}
		}
		
		return boxRow;
	}
	
	private int fitContainerInMultipleTimeSlots(int pitch, List<TimeSlot> timeSlots, int slotIndex, int sumTimeIntervals){
				
		if (slotIndex >= timeSlots.size())
			return -1;

		TimeSlot timeSlot = timeSlots.get(slotIndex);
		if (timeSlot != null)
			sumTimeIntervals += timeSlot.getTimeIntervalInMinutes();

		
		if ( (sumTimeIntervals / pitch) >= 1)
			return slotIndex;
		
		return fitContainerInMultipleTimeSlots(pitch, timeSlots, slotIndex+1, sumTimeIntervals);
	}

}
