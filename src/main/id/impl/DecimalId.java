package main.id.impl;

import main.id.Id;

public class DecimalId extends Id {

	public DecimalId(int id) {
		this.id = String.valueOf(id);
		
	}
	
	@Override
	public boolean equals(Id targetId) {
		if(this.id.equals(targetId.getId())){
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(Id targetId) {
		return this.id.compareTo( targetId.getId());
			
	}

}
