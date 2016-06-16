package application;

import java.time.LocalDate;

public class ApplicationSettings {
	
	private static LocalDate localDate;
	private static String filePath = "Users.sqlite";
	
	public static LocalDate getDate(){
		return localDate;
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

}
