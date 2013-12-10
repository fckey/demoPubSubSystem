package main.commons;

import main.data.Data;

public interface Receivable {
	/*
	 * return true if the data is still available
	 */
	Data receive(Data data);
}
