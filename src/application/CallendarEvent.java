package application;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallendarEvent {
	private Date eventDate;
	private String description = "";
	private String venue = "";
	
	@SuppressWarnings("deprecation")
	public CallendarEvent(String venue, int day, int month, int year, int hour, int minute, String description){
		this.description="";
		eventDate = new Date(year, month, day, hour, minute);
		this.description = description;
		this.venue = venue;
	}
	
	public CallendarEvent (int day, int month, int year, int hour, int minute, String description){
		this("venue not fixed", day, month, year, hour, minute, description);
	}
	
	public CallendarEvent (int day, int month, int year){
		this("venue not fixed", day, month, year, 0, 0, "Arrangement");
	}
	
	public CallendarEvent (int day, int month, int year, int hour, int minute){
		this("venue not fixed", day, month, year, hour, minute, "Arrangement");
	}
	
	public CallendarEvent (int day, int month, int year, int hour){
		this("venue not fixed", day, month, year, hour, 0, "Arrangement");
	}
	
	public CallendarEvent (int day, int month, int year, int hour, String description){
		this("venue not fixed", day, month, year, hour, 0, description);
	}
	
	public CallendarEvent (int day, int month, int year, String description){
		this("venue not fixed", day, month, year, 0, 0, description);
	}
	
	public CallendarEvent (String venue, int day, int month, int year, int hour, String description){
		this(venue, day, month, year, hour, 0, description);
	}
	
	public CallendarEvent(String venue, int day, int month, int year, String description){
		this(venue, day, month, year, 0, 0, description);
	}
	
	public CallendarEvent(String venue, int day, int month, int year){
		this(venue, day, month, year, 0, 0, "Arrangement");
	}
	
	public CallendarEvent(String venue, int day, int month, int year, int hour){
		this(venue, day, month, year, 0, 0, "Arrangement");
	}
	
	public CallendarEvent(String venue, int day, int month, int year, int hour, int minute){
		this(venue, day, month, year, hour, minute, "Arrangement");
	}
	

	
	public void setDate(Date d){
		eventDate = d;
	}
	
	public Date getDate(){
		return eventDate;
	}
	
	public void setDescription(String d){
		description = d;
	}
	
	public String getDescription(){
		return description;
	}
	
	public String getVenue(){
		return venue;
	}
	
	public void setVenue(String v){
		venue = v;
	}
	
	@Override
	public String toString(){
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(eventDate);
		return "Date: " + s + " Description: " + description + " Venue: " + venue;
	}

}
