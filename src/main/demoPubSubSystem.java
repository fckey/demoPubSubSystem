package main;


import main.Controller.Controller;
import main.commons.*;
import processing.core.PApplet;
import processing.core.PImage;

public class demoPubSubSystem extends PApplet {
	private PImage Background;
	private Controller controller;
	
	public void setup(){
		colorMode(HSB);
		size(Const.FULLPANELWIDTH,Const.FULLPANELHEIGHT);
		Background = loadImage(GetFilePath.getPath(Const.PROJECT)+Const.PICTPATH+"map.png");
		Const.TIME = System.currentTimeMillis();
		controller = new Controller(this);
		controller.init();
	}
	
	public void draw(){
		image(Background, 0, 0);
		
		controller.display();
	}
}
