package main.node.factory;

import main.commons.Location;
import main.id.Id;
import main.node.Node;

public interface NodeFactory {
	
	Node create(Location loc);
	
	Node create(Id id, Location loc);
}
