package br.org.fieb.heijunka.algorithm;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.ItemContainer;
import br.org.fieb.heijunka.model.TimeSlot;
import br.org.fieb.heijunka.model.WorkSchedule;

//Not considering items sharing production processes

public class DefaultHeijunkaStrategy implements IHeijunkaStrategy{

	@Override
	public int[][] leveling(Demand demandMap, WorkSchedule workScheduleMap) throws InvalidHeijunkaInputException {
				
		if (demandMap != null && workScheduleMap !=null)
			throw new InvalidHeijunkaInputException("Demand or work schedule is null!");
		
		else if (demandMap.getDemand().isEmpty() || workScheduleMap.getShifts().isEmpty()){
			throw new InvalidHeijunkaInputException("Demand or work schedule is empty!");
		}
		
		Set<ItemContainer> conteiners = demandMap.getDemand().keySet();
		Set<Integer> shiftNumbers = workScheduleMap.getShifts().keySet();
		
//		Collection<Integer> demandValues = demandMap.getDemand().values();
//		Collection<TimeSlot> timeSlots = workScheduleMap.getShifts().values();
		
		int numberOfItems = conteiners.size();
		int  numberOfShifts = shiftNumbers.size();
		
		int[][] box = new int[numberOfItems][numberOfShifts];

		//Object[] shiftNumbersArray = shiftNumbers.toArray();
		Object[] conteinersArray = conteiners.toArray();
		
		for (int i = 0; i < conteinersArray.length; i++) {
			
			if (conteinersArray[i] != null){
				ItemContainer kanbanCard = (ItemContainer)conteinersArray[i];
				
				int pitch = kanbanCard.getPitch();
				
				box[i] = fitConteinerInTimeSlots(pitch, workScheduleMap);
			}
			
		}	
			
		return null;
	}
	
	private int[] fitConteinerInTimeSlots(int pitch, WorkSchedule workScheduleMap){
		
		Set<Integer> shiftNumbers = workScheduleMap.getShifts().keySet();
		Object[] shiftNumbersArray = shiftNumbers.toArray();
		
		int[] boxRow = new int[shiftNumbers.size()];
		
		int sumTimeIntervals = 0; //In minutes. Will be used when the container doesn't fit in a single timeSlot.
		
		for (int j = 0; j < shiftNumbersArray.length; ) {
			
			if (shiftNumbersArray[j] != null){
				
				Integer shiftNumber = (Integer) shiftNumbersArray[j];
				
				TimeSlot timeSlot = workScheduleMap.getShifts().get(shiftNumber);
				
				int timeInterval = timeSlot.getTimeIntervalInMinutes();
				int fitness = timeInterval / pitch;
				
				sumTimeIntervals += timeInterval;
				
				if ( fitness >= 1){
					boxRow[j] = fitness;
					j++;
				}
				else {
					int shiftNumberFitted = fitContainerInMultipleTimeSlots(pitch, workScheduleMap.getShifts().values(), shiftNumber+1, sumTimeIntervals);
					if(shiftNumberFitted != -1){
						boxRow[shiftNumberFitted] = 1;
						j = shiftNumberFitted;
					}
					else
						break;
				}
			}
		}
		
		return boxRow;
	}
	
	private int fitContainerInMultipleTimeSlots(int pitch, Collection<TimeSlot> timeSlots, int shiftNumber, int sumTimeIntervals){
				
		if (shiftNumber >= timeSlots.size())
			return -1;

		List<TimeSlot> timeSlotsList = (List<TimeSlot>)timeSlots;

		TimeSlot timeSlot = timeSlotsList.get(shiftNumber);
		if (timeSlot != null)
			sumTimeIntervals += timeSlot.getTimeIntervalInMinutes();

		
		if ( (sumTimeIntervals / pitch) >= 1)
			return shiftNumber;
		
		return fitContainerInMultipleTimeSlots(pitch, timeSlots, shiftNumber+1, sumTimeIntervals);
	}
	
//	int monthlyDemand = demandMap.getDemand().get(kanbanCard);
//	int monthlyKanbanCardDemand = monthlyDemand / kanbanCard.getNumberOfItems();

}
