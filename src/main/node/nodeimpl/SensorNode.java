package main.node.nodeimpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import processing.core.PApplet;
import main.commons.Const;
import main.commons.GetFilePath;
import main.commons.Location;
import main.commons.Sendable;
import main.data.Data;
import main.id.Id;
import main.node.Node;
import main.textbox.TextBox;

public class SensorNode extends Node implements Sendable{

	protected boolean isActive = false; 
	protected final List<Id> dstIds = new ArrayList<Id>();
	
	public SensorNode(PApplet core, Id id, Location loc) {
		super(core, id, loc);
		nodeImage = core.loadImage(GetFilePath.getPath(Const.PROJECT)+Const.PICTPATH+"sensor.png");
		nodeWidth = nodeWidth/2;
		nodeHeight = nodeHeight*4/5;
		isActive = true;
		textBox = new TextBox(core, "Sensor", new Location(loc.getX()-nodeHeight, loc.getY()), Arrays.asList("Created","Temperature", ""));
	}
	@Override
	public void display() {
		core.noStroke();
		core.image(nodeImage, xEdge, yEdge,nodeWidth,nodeHeight);
		textBox.display();
	}
	
	public boolean isActive(){
		return isActive;
	}
	
	@Override
	public void send(Data data) {
		textBox.setText(String.format("%f",data.getValue()));
		
	}
	public List<Id> getDstIds() {
		return dstIds;
	}
	public void addDstId(Id dstId) {
		this.dstIds.add(dstId);
	}

}
