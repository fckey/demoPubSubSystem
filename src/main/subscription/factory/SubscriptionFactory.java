package main.subscription.factory;

import java.util.List;

import main.id.Id;
import main.id.factory.IdFactory;
import main.subscription.Subscription;
import main.subscription.impl.CalculateSubscription;
import main.subscription.impl.FilterSubscription;
import main.subscription.type.CalculationType;
import main.subscription.type.FilterType;
import processing.core.PApplet;

public class SubscriptionFactory {
	
	PApplet core;
	IdFactory idFactory;
	
	public SubscriptionFactory(PApplet core, IdFactory idFactory) {
		this.core = core;
		this.idFactory = idFactory;
	}
	
	public FilterSubscription createFilter(Id nodeId, List<Id> childs, List<Id> parents, FilterType op, float threshhold) {
		return new FilterSubscription(core, idFactory.create(), nodeId, parents, childs, threshhold, op);
	}
	
	public CalculateSubscription createCalculator(Id nodeId, List<Id> childs, List<Id> parents, CalculationType op, int threshhold) {
		return new CalculateSubscription(core, idFactory.create(), nodeId, parents, childs, threshhold, op);
	}
}
