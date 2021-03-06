package application;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class CallendarEvent implements Comparable<CallendarEvent> {
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
	
	public LocalDate getLocalDate(){
		LocalDate date = LocalDate.of(eventDate.getYear(), eventDate.getMonthValue(), eventDate.getDayOfMonth());
		return date;
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
	
	public void stringToDate(String s){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm.ssa");
		eventDate = LocalDateTime.parse(s, formatter);
	}
	
	@Override
	public String toString(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mma");
		String s = eventDate.format(formatter);
		return "Date: " + s + " Description: " + description + " Venue: " + venue;
	}

	@Override
	public int compareTo(CallendarEvent event) {
		return this.eventDate.compareTo(event.eventDate);
	}
}
