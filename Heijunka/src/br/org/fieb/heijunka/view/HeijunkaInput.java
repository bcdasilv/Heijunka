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


	public static void main(String args[]) throws FileNotFoundException{

		Scanner inFile= new Scanner(new FileReader("C:/heijunka/itencontainer.txt")).useDelimiter(",");
		Scanner inFile2= new Scanner(new FileReader("C:/heijunka/timeslot.txt")).useDelimiter(",");
		Vector<TimeSlot> timeSlots = new Vector<TimeSlot>();				
		Demand demand = new Demand();
		LinkedHashMap<ItemContainer, Integer> demandMap = new LinkedHashMap<ItemContainer, Integer>();
		WorkSchedule workSchedule = new WorkSchedule();
		int in, in2, in3;
		
		while(inFile.hasNext()){
			ItemContainer itemContainer = new ItemContainer();			
			itemContainer.setId(inFile.next());
			itemContainer.setName(inFile.next());			
			in = Integer.parseInt(inFile.next());			
			in = Integer.parseInt(inFile.next());
			itemContainer.setPitch(in);			
			in = Integer.parseInt(inFile.next());			
			demandMap.put(itemContainer, in);
			demand.setDemand(demandMap);			
		}
		inFile.close();		
		
		while(inFile2.hasNext()){			
			in = Integer.parseInt(inFile2.next());
			in2 = Integer.parseInt(inFile2.next());
			in3 = Integer.parseInt(inFile2.next());
			for(int i = in; i <= in2; i+=in3){
				TimeSlot timeSlot = new TimeSlot(i,i+=in3);
				timeSlots.add(timeSlot);
			}	
			in = Integer.parseInt(inFile2.next());
			workSchedule.setProductionDays(in);
		}
		inFile2.close();		
		workSchedule = new WorkSchedule(timeSlots);			
		HeijunkaBox hBox = new HeijunkaBox(demand, workSchedule);
		System.out.println(hBox.toString());	
		HeijunkaOutput output = new HeijunkaOutput();
		output.criaJanela(hBox);
	}
}
