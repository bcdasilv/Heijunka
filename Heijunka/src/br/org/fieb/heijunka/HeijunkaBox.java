package br.org.fieb.heijunka;

import java.io.FileNotFoundException;
import java.io.IOException;

import jxl.read.biff.BiffException;
import br.org.fieb.heijunka.algorithm.DefaultHJVariableSlots;
import br.org.fieb.heijunka.algorithm.IHeijunkaStrategy;
import br.org.fieb.heijunka.algorithm.InvalidHeijunkaInputException;
import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.WorkSchedule;
import br.org.fieb.heijunka.view.HeijunkaForm;
import br.org.fieb.heijunka.view.HeijunkaInput;


public class HeijunkaBox{

	private Demand demandMap;
	private WorkSchedule workSchedule;
	private IHeijunkaStrategy heijunkaAlgorithm;
	
	public HeijunkaBox(){
		heijunkaAlgorithm = new DefaultHJVariableSlots();
	}
	
	public HeijunkaBox(Demand demand, WorkSchedule workSchedule){
		setDemand(demand);
		setWorkSchedule(workSchedule);
		heijunkaAlgorithm = new DefaultHJVariableSlots();
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
			Integer[][] box = heijunkaAlgorithm.leveling(demandMap, workSchedule);
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
	
	public Integer[][] getBoxIntegerArray(){
		try {
			return heijunkaAlgorithm.leveling(demandMap, workSchedule);
		} catch (InvalidHeijunkaInputException e) {
			e.printStackTrace();
		}
		return null;
	}
	
//	public static void main(String args[]){
//		
//		Demand demand = new Demand();
//		
//		ItemContainer itemContainer1 = new ItemContainer();
//		ItemContainer itemContainer2 = new ItemContainer();
//		ItemContainer itemContainer3 = new ItemContainer();
//		ItemContainer itemContainer4 = new ItemContainer();
//		ItemContainer itemContainer5 = new ItemContainer();
//		ItemContainer itemContainer6 = new ItemContainer();
//		
//		itemContainer1.setId("123");
//		itemContainer1.setName("Liquidificador Black Diamond 110V");
//		itemContainer1.setNumberOfItems(6); 
//		itemContainer1.setPitch(30); //assuming 5 minutes per item
//		
//		itemContainer2.setId("456");
//		itemContainer2.setName("Ventilador 30cm branco 110V");
//		itemContainer2.setNumberOfItems(4); 
//		itemContainer2.setPitch(32); //assuming 8 minutes per item
//		
//		itemContainer3.setId("789");
//		itemContainer3.setName("Refrigerador Branco 220V");
//		itemContainer3.setNumberOfItems(1); 
//		itemContainer3.setPitch(70); //assuming 70 minutes per item
//		
//		itemContainer4.setId("abc");
//		itemContainer4.setName("Lavadora/Secadora Inox 11kg 110v");
//		itemContainer4.setNumberOfItems(1); 
//		itemContainer4.setPitch(130); //assuming 130 minutes per item
//		
//		itemContainer5.setId("xyz");
//		itemContainer5.setName("Torradeira preta 220v");
//		itemContainer5.setNumberOfItems(10); 
//		itemContainer5.setPitch(60); //assuming 6 minutes per item
//		
//		itemContainer6.setId("xpto");
//		itemContainer6.setName("Disco voador");
//		itemContainer6.setNumberOfItems(1); 
//		itemContainer6.setPitch(100000); //testing a product that cannot be produced in a day
//		
//		LinkedHashMap<ItemContainer, Integer> demandMap = new LinkedHashMap<ItemContainer, Integer>();
//		demandMap.put(itemContainer1, 450*itemContainer1.getNumberOfItems());
//		demandMap.put(itemContainer2, 150*itemContainer2.getNumberOfItems());
//		demandMap.put(itemContainer3, 100*itemContainer3.getNumberOfItems());
//		demandMap.put(itemContainer4, 100*itemContainer4.getNumberOfItems());
//		demandMap.put(itemContainer5, 100*itemContainer5.getNumberOfItems());
//		demandMap.put(itemContainer6, 100*itemContainer6.getNumberOfItems());
//		
//		demand.setDemand(demandMap);
//		
//		Vector<TimeSlot> timeSlots = new Vector<TimeSlot>();
//		
//		TimeSlot timeSlot1 = new TimeSlot(8, 9);
//		timeSlots.add(timeSlot1);
//		
//		TimeSlot timeSlot2 = new TimeSlot(9, 10);
//		timeSlots.add(timeSlot2);
//		
//		TimeSlot timeSlot3 = new TimeSlot(10, 11);
//		timeSlots.add(timeSlot3);
//		
//		TimeSlot timeSlot4 = new TimeSlot(11, 12);
//		timeSlots.add(timeSlot4);
//		
//		TimeSlot timeSlot5 = new TimeSlot(12, 13);
//		timeSlots.add(timeSlot5);
//		
//		TimeSlot timeSlot6 = new TimeSlot(13, 14);
//		timeSlots.add(timeSlot6);
//		
//		TimeSlot timeSlot7 = new TimeSlot(14, 15);
//		timeSlots.add(timeSlot7);		
//		
//		TimeSlot timeSlot8 = new TimeSlot(15, 16);
//		timeSlots.add(timeSlot8);	
//		
//		WorkSchedule workSchedule = new WorkSchedule(timeSlots);
//		workSchedule.setProductionDays(30);
//		
//		HeijunkaBox hBox = new HeijunkaBox(demand, workSchedule);
//		hBox.setHeijunkaAlgorithm(new DefaultHJVariableSlotsWithMonthlyDemand());
//		
//		System.out.println(hBox.toString());
//		
//	}
	
	public static void main(String args[]) throws BiffException, IOException{
		
		HeijunkaInput hInput = new HeijunkaInput();		
		/*HeijunkaInput hInput = new HeijunkaInput();
		HeijunkaBox hBox = new HeijunkaBox(hInput.getDemand(), hInput.getWorkSchedule());
		HeijunkaOutput hOut = new HeijunkaOutput("Heijunka Demo");
		hOut.criaJanela(hBox);*/
		HeijunkaForm hFrame = new HeijunkaForm("Programação Heijunka");
		//hFrame.criaJanelaFuncionamentoFabrica();
		hFrame.criaJanelaitem();
	}
	
}
