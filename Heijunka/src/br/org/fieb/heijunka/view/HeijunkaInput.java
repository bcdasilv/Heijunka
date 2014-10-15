package br.org.fieb.heijunka.view;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Vector;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.ItemContainer;
import br.org.fieb.heijunka.model.TimeSlot;
import br.org.fieb.heijunka.model.WorkSchedule;

public class HeijunkaInput {

	private Demand demand;
	private WorkSchedule workSchedule;
	
	public HeijunkaInput() throws BiffException, IOException {

		Workbook workbook = Workbook.getWorkbook(new File("C:/heijunka/teste.xls"));
		
		//Vector<TimeSlot> timeSlots = new Vector<TimeSlot>();				
		
		setDemand(new Demand());
		LinkedHashMap<ItemContainer, Integer> demandMap = new LinkedHashMap<ItemContainer, Integer>();
		setWorkSchedule(new WorkSchedule());
		int j=0, in;
		
		Sheet sheet = workbook.getSheet(0);
		int linhas = sheet.getRows();
		int colunas = sheet.getColumns();		
		System.out.println("Quantidade de Linhas: "+ linhas);
		System.out.println("Quantidade de Colunas: "+ colunas);
		
		for (int i=1; i<linhas; i++){
			j=0;
			ItemContainer itemContainer = new ItemContainer();	
			
			while(j<colunas-1 ){
				itemContainer.setId(sheet.getCell(j, i).getContents());
				j++;
				System.out.println("SKU: "+ itemContainer.getId());
				
				itemContainer.setName(sheet.getCell(j, i).getContents());
				j++;
				System.out.println("Nome - Des: "+ itemContainer.getName());
				
				itemContainer.setNumberOfItems(Integer.parseInt(sheet.getCell(j, i).getContents()));
				j++;
				System.out.println("Quantide de Itens: "+ itemContainer.getNumberOfItems());
				
				itemContainer.setPitch(Integer.parseInt(sheet.getCell(j, i).getContents()));
				j++;
				System.out.println("Pitch: "+ itemContainer.getPitch());
				
				in = Integer.parseInt(sheet.getCell(j, i).getContents());			
				System.out.println("Demanda Mensal: "+ in);
				demandMap.put(itemContainer, in);
				getDemand().setDemand(demandMap);		
				
			}
			
		}
				
		workbook.close();
		
		
			
		/*
		 * String as2 = sheet.getCell(j, i).getContents();
				System.out.println("Linha "+ i +":"+ as2);
		 * 
		 * String as1 = sheet.getCell(j, 0).getContents();
			System.out.println("Coluna "+ j +":"+ as1);
		 * 
		 * Scanner inFile= new Scanner(new FileReader("C:/heijunka/itencontainer.txt")).useDelimiter(",");
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
	}*/
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
