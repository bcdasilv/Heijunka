package br.org.fieb.heijunka.model;

import java.util.LinkedHashMap;
import java.util.Map;


// Monthly demand
public class Demand {

	private Map<ItemContainer, Integer> demand;
	
	public Demand(){
		setDemand(new LinkedHashMap<ItemContainer, Integer>());
		
	}

	public Demand(Map<ItemContainer, Integer> demand){
		setDemand(demand);
		
	}
	
	public Map<ItemContainer, Integer> getDemand() {
		return demand;
	}

	public void setDemand(Map<ItemContainer, Integer> demand) {
		this.demand = demand;
	}
}
