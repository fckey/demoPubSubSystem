package main.commons;


public class Const {
	
	public final static String PROJECT = "demoPubSubSystem";
	public final static String PICTPATH = "resources/pict/";

	public final static int FULLPANELWIDTH = 1450;
	public final static int FULLPANELHEIGHT = 850;
	public final static int NODENUM =5;
	public final static int SENSORNUMM = 2;
	public final static int LINENUM = 6;
	public static long TIME;
	public final static double DATASPEED = 0.02;
	
	

	public final static int GRID = 1024;

	public final static int WINDOW = 1200;
	public final static int BINDIGIT = 4;
	public final static int DIVGRID = (int)Math.pow(2, BINDIGIT);
	public final static int CELL = GRID / DIVGRID;
	public final static int ZLENGTH = 8;

	public final static int MAXDATAVALUE = 40; 
	public final static int TEXTSIZE = 18;
	public final static int NODETHRESHHOLD = 7;
}
