package main.node;

import java.util.Arrays;

import main.commons.Const;
import main.commons.Displayable;
import main.commons.Location;
import main.id.Id;
import main.textbox.TextBox;
import processing.core.PApplet;
import processing.core.PImage;

public abstract class Node implements Displayable{
	protected Id id;
	protected Location location;
	protected PApplet core;
	protected TextBox textBox;
	protected PImage nodeImage = null;
	protected int nodeColor;

	protected float  xEdge;
	protected float  yEdge;
	protected  int nodeWidth = Const.FULLPANELWIDTH/20;
	protected  int nodeHeight = Const.FULLPANELHEIGHT/10;
	
	public Node(PApplet core, Id id, Location loc) {
		this.core = core;
		this.id = id;
		this.location = new Location(loc.getX()+nodeWidth/2, loc.getY()+nodeHeight/2);
		this.xEdge = loc.getX();
		this.yEdge = loc.getY();
		this.nodeColor = core.color(150,255,255,255);
		this.textBox = new TextBox(core, "", loc, Arrays.asList("CPU","Load",""));
	}
	
	public abstract void display();
	
	public Id getId() {
		return id;
	}
	
	public void setLocation(Location loc){
		this.location = loc;
	}
	
	public Location getLocation(){
		return this.location;
	}
}
