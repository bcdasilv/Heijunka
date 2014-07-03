package br.org.fieb.heijunka;

import java.util.LinkedHashMap;
import java.util.Vector;

import br.org.fieb.heijunka.algorithm.DefaultHeijunkaStrategy;
import br.org.fieb.heijunka.algorithm.IHeijunkaStrategy;
import br.org.fieb.heijunka.algorithm.InvalidHeijunkaInputException;
import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.ItemContainer;
import br.org.fieb.heijunka.model.TimeSlot;
import br.org.fieb.heijunka.model.WorkSchedule;


public class HeijunkaBox{

	private Demand demandMap;
	private WorkSchedule workSchedule;
	private IHeijunkaStrategy heijunkaAlgorithm;
	
	public HeijunkaBox(){
		heijunkaAlgorithm = new DefaultHeijunkaStrategy();
	}
	
	public HeijunkaBox(Demand demand, WorkSchedule workSchedule){
		setDemand(demand);
		setWorkSchedule(workSchedule);
		heijunkaAlgorithm = new DefaultHeijunkaStrategy();
	}
	
	
	public Demand getDemand() {
		return demandMap;
	}
	
	public void setDemand(Demand demandMap) {
		this.demandMap = demandMap;
	}

	public WorkSchedule getWorkSchedule() {
		return workSchedule;
	}

	public void setWorkSchedule(WorkSchedule workSchedule) {
		this.workSchedule = workSchedule;
	}
	
	public void setHeijunkaAlgorithm(IHeijunkaStrategy heijunkaAlgorithm){
		this.heijunkaAlgorithm = heijunkaAlgorithm;
	}
	
	public String toString(){
		String output = "";
		try {
			int[][] box = heijunkaAlgorithm.leveling(demandMap, workSchedule);
			for (int i = 0; i < box.length; i++) {
				for (int j = 0; j < box[i].length; j++) {
					output += box[i][j] + "  ";
				}
				output += "\n";
			}
		} catch (InvalidHeijunkaInputException e) {
			e.printStackTrace();
		}
		return output;
	}
	
	public static void main(String args[]){
		
		Demand demand = new Demand();
		
		ItemContainer itemContainer1 = new ItemContainer();
		ItemContainer itemContainer2 = new ItemContainer();
		ItemContainer itemContainer3 = new ItemContainer();
		ItemContainer itemContainer4 = new ItemContainer();
		
		itemContainer1.setId("123");
		itemContainer1.setName("Liquidificador Black Diamond 110V");
		itemContainer1.setNumberOfItems(6); 
		itemContainer1.setPitch(30); //assuming 5 minutes per item
		
		itemContainer2.setId("456");
		itemContainer2.setName("Ventilador 30cm branco 110V");
		itemContainer2.setNumberOfItems(4); 
		itemContainer2.setPitch(32); //assuming 8 minutes per item
		
		itemContainer3.setId("789");
		itemContainer3.setName("Refrigerador Branco 220V");
		itemContainer3.setNumberOfItems(1); 
		itemContainer3.setPitch(70); //assuming 70 minutes per item
		
		itemContainer4.setId("xyz");
		itemContainer4.setName("Lavadora/Secadora Inox 11kg 110v");
		itemContainer4.setNumberOfItems(1); 
		itemContainer4.setPitch(130); //assuming 130 minutes per item
		
		LinkedHashMap<ItemContainer, Integer> demandMap = new LinkedHashMap<ItemContainer, Integer>();
		demandMap.put(itemContainer1, 50000);
		demandMap.put(itemContainer2, 15000);
		demandMap.put(itemContainer3, 500);
		demandMap.put(itemContainer4, 200);
		
		demand.setDemand(demandMap);
		
		Vector<TimeSlot> timeSlots = new Vector<TimeSlot>();
		
		TimeSlot timeSlot1 = new TimeSlot(8, 9);
		timeSlots.add(timeSlot1);
		
		TimeSlot timeSlot2 = new TimeSlot(9, 10);
		timeSlots.add(timeSlot2);
		
		TimeSlot timeSlot3 = new TimeSlot(10, 11);
		timeSlots.add(timeSlot3);
		
		TimeSlot timeSlot4 = new TimeSlot(11, 12);
		timeSlots.add(timeSlot4);
		
		TimeSlot timeSlot5 = new TimeSlot(12, 13);
		timeSlots.add(timeSlot5);
		
		TimeSlot timeSlot6 = new TimeSlot(13, 14);
		timeSlots.add(timeSlot6);
		
		TimeSlot timeSlot7 = new TimeSlot(14, 15);
		timeSlots.add(timeSlot7);		
		
		TimeSlot timeSlot8 = new TimeSlot(15, 16);
		timeSlots.add(timeSlot8);	
		
		WorkSchedule workSchedule = new WorkSchedule(timeSlots);
		
		HeijunkaBox hBox = new HeijunkaBox(demand, workSchedule);
		
		System.out.println(hBox.toString());
		
	}
	
}
