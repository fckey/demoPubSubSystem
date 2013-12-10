package main.data;


import processing.core.PApplet;
import main.commons.Const;
import main.commons.Displayable;
import main.commons.Location;
import main.data.manager.DataManager;
import main.id.Id;

public class Data implements Displayable {
	protected PApplet core;
	protected Id dataId;
	protected Id srcdId;
	protected Id dstId;
	protected Location srcLocation;
	
	protected Location dstLocation;
	protected Location current;
	
	protected float radius = 12;
	protected int radiusThreshhold = 20;
	protected float heightOfData = 2;
	protected float widthOfData = 3/4;
	protected int dataColor;
	protected double value;
	protected float xVelocity;
	protected float yVelocity;
	
	protected boolean canMove = true;
	protected boolean isProcessed = false;
	
	protected static DataManager dataMgr =null;
	
	public Data(PApplet core, Id id){
		this.core = core;
		this.dataId =id;
	}


	public void init(Id srcId,Id dstId, Location src, Location dst, double value) {
		this.srcdId = srcId;
		this.dstId = dstId;
		this.current = new Location(src.getX(), src.getY());
		this.srcLocation = src;
		this.dstLocation = dst;
		this.value = value*100 % Const.MAXDATAVALUE;
		xVelocity = (float) ((dstLocation.getX() - srcLocation.getX()) * Const.DATASPEED);
		yVelocity = (float) ((dstLocation.getY() - srcLocation.getY()) * Const.DATASPEED);
		isProcessed = false;
		dataColor = core.color(0, 255, 255, 250);
	}	
	public void init(Id srcId,Id dstId, Location src, Location dst) {
		this.srcdId = srcId;
		this.dstId = dstId;
		this.current = new Location(src.getX(), src.getY());
		this.srcLocation = src;
		this.dstLocation = dst;
		xVelocity = (float) ((dstLocation.getX() - srcLocation.getX()) * Const.DATASPEED);
		yVelocity = (float) ((dstLocation.getY() - srcLocation.getY()) * Const.DATASPEED);
//		dataColor = core.color(0, 255, 255, 250);
	}
	
	@Override
	public void display() {
		core.noStroke();
		core.fill(dataColor);
		if(radius > radiusThreshhold){
			core.ellipse(current.getX(), current.getY(), radius*widthOfData, radius*heightOfData);
		} else {
			core.ellipse(current.getX(), current.getY(), radius, radius);
		}
	}
	
	public void move(){
		if(canMove){
			current.setX( current.getX() + xVelocity);
			current.setY( current.getY() + yVelocity);
		}
	}

	public void routing(Id newDstId,Location src, Location dst){
		init(dstId, newDstId,  src, dst);
	}
	public Id getDataId() {
		return dataId;
	}
	public double getValue(){
		return value;
	}
	
	public void setValue(Double val){
		this.value = val;
	}
	
	public void setCanMove(boolean flag){
		canMove = flag;
	}
	
	public boolean isProcessed(){
		return isProcessed;
	}
	
	public void setIsProcessed(boolean flag){
		isProcessed = flag;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
	}
	
	public float getRadius() {
		return radius;
	}

	public void setDataColor(int dataColor) {
		this.dataColor = dataColor;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public boolean isArrived(){
		if(current.getX() > dstLocation.getX()-1  && current.getY() > dstLocation.getY()- 1){
			return true;
		}else{
			return false;
		}
	}

	public Id getSrcdId() {
		return srcdId;
	}

	public Id getDstId() {
		return dstId;
	}

	public void setxVelocity(float xVelocity) {
		this.xVelocity = xVelocity;
	}

	public void setyVelocity(float yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	public Location getSrcLocation() {
		return srcLocation;
	}

	public Location getDstLocation() {
		return dstLocation;
	}

	public void setSrcLocation(Location srcLocation) {
		this.srcLocation = srcLocation;
	}

	public void setDstLocation(Location dstLocation) {
		this.dstLocation = dstLocation;
	}

}
