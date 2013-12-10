package main.subscription.impl;

import java.util.List;

import processing.core.PApplet;

import main.commons.*;
import main.data.Data;
import main.id.Id;
import main.subscription.Subscription;
import main.subscription.type.FilterType;

public class FilterSubscription extends Subscription {

	protected float threshhold;
	protected FilterType operatorType;
	
	public FilterSubscription(PApplet core, Id subId, Id nodeId, List<Id> parents, List<Id> childs, float threshhold,  FilterType operatorType) {
		super(core, subId, nodeId, parents, childs);
		this.operatorType = operatorType; 
		subscriptionImage = core.loadImage(GetFilePath.getPath(Const.PROJECT)+Const.PICTPATH+"filter.jpg");
	}

	@Override
	public boolean process(Data data) {
		if(!filter(data)){
			return false;
		}
		return true;
	}
	
	private boolean filter(Data data){
		if(operatorType == FilterType.LESS_THAN){
			return data.getValue() < threshhold;
		}else if(operatorType == FilterType.LESS_EQ){
			return data.getValue() <= threshhold;
		}else if(operatorType == FilterType.GREATER_THAN){
			return data.getValue() > threshhold;
		}else if(operatorType == FilterType.GREATER_EQ){
			return data.getValue() >= threshhold;
		}else if(operatorType == FilterType.EQ){
			return data.getValue() == threshhold;
		}else{
			return false;
		}
	}

}
