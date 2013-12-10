package main.data.manager;

import java.util.LinkedList;
import java.util.List;

import com.sun.xml.internal.ws.wsdl.writer.UsingAddressing;

import main.commons.Const;
import main.data.Data;
import main.data.factory.DataFactory;
import main.node.Node;

public class DataManager {
	private static List<Data> dataStockList;
	private static List<Data> dataUsingList;
	private static DataFactory dataFactory;
	private static final int INIT_DATA_NUM = 20;
	
	public DataManager(DataFactory dataFactory) {
		dataStockList = new LinkedList<Data>();
		dataUsingList = new LinkedList<Data>();
		this.dataFactory = dataFactory;
		for(int i =0;i<INIT_DATA_NUM;i++){
			dataStockList.add(dataFactory.create());
		}
	}
	
	public Data generate(Node srcNode, Node dstNode){
		Data data;
		synchronized (dataStockList) {
			if(dataStockList.isEmpty()){
				data = dataFactory.create(srcNode, dstNode);
				dataUsingList.add(data);
			}else{
				data = getIdleData();
				data.init(srcNode.getId(), dstNode.getId(), srcNode.getLocation(), dstNode.getLocation(), Math.random() % Const.MAXDATAVALUE);
			}
			return data;
		}
	}

	public void remove(Data data){
		dataUsingList.remove(data);
		dataStockList.add(data);
	}
	
	private void setIdleData(Data data){
		dataStockList.add(data);
	}

	private Data getIdleData(){
		Data data;
		data = dataStockList.remove(0);
		dataUsingList.add(data);
		return data;
	}
	
}
