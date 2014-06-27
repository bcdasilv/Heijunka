package br.org.fieb.heijunka;

import br.org.fieb.heijunka.algorithm.IHeijunkaStrategy;
import br.org.fieb.heijunka.algorithm.InvalidHeijunkaInputException;
import br.org.fieb.heijunka.model.Demand;
import br.org.fieb.heijunka.model.WorkSchedule;


public class HeijunkaBox{

	private Demand demandMap;
	private WorkSchedule workScheduleMap;
	private IHeijunkaStrategy heijunkaAlgorithm;
	
	public HeijunkaBox(){
		
	}
	
	public HeijunkaBox(Demand demand, WorkSchedule workSchedule){
		setDemand(demand);
		setWorkSchedule(workSchedule);
	}
	
	
	public Demand getDemand() {
		return demandMap;
	}
	
	public void setDemand(Demand demandMap) {
		this.demandMap = demandMap;
	}

	public WorkSchedule getWorkSchedule() {
		return workScheduleMap;
	}

	public void setWorkSchedule(WorkSchedule workScheduleMap) {
		this.workScheduleMap = workScheduleMap;
	}
	
	public String toString(){
		String output = "";
		try {
			int[][] box = heijunkaAlgorithm.leveling(demandMap, workScheduleMap);
			for (int i = 0; i < box.length; i++) {
				for (int j = 0; j < box.length; j++) {
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
		
	}
	
}
