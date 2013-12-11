package main.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import processing.core.PApplet;

import main.commons.Const;
import main.commons.Displayable;
import main.commons.Location;
import main.commons.Subscribable;
import main.data.Data;
import main.data.factory.DataFactory;
import main.data.manager.DataManager;
import main.graph.Graph;
import main.id.Id;
import main.id.factory.DecimalIdFactory;
import main.id.factory.IdFactory;
import main.line.Line;
import main.line.factory.LIneFactory;
import main.node.Node;
import main.node.factory.NodeFactory;
import main.node.factory.ProcessingNodeFactory;
import main.node.factory.SensorFactory;
import main.node.factory.SubscriberFactory;
import main.node.nodeimpl.ProcessingNode;
import main.node.nodeimpl.SensorNode;
import main.node.nodeimpl.Subscriber;
import main.subscription.Subscription;
import main.subscription.factory.SubscriptionFactory;
import main.subscription.impl.CalculateSubscription;
import main.subscription.type.CalculationType;
import main.subscription.type.FilterType;



public class Controller {
	private static PApplet core;
	

	/*** Better to configure with config file ***/
	private static List<Location> nodeLocationList = Arrays.asList(new Location(Const.FULLPANELWIDTH/8,Const.FULLPANELHEIGHT/4),new Location(Const.FULLPANELWIDTH/2,Const.FULLPANELHEIGHT/4-Const.FULLPANELHEIGHT/30),
			new Location(Const.FULLPANELWIDTH/10,Const.FULLPANELHEIGHT*4/5),new Location(Const.FULLPANELWIDTH*5/13,Const.FULLPANELHEIGHT*9/13),new Location(Const.FULLPANELWIDTH*7/13,Const.FULLPANELHEIGHT*3/4));//tmp
	private static String[][] Connection = {{"0","1"},{"1","3"},{"0","3"},{"1","4"},{"2","3"},{"3","4"}};//tmp
	private static List<Location>  sensorLocationList = Arrays.asList(new Location(Const.FULLPANELWIDTH/13,Const.FULLPANELHEIGHT*3/13), new Location(Const.FULLPANELWIDTH/13-Const.FULLPANELWIDTH/130,Const.FULLPANELHEIGHT*11/14));
	private static List<String> sensordest = Arrays.asList("0","2");
	private static List<Location> subscriberLocationList = Arrays.asList(new Location(Const.FULLPANELWIDTH*9/13, Const.FULLPANELHEIGHT*9/13));
	
	/***end configuration data***/
	
	private static NodeFactory processingNodeFactory;
	private static List<Id> nodeIdList;
	private static Map<String,Node> nodeMap;
	
	private static NodeFactory sensorFactory;
	private static List<Id> sensorIdList;
	
	private static SubscriberFactory subscriberFactory;
	private static List<Id> subscriberIdList;
	
	private static SubscriptionFactory subscriptionFactory;
	private static Map<String, Subscription> subscriptionMap;
	
	private static LIneFactory lineFactory;
	private static List<Line> lineList;
	
	private static List<Data> dataList;
	private static DataManager dataMgr;
	private static Timer dataGeneraterTimer = new Timer(true);
	private static Timer subscriptionMoveTimer = new Timer(true);
	private static Subscription movingSubscription;
    private static List<Graph> graphList;
    private static IdFactory idFactory;
	 
	public Controller(PApplet core) {
		this.core = core;
	}
	
	public void init(){
		idFactory = new DecimalIdFactory();
		nodeMap = new HashMap<String, Node>();
		subscriptionMap  = new HashMap<String, Subscription>();
 		dataList = new LinkedList<Data>(); 
		dataMgr = new DataManager(new DataFactory(core, new DecimalIdFactory() ));

		initProcessingNode();
		initSensor();
		initSubscriber();
		initLine();
		initSubscription();
		initGraph();
		dataGeneraterTimer.schedule(createDataGenerator(),500, 300);
		subscriptionMoveTimer.schedule(createSubscriptionMover(), 10000, 10000);
	}
	
	private void initProcessingNode(){
		processingNodeFactory = new ProcessingNodeFactory(core, idFactory);
		nodeIdList = new ArrayList<Id>();
		for(int i=0;i<nodeLocationList.size();i++){
			ProcessingNode newNode = (ProcessingNode)processingNodeFactory.create(nodeLocationList.get(i));
			Id newId = newNode.getId();
			nodeIdList.add(newId);
			nodeMap.put(newId.getId(), newNode);
		}
	}
	
