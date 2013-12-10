package main.subscription.impl;

import java.util.List;

import processing.core.PApplet;

import main.commons.*;
import main.data.Data;
import main.id.Id;
import main.subscription.Subscription;
import main.subscription.type.CalculationType;
import main.subscription.type.FilterType;

public class CalculateSubscription extends Subscription{
	
	protected Data bufferData = null;
	protected double bufferValue = 0;
	protected int count = 0;
	protected float radius;
	protected int threshhold;
	protected CalculationType operatorType;
	public CalculateSubscription(PApplet core,Id subId, Id nodeId, List<Id> parents, List<Id> chils, int threshhold, CalculationType operaterType) {
		super(core, subId, nodeId, parents, chils);
		this.operatorType = operaterType;
		subscriptionImage = core.loadImage(GetFilePath.getPath(Const.PROJECT)+Const.PICTPATH+"calc.jpg");
		this.threshhold = threshhold;
	}

	@Override
	public boolean process(Data data) {
		boolean flag = false;
		
//		
		if(bufferData == null){
			bufferData = data;
			bufferData.setCanMove(false);
			bufferData.setIsProcessed(true);
			radius = bufferData.getRadius();
			bufferValue = 0;
			count = 0;
			
			flag = true;
		}
		if(data.getDataId().equals(bufferData.getDataId())){
			flag = true;
		}
//		radius += 10;
		bufferData.setDataColor(core.color(134,132,205,count * 30));
		bufferData.setRadius(20);//bufferData.getRadius());

		if(calculate(data)){
			bufferData.setCanMove(true);
			bufferData = null;
		}
		
		return flag; 
		
	}
	
	protected void setVelocity(float x, float y){
		bufferData.setxVelocity(x);
		bufferData.setyVelocity(y);
	}
	
	protected boolean calculate(Data data){
		switch(operatorType) {
        case AVG:
            return calcAvg(data);
//        case MIN:
//            return calcMIN(data);
//        case MAX:
//            return calcMAX(data);
//        case COUNT:
//            return calcCOUNT(data);
        case SUM:
            return calcSum(data);
		default:
			return false;
		}
	}
    private boolean calcAvg(Data data) {
        bufferValue += data.getValue();
        count++;
        Data lastD = null;
        if(count > threshhold){
        	bufferData.setValue(bufferValue/threshhold);
        	return true;
        }
        return false;
    }
    
    private boolean calcSum(Data data) {
        bufferValue += data.getValue();
        count++;
        Data lastD = null;
        if(count > threshhold){
        	bufferData.setValue(bufferValue);
        	return true;
        }
        return false;
    }
 
    public Data getBuffered(){
    	return bufferData;
    }
}
