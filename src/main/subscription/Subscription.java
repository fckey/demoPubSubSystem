package main.subscription;

import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;
import processing.core.PImage;

import main.commons.Const;
import main.commons.Displayable;
import main.commons.Subscribable;
import main.data.Data;
import main.id.Id;

public abstract class Subscription implements Displayable {
	protected PApplet core; 
	protected final List<Id> parents = new ArrayList<Id>();
	protected final List<Id> childs= new ArrayList<Id>();
	protected Id nodeId;
	protected Id subscriptionId;
	
	protected float  xEdge;
	protected float  yEdge;
	protected  int nodeWidth = Const.FULLPANELHEIGHT/20;
	protected  int nodeHeight = Const.FULLPANELHEIGHT/20;
		
	protected PImage subscriptionImage = null;
	public Subscription(PApplet core,Id subId, Id nodeId, List<Id> parents, List<Id> childs) {
		this.core = core;
		this.nodeId = nodeId;
		this.parents.addAll(parents);
		this.childs.addAll(childs);
		this.subscriptionId =subId;
	}
	public abstract boolean process(Data data);
	
	public void display() {
		core.noStroke();
		core.image(subscriptionImage, xEdge, yEdge,nodeWidth,nodeHeight);
		
	}
	
	public float getxEdge() {
		return xEdge;
	}
	public float getyEdge() {
		return yEdge;
	}
	public void setxEdge(float xEdge) {
		this.xEdge = xEdge + (nodeWidth/2);
	}
	public void setyEdge(float yEdge) {
		this.yEdge = yEdge + (nodeHeight/2);
	}
	
	public Id getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(Id subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public Id getNodeId() {
		return nodeId;
	}
	public void setNodeId(Id nodeId) {
		this.nodeId = nodeId;
	}
	public List<Id> getParents() {
		return parents;
	}
	public List<Id> getChilds() {
		return childs;
	}
	public void addParents(Id parent) {
		this.parents.add(parent);
	}
	public void addChilds(Id child) {
		this.childs.add(child);
	}
	
	public void removeParents(Subscription parent) {
		this.parents.remove(parent);
	}
	public void removeChilds(Subscription child) {
		this.childs.remove(child);
	}
}