	private void initSensor() {
		sensorFactory = new SensorFactory(core, idFactory);
		sensorIdList = new ArrayList<Id>();
		for(int i=0 ;i<sensorLocationList.size();i++){
			SensorNode newSensor = (SensorNode)sensorFactory.create(sensorLocationList.get(i));
			Id newId = newSensor.getId();
			sensorIdList.add(newId);
			newSensor.addDstId(nodeMap.get(sensordest.get(i)).getId());
			nodeMap.put(newId.getId(), newSensor);
		}
	}
	
	private void initSubscriber(){
		subscriberIdList = new ArrayList<Id>();
		subscriberFactory = new SubscriberFactory(core, idFactory);
		for(int i=0 ;i<subscriberLocationList.size();i++){
			Subscriber newSusscriber = (Subscriber)subscriberFactory.create(subscriberLocationList.get(i));
			Id newId = newSusscriber.getId();
			subscriberIdList.add(newId);
			nodeMap.put(newId.getId(), newSusscriber);
		}
	}
	
	private void initLine() {
		lineFactory = new LIneFactory(core);
		lineList = new ArrayList<Line>();
		for (String[] pair : Connection) {
			lineList.add(lineFactory.create(nodeMap.get(pair[0]).getLocation(), nodeMap.get(pair[1]).getLocation()));
		}
	}
	
	/*** messy only for specific use case ***/
	private void initSubscription(){
		List<String> edgeOnNode = Arrays.asList("2", "0");
		List<String> edgeIds = new ArrayList<String>();
		List<String> jointOnNode = Arrays.asList("3", "3");
		List<String> jointIds = new ArrayList<String>();
		List<String> rootOnNode = Arrays.asList("4");
		List<String> rootIds = new ArrayList<String>();
		List<String> onSubscrber = Arrays.asList("7");
		subscriptionFactory = new SubscriptionFactory(core, new DecimalIdFactory());
		//edges
		for (String id : edgeOnNode) {
			Subscribable node = (Subscribable) nodeMap.get(id);
			Subscription subscription = subscriptionFactory.createFilter(((Node)node).getId(), new ArrayList<Id>(), new ArrayList<Id>(), FilterType.GREATER_EQ, (float)20.0);
			subscriptionMap.put(subscription.getSubscriptionId().getId(), subscription);
			edgeIds.add(subscription.getSubscriptionId().getId());
			node.addSubscription(subscription);
		}
		//joint
		for (int i = 0; i < jointOnNode.size(); i++) {
			String id = jointOnNode.get(i);
			Subscribable node = (Subscribable) nodeMap.get(id);
			Subscription subscription = subscriptionFactory.createCalculator(((Node)node).getId(), new ArrayList<Id>(), new ArrayList<Id>(), CalculationType.AVG, (int)10.0);
			Subscription childSub = subscriptionMap.get(edgeIds.get(i));
			subscription.addChilds(childSub.getSubscriptionId());
			childSub.addParents(subscription.getSubscriptionId());
			jointIds.add(subscription.getSubscriptionId().getId().toString());
			subscriptionMap.put(subscription.getSubscriptionId().getId().toString(), subscription);
			node.addSubscription(subscription);		
			movingSubscription = subscription;
		}
		//root
		for (String id : rootOnNode) {
			Subscribable node = (Subscribable) nodeMap.get(id);
			Subscription subscription = subscriptionFactory.createFilter(((Node)node).getId(), new ArrayList<Id>(), new ArrayList<Id>(), FilterType.GREATER_EQ, (float)20.0);
			for (String jointId : jointIds) {
				Subscription childSub = subscriptionMap.get(jointId);
				subscription.addChilds(childSub.getSubscriptionId());
				childSub.addParents(subscription.getSubscriptionId());
			}
			rootIds.add(subscription.getSubscriptionId().getId().toString());
			subscriptionMap.put(subscription.getSubscriptionId().getId().toString(), subscription);
			node.addSubscription(subscription);			
		}
		//subscriber
		for (String id : onSubscrber) {
			Subscribable node = (Subscribable) nodeMap.get(id);
			Subscription subscription = subscriptionFactory.createFilter(((Node)node).getId(), new ArrayList<Id>(), new ArrayList<Id>(), FilterType.GREATER_EQ, (float)100);
			for (String rootId : rootIds) {
				Subscription childSub = subscriptionMap.get(rootId);
				subscription.addChilds(childSub.getSubscriptionId());
				childSub.addParents(subscription.getSubscriptionId());
			}
			subscriptionMap.put(subscription.getSubscriptionId().getId().toString(), subscription);
			node.addSubscription(subscription);			
		}
	}
	
