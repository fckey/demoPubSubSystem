package main.textbox;

import java.util.List;

import main.commons.Const;
import main.commons.Displayable;
import main.commons.Location;
import processing.core.PApplet;

public class TextBox implements Displayable {
	/* Text with node. */
	protected PApplet core;

	protected String   titleText;
	protected String[] textList;
	protected int      textColor;
	protected float      textBoxX;
	protected float      textBoxY;
	protected int      textBoxColor;
	protected int      textBoxWidth;
	protected int      textBoxHeight;
	protected int     WIDTH = Const.GRID / 50;
	protected int     HEIGHT = Const.GRID / 15;
	
	public TextBox(PApplet core,String title, Location loc, List<String> strArray) {
		this.core = core;
		this.titleText = title;
		textColor     = core.color(10);
		textBoxColor  = core.color(34,32,255,255);
		textBoxHeight = 60;
		textBoxWidth  = 100;
		textBoxX      = loc.getX() - WIDTH;
		textBoxY      = loc.getY() - HEIGHT;
		textList     =  (String[]) strArray.toArray();
		
	}
	
	
	@Override
	public void display() {
		core.stroke(1);
		core.fill(textBoxColor);
		core.rect(textBoxX, textBoxY, textBoxWidth, textBoxHeight);
		core.fill(1);
		for (int i = 0; i < textList.length; i++){
			core.text(textList[i], textBoxX+5, textBoxY+15*(i+1));
		}
	}

	public void setText(String text){
		textList[textList.length-1]= text;
	}
	

	public void setTextBoxColor(int textBoxColor) {
		this.textBoxColor = textBoxColor;
	}

	
}

