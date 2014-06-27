package br.org.fieb.heijunka.model;

import java.util.HashMap;
import java.util.Map;

// Demand in months
public class Demand {

	private Map<KanbanCard, Integer> demand;
	
	public Demand(){
		setDemand(new HashMap<KanbanCard, Integer>());
		
	}

	public Demand(HashMap<KanbanCard, Integer> demand){
		setDemand(demand);
		
	}
	
	public Map<KanbanCard, Integer> getDemand() {
		return demand;
	}

	public void setDemand(Map<KanbanCard, Integer> demand) {
		this.demand = demand;
	}
}
