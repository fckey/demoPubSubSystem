package main.commons;

import java.io.File;

public class GetFilePath {
	public static String  getPath(String key){
		String abspath;
		String path = "";
		String[] parts;
		 File objFile = new File("");
		 abspath = objFile.getAbsolutePath();
		 parts = abspath.split("/");
		 for(String part : parts){
			 path = path + part + "/" ;
			 if(part.equals(key)){
				 break;
			 }
		 }
		 return path;
	}
	
	public static String  getPath(){
		String abspath;
		String path = "";
		String[] parts;
		 File objFile = new File("");
		 abspath = objFile.getAbsolutePath();
		 parts = abspath.split("/");
		 for(String part : parts){
			 path = path + part + "/" ;
		 }
		 return path;
	}
}
