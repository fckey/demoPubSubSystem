package main.data.factory;

import java.util.Random;

import main.commons.Const;
import main.commons.Location;
import main.data.Data;
import main.id.Id;
import main.id.factory.IdFactory;
import main.node.Node;
import processing.core.PApplet;

public class DataFactory {
	protected static PApplet core;
	protected IdFactory idFactory;
	Random rand = new Random();
	public DataFactory(PApplet core, IdFactory idFactory) {
		this.core = core;
		this.idFactory = idFactory;

	}
	
	public Data create(){
		return new Data(core, idFactory.create());
	}
	
	public Data create(Node srcNode, Node dstNode){
		return create(srcNode.getId(), dstNode.getId(), srcNode.getLocation(), dstNode.getLocation());
	}
	
	public Data create(Id srcId, Id dstId, Location src, Location dst){
		Data data = new Data(core, idFactory.create());
		data.init(srcId, dstId, src, dst, rand.nextDouble());
		return data;
		
	}
}