	private void initGraph() {
		graphList = new ArrayList<Graph>();
		for (Id id : nodeIdList) {
			graphList.add(new Graph(core,nodeMap.get(id.getId())));
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void display(){
		for (Displayable line : lineList) {
			line.display();
		}
		
		for (Displayable node : nodeMap.values()) {
			node.display();
		}
		for (Graph graph : graphList) {
			graph.display();
		}
		synchronized (dataList) {
			
			Iterator itr = dataList.iterator();
			while(itr.hasNext()){
				Data data = (Data)itr.next();
				if(!preProcess(data)){
					itr.remove();
				}else{
					data.display();
					data.move();
				}
			}
		}
	}
	
	public boolean preProcess(Data data){
		if(data.isArrived()){
			Subscribable node = (Subscribable)nodeMap.get(data.getDstId().getId());
			node.receive(data);
			for (Subscription subscription : node.getSubscriptions()) {
				if(subscription.process(data)){
					return routeData(data, subscription);
				}else{
					dataMgr.remove(data);
					return false;//filtered
				}
			}
			dataMgr.remove(data);
			return false;//no subscription
		}
		
		return true;//not arrived
	}

	private boolean routeData(Data data, Subscription subscription){
		for (Id parentSubId : subscription.getParents()) {
			Subscription dstSubscription = subscriptionMap.get(parentSubId.getId());
			Node dstNode = nodeMap.get(dstSubscription.getNodeId().getId());
			data.init(data.getDstId(), dstNode.getId(), data.getDstLocation(), dstNode.getLocation());
			return true; //tmp need to clone data actually
		}
		return false;
	}
	
	private void moveSubscripiton(Subscription sub){
		Id currentNodeId = sub.getNodeId();
		ProcessingNode currentNode = (ProcessingNode)nodeMap.get(currentNodeId.getId());
		if(currentNodeId.getId().equals("1")){
			moveSubscripiton(sub, (ProcessingNode)nodeMap.get(currentNodeId.getId()), (ProcessingNode)nodeMap.get("3"));
    	}else{//node on 3
    		moveSubscripiton(sub, (ProcessingNode)nodeMap.get(currentNodeId.getId()), (ProcessingNode)nodeMap.get("1"));
    		currentNode.removeSubscription(sub);
    		((ProcessingNode)nodeMap.get("1")).addSubscription(sub);
    	}
	}
	private void moveSubscripiton(Subscription sub, ProcessingNode currentNode, ProcessingNode nextNode){
		currentNode.removeSubscription(sub);
		nextNode.addSubscription(sub);
		sub.setNodeId(nextNode.getId());
		synchronized (dataList) {
			dataList.remove(((CalculateSubscription)sub).getBuffered());	
		}
	}
	
	private void generateData(){
    	synchronized (dataList) {
	    	for (Id sensorId : sensorIdList) {
	    		generateDataForEachSenseor((SensorNode) nodeMap.get(sensorId.getId()));
			}
	    }
	}
	
	private void generateDataForEachSenseor(SensorNode sensor){
		if(sensor.isActive()) {
			for(Id dstId : sensor.getDstIds()){
				Node node = nodeMap.get(dstId.getId());
				if(node != null &&node.getId().equals(dstId)){
					Data newData = dataMgr.generate(sensor, node);
					sensor.send(newData);
					dataList.add(newData);
				}
			}
		}
	}
	
	private TimerTask createSubscriptionMover(){
		return new TimerTask() {
			Subscription sub = movingSubscription;
            @Override
            public void run() {
            	moveSubscripiton(sub);
            	
            }
		};
	}
	
	private TimerTask createDataGenerator(){
		return new TimerTask() {
            @Override
            public void run() {
            	generateData();
            }
		};
	}
}
