package main.graph.parts;


import processing.core.PApplet;
import main.commons.Displayable;
import main.graph.Graph;

public class GraphData implements Displayable {
	protected PApplet core;
	protected float  xPos;
	protected float  yPos;
	protected int color;
	static float RADIUS = 3;
	
	public GraphData(PApplet core,float xpos,float ypos){
		this.core = core;
		this.xPos = xpos;
		this.yPos = ypos;
		this.color = core.color(50,255,255,250);
	}
	
	public void init(float xpos,float ypos){
		this.xPos = xpos;
		this.yPos = ypos;
	}
	
	public void setDataColor(int color){
		this.color = color;
	}
	public void update(){
		xPos = xPos - Graph.getPanelWidth()/20;
	}
	public float getXpos(){
		return xPos;
	}
	public float getYpos(){
		return yPos;
	}
	public int getDataColor(){
		return color;
	}
	@Override
	public void display() {
		core.noStroke();
		core.fill(color);
		core.ellipse(xPos, yPos,RADIUS,RADIUS);
		
	}

}
