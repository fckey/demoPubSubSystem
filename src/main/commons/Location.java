package main.commons;

public class Location {
	
	protected float x;
	protected float y;
	
	public Location(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public void setX(float xLocation) {
		this.x = xLocation;
	}
	public void setY(float yLocation) {
		this.y = yLocation;
	}
	
}
