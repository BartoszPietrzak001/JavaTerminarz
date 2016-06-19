package application;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import com.thoughtworks.xstream.XStream;

public class ApplicationSettings {
	
	private static LocalDate localDate;
	private static String filePath;
	private static boolean defaultCustom;
	
	private static String login;
	
	public static LocalDate getDate(){
		return localDate;
	}
	
	public static boolean getDefaultCustom(){
		return defaultCustom;
	}
	
	public static void setDefaultCustom(boolean value){
		defaultCustom = value;
	}
	
	public static void setDate(LocalDate date){
		localDate = date;
	}
	
	public static String getFilePath(){
		return filePath;
	}
	
	public static void setFilePath(String path){
		filePath = path;
	}
	
	public static String getLogin(){
		return login;
	}
	
	public static void setLogin(String l){
		login = l;
	}
	


}
