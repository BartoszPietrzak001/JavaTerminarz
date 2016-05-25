package application;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CallendarEvent {
	private Date eventDate;
	private String description = "";
	
	@SuppressWarnings("deprecation")
	public CallendarEvent(int day, int month, int year, int hour, int minute, String description)
	{
		eventDate = new Date(year, month, day, hour, minute);
		this.description = description;
	}
	
	public CallendarEvent (int day, int month, int year)
	{
		this(day, month, year, 0, 0, "Arrangement");
	}
	
	public CallendarEvent (int day, int month, int year, int hour, int minute)
	{
		this(day, month, year, hour, minute, "Arrangement");
	}
	
	public CallendarEvent (int day, int month, int year, int hour)
	{
		this(day, month, year, hour, 0, "Arrangement");
	}
	
	public CallendarEvent (int day, int month, int year, String description)
	{
		this(day, month, year, 0, 0, description);
	}
	
	public CallendarEvent (int day, int month, int year, int hour, String description)
	{
		this(day, month, year, hour, 0, description);
	}
	
	public void setDate(Date d)
	{
		eventDate = d;
	}
	
	public Date getDate()
	{
		return eventDate;
	}
	
	public void setDescription(String d)
	{
		description = d;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	@Override
	public String toString()
	{
		Format formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = formatter.format(eventDate);
		return "Date: " + s + " Description: " + description;
	}

}
