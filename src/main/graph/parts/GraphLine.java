package main.graph.parts;

import main.graph.parts.GraphData;
import processing.core.PApplet;
import main.commons.Displayable;

public class GraphLine implements Displayable{
	protected PApplet core;
	protected float startX;
	protected float startY;
	protected float endX;
	protected float endY;
	int LineColor;
	GraphData[] dataSet = new GraphData[2];
	
	public GraphLine(PApplet core,GraphData predata,GraphData afterdata){
		this.core = core;
		dataSet[0]=predata;
		dataSet[1]=afterdata;
		LineColor = core.color(150,255,255,360);
	}
	
	public void init(GraphData predata,GraphData afterdata){
		dataSet[0]=predata;
		dataSet[1]=afterdata;
		LineColor = core.color(150,255,255,360);
	}
	
	private void updatePosition(){
		startX = dataSet[0].getXpos();
		startY = dataSet[0].getYpos();
		endX = dataSet[1].getXpos();
		endY = dataSet[1].getYpos();
	}
	
	@Override
	public void display() {
		updatePosition();
		core.stroke(LineColor);
		core.strokeWeight(2);
		core.line(startX,startY,endX,endY);  	
	}

}
