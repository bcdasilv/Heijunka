package br.org.fieb.heijunka.model;

import java.util.HashMap;
import java.util.Map;

// Demand in months
public class Demand {

	private Map<ItemContainer, Integer> demand;
	
	public Demand(){
		setDemand(new HashMap<ItemContainer, Integer>());
		
	}

	public Demand(HashMap<ItemContainer, Integer> demand){
		setDemand(demand);
		
	}
	
	public Map<ItemContainer, Integer> getDemand() {
		return demand;
	}

	public void setDemand(Map<ItemContainer, Integer> demand) {
		this.demand = demand;
	}
}
