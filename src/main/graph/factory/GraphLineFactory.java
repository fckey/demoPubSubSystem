package main.graph.factory;

import main.graph.parts.GraphData;
import main.graph.parts.GraphLine;
import main.id.factory.IdFactory;
import processing.core.PApplet;

public class GraphLineFactory {
	private static  PApplet core;
		
	public GraphLineFactory(PApplet core) {
		this.core = core;
	}
	
	public GraphLine create(GraphData pre, GraphData after){
		return new GraphLine(core, pre, after);
	}
	
}
