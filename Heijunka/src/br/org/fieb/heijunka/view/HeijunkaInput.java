package br.org.fieb.heijunka.view;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Vector;

import br.org.fieb.heijunka.HeijunkaBox;
import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.ItemContainer;
import br.org.fieb.heijunka.model.TimeSlot;
import br.org.fieb.heijunka.model.WorkSchedule;

public class HeijunkaInput {

	private Demand demand;
	private WorkSchedule workSchedule;
	
	public HeijunkaInput() throws FileNotFoundException {


		Scanner inFile= new Scanner(new FileReader("C:/heijunka/itencontainer.txt")).useDelimiter(",");
		Scanner inFile2= new Scanner(new FileReader("C:/heijunka/timeslot.txt")).useDelimiter(",");
		Vector<TimeSlot> timeSlots = new Vector<TimeSlot>();				
		setDemand(new Demand());
		LinkedHashMap<ItemContainer, Integer> demandMap = new LinkedHashMap<ItemContainer, Integer>();
		setWorkSchedule(new WorkSchedule());
		int in, in2, in3;
		
		while(inFile.hasNext()){
			ItemContainer itemContainer = new ItemContainer();			
			itemContainer.setId(inFile.next());
			itemContainer.setName(inFile.next());			
			in = Integer.parseInt(inFile.next());	
			itemContainer.setNumberOfItems(in);
			in = Integer.parseInt(inFile.next());
			itemContainer.setPitch(in);			
			in = Integer.parseInt(inFile.next());			
			demandMap.put(itemContainer, in);
			getDemand().setDemand(demandMap);			
		}
		inFile.close();		
		
		while(inFile2.hasNext()){			
			in = Integer.parseInt(inFile2.next());
			in2 = Integer.parseInt(inFile2.next());
			in3 = Integer.parseInt(inFile2.next());
			for(int i = in; i < in2; ){
				TimeSlot timeSlot = new TimeSlot(i,i+=in3);
				timeSlots.add(timeSlot);
			}	
			in = Integer.parseInt(inFile2.next());
			getWorkSchedule().setProductionDays(in);
		}
		inFile2.close();	
		getWorkSchedule().setWorkTimeSlots(timeSlots);
		//HeijunkaBox hBox = new HeijunkaBox(getDemand(), getWorkSchedule());
		//System.out.println(hBox.toString());	
	}

	public Demand getDemand() {
		return demand;
	}

	public void setDemand(Demand demand) {
		this.demand = demand;
	}

	public WorkSchedule getWorkSchedule() {
		return workSchedule;
	}

	public void setWorkSchedule(WorkSchedule workSchedule) {
		this.workSchedule = workSchedule;
	}
}
