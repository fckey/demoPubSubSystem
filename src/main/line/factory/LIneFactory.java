package main.line.factory;

import main.commons.Location;
import main.line.Line;
import processing.core.PApplet;

public class LIneFactory {

	protected PApplet core;
	
	public LIneFactory(PApplet core) {
		this.core = core;
	}
	
	public Line create(Location start, Location end){
		return new Line(core, start, end);
	}
}
