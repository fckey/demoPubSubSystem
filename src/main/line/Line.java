package main.line;

import processing.core.PApplet;
import main.commons.Displayable;
import main.commons.Location;

public class Line implements Displayable{
	PApplet core;
	protected Location startLoc;
	protected Location endLoc;
	
	public Line(PApplet core, Location s, Location e) {
		this.core = core;
		this.startLoc = s;
		this.endLoc = e;
	}

	@Override
	public void display() {
		core.stroke(core.color(78,200,255,255));
		core.strokeWeight(3);
		core.line(startLoc.getX(),startLoc.getY(), endLoc.getX(), endLoc.getY());  
	}
}
