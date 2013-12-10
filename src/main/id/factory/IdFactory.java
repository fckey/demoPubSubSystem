package main.id.factory;

import main.commons.Location;
import main.id.Id;

public abstract class IdFactory {
	abstract public Id create(Location loc);
	abstract public Id create();
}
