package main.graph.factory;

import main.graph.parts.GraphData;
import main.id.factory.IdFactory;
import processing.core.PApplet;

public class GraphDataFactory {

	private static  PApplet core;
	private float xStart;
	
	public GraphDataFactory(PApplet core, float xPos) {
		this.core = core;
		xStart = xPos;

	}
	
	public GraphData create(float yStart){
		return new GraphData(core, xStart, yStart);
	}
	
}
