package main.id.factory;

import main.commons.Location;
import main.id.Id;
import main.id.impl.DecimalId;

public class DecimalIdFactory extends IdFactory {
	Integer id;
	
	public DecimalIdFactory() {
		id = 0;
	}	
	
	@Override
	public Id create(Location loc) {
		Integer thisId = id;
		id++;
		return new DecimalId(thisId);
	}

	@Override
	public Id create() {
		Integer thisId = id;
		id++;
		return new DecimalId(thisId);
	}

}
