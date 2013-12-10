package main.graph.manager;

import java.util.LinkedList;
import java.util.List;

import main.data.Data;
import main.graph.factory.GraphDataFactory;
import main.graph.factory.GraphLineFactory;
import main.graph.parts.GraphData;
import main.graph.parts.GraphLine;


import processing.core.PApplet;

public class GraphPartsManager {
	private static  PApplet core;
	private static List<GraphData> dataStockList;
	private static List<GraphData> dataUsingList;
	private float xPos;
	private static List<GraphLine> lineStockList;
	private static List<GraphLine> lineUsingList;
	private GraphDataFactory dataFactory;
	private GraphLineFactory lineFactory;
	
	public GraphPartsManager(PApplet core, float xPos) {
		this.core = core;
		this.xPos = xPos;
		dataStockList = new LinkedList<GraphData>();
		dataUsingList = new LinkedList<GraphData>();
		lineStockList = new LinkedList<GraphLine>();
		lineUsingList = new LinkedList<GraphLine>();
		dataFactory   = new GraphDataFactory(core, xPos); 
		lineFactory   = new GraphLineFactory(core);
	}
	
	public GraphData createData(float yPos){
		GraphData data;
		if(dataStockList.isEmpty()){
			data = dataFactory.create(yPos);
			dataUsingList.add(data);
		}else{
			data = getIdleData();
			data.init(xPos, yPos);
		}
		return data;
	}
	private GraphData getIdleData(){
		GraphData data;
		data = dataStockList.remove(0);
		dataUsingList.add(data);
		return data;
	}
	
	public void removeData(GraphData data){
		dataUsingList.remove(data);
		dataStockList.add(data);
	}
	
	public GraphLine createLine(GraphData pre, GraphData after){
		GraphLine line;
		if(lineStockList.isEmpty()){
			line = lineFactory.create(pre, after);
			lineUsingList.add(line);
		}else{
			line = getIdleLine();
			line.init(pre, after);
		}
		return line;
	}
	private GraphLine getIdleLine(){
		GraphLine line;
		line = lineStockList.remove(0);
		lineUsingList.add(line);
		return line;
	}
	
	public void removeLine(GraphLine line){
		lineUsingList.remove(line);
		lineStockList.add(line);
	}
}
