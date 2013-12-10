package main.commons;

import java.util.List;

import main.data.Data;
import main.subscription.Subscription;

public interface Subscribable {
	
	void receive(Data data);
	
	List<Subscription> getSubscriptions();
	
	void addSubscription(Subscription sub);
	
	void removeSubscription(Subscription sub);
}
