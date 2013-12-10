package main.node.nodeimpl;

import java.util.LinkedList;
import java.util.List;


import processing.core.PApplet;

import main.commons.Const;
import main.commons.Displayable;
import main.commons.GetFilePath;
import main.commons.Location;
import main.commons.Sendable;
import main.commons.Subscribable;
import main.data.Data;
import main.id.Id;
import main.node.Node;
import main.subscription.Subscription;

public class ProcessingNode extends Node implements Sendable, Subscribable{
	protected int dataCount = 0;
	protected int prevDataNum = 0;
	protected int currentDataNum = 0;
	protected final List<Subscription> subscriptions;
	protected long latest;
	protected long currentTime;
	public ProcessingNode(PApplet core, Id id, Location location) {
		
		super(core, id, location);
//		this.textBox = new TextBox("Node"+this.id, this.core.color(0), this.location, "CPU");
		this.nodeImage = core.loadImage(GetFilePath.getPath(Const.PROJECT)+Const.PICTPATH+"pc.gif");
		currentTime = System.currentTimeMillis();
		subscriptions = new LinkedList<Subscription>();
	}

	@Override
	public void display() {
		core.noStroke();
		core.image(nodeImage, xEdge, yEdge,nodeWidth,nodeHeight);
		for (Displayable sub : subscriptions) {
			sub.display();
		}
		latest = System.currentTimeMillis();
		
		if(latest > currentTime+1200){
			currentDataNum = dataCount;
			if(currentDataNum > Const.NODETHRESHHOLD){
				textBox.setTextBoxColor(core.color(0,255,255,250));
			}else{
				textBox.setTextBoxColor(core.color(34,32,255,255));
			}
			textBox.setText(String.valueOf(currentDataNum)+ "(data/sec)");
			prevDataNum = dataCount;
			dataCount = 0;
			currentTime = latest;
		}
		textBox.display();
	}

	@Override
	public void receive(Data data) {
		dataCount++;
	}

	@Override
	public void send(Data data) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}
	
	@Override
	public void addSubscription(Subscription sub) {
		sub.setxEdge(xEdge);
		sub.setyEdge(yEdge);
		subscriptions.add(sub);
	}
	
	@Override
	public void removeSubscription(Subscription sub) {
		 subscriptions.remove(sub);
	}

	public int getRate(){
		return currentDataNum;
	}
	
	public long getCurrentTile(){
		return currentTime;
	}
}
