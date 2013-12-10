package main.node.factory;

import processing.core.PApplet;
import main.commons.Location;
import main.id.Id;
import main.id.factory.IdFactory;
import main.node.Node;
import main.node.nodeimpl.SensorNode;
import main.node.nodeimpl.Subscriber;

public class SubscriberFactory implements NodeFactory {

	private static  PApplet core;
	private static  IdFactory idFactory;
	public SubscriberFactory(PApplet core, IdFactory idFactory) {
		this.core = core;
		this.idFactory = idFactory;
	}
	
	public  Node create(Location loc){
		return create(idFactory.create(loc), loc);
	}
	
	public Node create(Id id, Location loc){
		return new Subscriber
			(core, id, loc);
	}

}
