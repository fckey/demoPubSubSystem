package main.node.nodeimpl;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import processing.core.PApplet;
import main.commons.Const;
import main.commons.GetFilePath;
import main.commons.Location;
import main.commons.Subscribable;
import main.data.Data;
import main.id.Id;
import main.node.Node;
import main.subscription.Subscription;
import main.textbox.TextBox;

public class Subscriber extends Node implements Subscribable{

	protected final List<Subscription> subscriptions;
	public Subscriber(PApplet core, Id id, Location loc) {
		super(core, id, loc);
		this.nodeImage = core.loadImage(GetFilePath.getPath(Const.PROJECT)+Const.PICTPATH+"phone.jpg");
		nodeWidth = nodeWidth * 5/8;
		subscriptions = new LinkedList<Subscription>();
		textBox = new TextBox(core, "Subscriber", loc, Arrays.asList("Received","Temperature", ""));
	}

	@Override
	public void display() {
		core.noStroke();
		core.image(nodeImage, xEdge, yEdge,nodeWidth,nodeHeight);
		textBox.display();
	}

	@Override
	public void receive(Data data) {
		textBox.setText(String.format("%2f",data.getValue()));
	}

	@Override
	public List<Subscription> getSubscriptions() {
		return subscriptions;
	}

	@Override
	public void addSubscription(Subscription sub) {
		 subscriptions.add(sub);
	}
	@Override
	public void removeSubscription(Subscription sub) {
		 subscriptions.remove(sub);
	}
}
