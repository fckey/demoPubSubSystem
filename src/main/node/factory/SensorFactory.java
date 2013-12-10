package main.node.factory;

import processing.core.PApplet;
import main.commons.Location;
import main.id.Id;
import main.id.factory.IdFactory;
import main.node.Node;
import main.node.nodeimpl.ProcessingNode;
import main.node.nodeimpl.SensorNode;

public class SensorFactory implements NodeFactory {

	private static  PApplet core;
	private static  IdFactory idFactory;
	public SensorFactory(PApplet core, IdFactory idFactory) {
		this.core = core;
		this.idFactory = idFactory;
	}
	
	public  Node create(Location loc){
		return create(idFactory.create(loc), loc);
	}
	
	public Node create(Id id, Location loc){
		return new SensorNode(core, id, loc);
	}

}
