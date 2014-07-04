package br.org.fieb.heijunka.algorithm;

import java.util.List;
import java.util.Set;

import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.ItemContainer;
import br.org.fieb.heijunka.model.TimeSlot;
import br.org.fieb.heijunka.model.WorkSchedule;

/*
 * Esta estratégia nivela os produtos considerando a demanda mensal. Assim,
 * o algoritmo procura ajustar o mais próximo possível a produção diária
 * para que não ultrapasse a demanda mensal.
 * 
 * */
public class DefaultHJVariableSlotsWithMonthlyDemand implements IHeijunkaStrategy{

	@Override
	public Integer[][] leveling(Demand demandMap, WorkSchedule workSchedule) throws InvalidHeijunkaInputException {
				
		if (demandMap == null && workSchedule ==null)
			throw new InvalidHeijunkaInputException("Demand or work schedule is null!");
		
		else if (demandMap.getDemand().isEmpty() || workSchedule.getWorkTimeSlots().isEmpty())
			throw new InvalidHeijunkaInputException("Demand or work schedule is empty!");
		
		Set<ItemContainer> conteiners = demandMap.getDemand().keySet();
		
		int numberOfItems = conteiners.size();
		int  numberOfSlots = workSchedule.getWorkTimeSlots().size();
		
		Integer[][] box = initializeArray(numberOfItems, numberOfSlots);

		Object[] conteinersArray = conteiners.toArray();
		
		for (int i = 0; i < conteinersArray.length; i++) {
			
			if (conteinersArray[i] != null){
				ItemContainer conteiner = (ItemContainer)conteinersArray[i];
				
				int pitch = conteiner.getPitch();
				if (conteinerFitInDay(pitch, workSchedule)){
					int conteinersDayLimit = conteinersDayLimit(conteiner.getNumberOfItems(), 
							demandMap.getDemand().get(conteiner), workSchedule.getProductionDays(), true);
					box[i] = fitConteinerInTimeSlots(conteinersDayLimit, pitch, workSchedule);
				}
				else{
					//box[i] = new Integer[numberOfSlots];
					box[i] = initializeArray(numberOfSlots);
				}
			}
			
		}	
			
		return box;
	}
	
	private Integer[] fitConteinerInTimeSlots(int conteinersDayLimit, int pitch, WorkSchedule workSchedule){

		List<TimeSlot> workTimeSlots = workSchedule.getWorkTimeSlots();

		Object[] workTimeSlotsArray = workTimeSlots.toArray();

		//Integer[] boxRow = new Integer[workTimeSlots.size()];
		Integer[] boxRow = initializeArray(workTimeSlots.size());

		int sumTimeIntervals = 0; //In minutes. It'll be used when the container doesn't fit in a single timeSlot.
		int numberOfConteinersRow = 0; //to control production above the monthly demand

		for (int j = 0; j < workTimeSlotsArray.length; ) {

			if (numberOfConteinersRow < conteinersDayLimit){
				TimeSlot timeSlot = (TimeSlot)workTimeSlotsArray[j];

				if (timeSlot!= null){

					int timeInterval = timeSlot.getTimeIntervalInMinutes();
					int fitness = howManyConteinersInSlot(timeInterval, pitch);

					sumTimeIntervals += timeInterval;

					if (fitness >= 1) {
						while(!isFitnessFeasibleAccordingDailyDemand(numberOfConteinersRow, fitness, conteinersDayLimit)){
							fitness--;
						}
						boxRow[j] = fitness;
						sumTimeIntervals = 0;
						numberOfConteinersRow += fitness;
						j++;
					}
					else {
						if (j==0){ //products always pulled in the first slot
							boxRow[j] = 1;
							sumTimeIntervals = 0;
							j++;
						}
						else{
							int timeSlotFitted = fitContainerInMultipleTimeSlots(pitch, workTimeSlots, j+1, sumTimeIntervals);
							if(timeSlotFitted != -1){
								boxRow[timeSlotFitted] = 1;
								j = timeSlotFitted + 1;
								sumTimeIntervals = 0;
							}
							else
								break;
						}
						numberOfConteinersRow += 1;
					}
				}
			}
			else
				break;
		}

		return boxRow;
	}
	
	private int fitContainerInMultipleTimeSlots(int pitch, List<TimeSlot> timeSlots, int slotIndex, int sumTimeIntervals){
				
		if (slotIndex >= timeSlots.size())
			return -1;

		TimeSlot timeSlot = timeSlots.get(slotIndex);
		if (timeSlot != null)
			sumTimeIntervals += timeSlot.getTimeIntervalInMinutes();

		if ( howManyConteinersInSlot(sumTimeIntervals, pitch) >= 1)
			return slotIndex;
		
		return fitContainerInMultipleTimeSlots(pitch, timeSlots, slotIndex+1, sumTimeIntervals);
	}
	
	private int howManyConteinersInSlot(int timeInterval, int pitch){
		return timeInterval / pitch;
	}
	
	private boolean conteinerFitInDay(int pitch, WorkSchedule workSchedule){
		
		int productionMinutesDay = workSchedule.productionMinutesDay();
		
		if (productionMinutesDay > pitch)
			return true;
		
		return false;
	}
	
	private int conteinersDayLimit(int containerSize, int monthlyDemand, int productionDays, boolean roundUp){
		int numberOfContainersDay = 0;

		int monthlyDemandInContainers = monthlyDemand / containerSize; 
		if ( ((monthlyDemand % containerSize) != 0) && roundUp )
			monthlyDemandInContainers++;
		
		numberOfContainersDay = monthlyDemandInContainers / productionDays;
		if ( ((monthlyDemandInContainers % productionDays) != 0) && roundUp )
			numberOfContainersDay++;
		
		return numberOfContainersDay;
	}
	
	private boolean isFitnessFeasibleAccordingDailyDemand(int numberOfConteinersRow, int fitness, int conteinersDayLimit){
		return ((numberOfConteinersRow + fitness) <= conteinersDayLimit);
	}
	
	private Integer[][] initializeArray(int numberOfItems, int numberOfSlots){
		Integer[][] box = new Integer[numberOfItems][numberOfSlots];
		for (int i = 0; i < box.length; i++) {
			box[i] = initializeArray(numberOfSlots); 
		}
		return box;
	}
	
	private Integer[] initializeArray(int numberOfSlots){
		Integer[] box = new Integer[numberOfSlots];
		for (int i = 0; i < box.length; i++) {
			box[i] = new Integer(0);
		}
		return box;
	}

}
