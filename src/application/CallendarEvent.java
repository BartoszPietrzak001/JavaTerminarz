package application;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CallendarEvent {
	private LocalDateTime eventDate;
	private String description;
	private String venue;
	
	@SuppressWarnings("deprecation")
	public CallendarEvent(LocalDateTime localDateTime, String description, String venue){
		
		this.description = description;
		
		this.venue = venue;
		
		eventDate = localDateTime;
	}
	
	public CallendarEvent(CallendarEvent ce){
		this.description = ce.description;
		this.eventDate = ce.eventDate;
		this.venue = ce.venue;
	}
	
	public CallendarEvent(){}
	
	public void setDate(LocalDateTime d){
		eventDate = d;
	}
	
	public LocalDateTime getDate(){
		return eventDate;
	}
	
	public String getDateString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm.SSa");
		String s = eventDate.format(formatter);
		return s;
	}
	
	public String getDescription(){
		return description.toString();
	}
	
	public String getVenue(){
		return venue.toString();
	}
	
	public void setDescription(String s){
		description = s;
	}

	public void setVenue(String s){
		venue = s;
	}
	
	@Override
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm.SSa");
		String s = eventDate.format(formatter);
		return "Date: " + s + " Description: " + description + " Venue: " + venue;
	}
}
