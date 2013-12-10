package main.node.factory;

import main.commons.Location;
import main.id.Id;
import main.id.factory.IdFactory;
import main.node.Node;
import main.node.nodeimpl.ProcessingNode;
import processing.core.PApplet;

public class ProcessingNodeFactory implements NodeFactory{
	private static  PApplet core;
	private static  IdFactory idFactory;
	
	public ProcessingNodeFactory(PApplet core, IdFactory idFactory) {
		this.core = core;
		this.idFactory = idFactory;
	}
	
	public  Node create(Location loc){
		return create(idFactory.create(loc), loc);
	}
	
	public Node create(Id id, Location loc){
		return new ProcessingNode(core, id, loc);
	}
	
}
