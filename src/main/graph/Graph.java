package main.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import main.commons.Const;
import main.commons.Displayable;
import main.graph.manager.GraphPartsManager;
import main.graph.parts.GraphData;
import main.graph.parts.GraphLine;
import main.node.Node;
import main.node.nodeimpl.ProcessingNode;

public class Graph implements Displayable{
	protected PApplet core;
	protected float baseXpos;
	protected float baseYpos;
	protected float baseWidth;
	protected float baseHeight;
	protected int baseColor;
	protected float panelXpos;
	protected float panelYpos;
	protected static float panelWidth;
	protected float panelHeight;
	protected float panelColor;
	protected float graphXStart;
	int textColor;
	String PrintText;
	ProcessingNode node;
	List<GraphData> dataSet = new LinkedList<GraphData>();
	List<GraphLine> lineSet = new LinkedList<GraphLine>();
	Long StartTime;
	Long NowTime;
	protected GraphPartsManager partsMgr;
	double threshhold;
	float threshholdStartX;
	float threshholdendX;
	float threshholdYpos;
	
	static int WIDTH = Const.FULLPANELWIDTH*4/5;
	static int HEIGHT = Const.FULLPANELHEIGHT/Const.NODENUM;
	static int GAPWIDTH = WIDTH/70;
	static float DATAHEIGHTBASE =0;//データが来たら最低限上がる高さ
	static float DATAMOVERATE;//1データにつき上がる高さ

	
	public Graph(PApplet core,Node newNode){
		this.core = core;
		this.node = (ProcessingNode)newNode;
		baseXpos = WIDTH;
		baseYpos = Integer.valueOf(this.node.getId().getId()) *HEIGHT;
		baseWidth = core.width-baseXpos;
		baseHeight = HEIGHT;
		baseColor = core.color(150,100,200,255);
		panelXpos = baseXpos + GAPWIDTH;
		panelYpos = baseYpos + GAPWIDTH;
		panelWidth = baseWidth -GAPWIDTH*2;
		panelHeight = baseHeight -GAPWIDTH*2;
		graphXStart = panelXpos+panelWidth;
		panelColor = core.color(134,100,32,155);
		StartTime = System.currentTimeMillis();
		partsMgr = new GraphPartsManager(core, graphXStart);
		/*threshhold*/
		threshhold = panelHeight*0.65;
		threshholdStartX = panelXpos;
		threshholdendX = panelXpos+panelWidth;
		threshholdYpos = (panelYpos+panelHeight) - Float.parseFloat(Double.toString(threshhold));
		
		/*text*/
		PrintText = "DataProcessor"+node.getId().getId();
		textColor = core.color(34,32,255,255);
		DATAMOVERATE = panelHeight/12;
	}
	
	public void display() {
		/*base*/
		core.stroke(core.color(34,32,255,255));
		core.fill(baseColor);
		core.rect(baseXpos,baseYpos,baseWidth,baseHeight);
		/*panel*/
		core.stroke(core.color(34,32,255,255));
		core.fill(panelColor);
		core.rect(panelXpos,panelYpos,panelWidth,panelHeight);
		/*threshold line*/
		core.stroke(core.color(0,255,255,250));
		core.strokeWeight(2);
		core.line(threshholdStartX,threshholdYpos,threshholdendX,threshholdYpos);
		
		/*text*/
		core.fill(textColor);
		core.text(PrintText,panelXpos+(panelWidth/2)-50,(panelYpos),100,500);
		int datasize = dataSet.size();
		if(!dataSet.isEmpty()){//
			int linesize = lineSet.size();
			for(int i=0;i < datasize;i++){
				GraphData data = dataSet.get(i);
				if(data.getXpos()<panelXpos){
					dataSet.remove(i);
					lineSet.remove(i);
					datasize = dataSet.size();
					linesize = lineSet.size();
					continue;
				}
				if(linesize > i){
					lineSet.get(i).display();
				}
				data.display();
			}
		}

		if(node.getCurrentTile() -StartTime > 2000){
			for(GraphData data : dataSet){
				data.update();	
			}
			makeData();
			datasize = dataSet.size();
			if(datasize>=2){
				makeLine(datasize);
			}
			StartTime = node.getCurrentTile();
		}

	}
	
	void makeData(){
		float rate = node.getRate();
		float yPos = panelYpos+panelHeight-3;		
		GraphData newdata;
		if(rate>0){
			 yPos = (yPos-DATAHEIGHTBASE)-DATAMOVERATE*rate;
		}
		newdata = partsMgr.createData(yPos);
		if(panelYpos+panelHeight -yPos > threshhold){

			baseColor = core.color(0,255,255,250);
		}else{
			baseColor = core.color(150,100,200,255);
		}
		dataSet.add(newdata);
	}
	void makeLine(int datanum){
		
		lineSet.add(partsMgr.createLine(dataSet.get(datanum-2), dataSet.get(datanum-1)));
	}
	void removeObjset(int i){
		dataSet.remove(i);
		lineSet.remove(i);
	}
	void updateData(){
		for(GraphData data : dataSet){
			data.update();
		}
	}
	
	public static float getPanelWidth(){
		return panelWidth;
	}
}
