package model;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Worker implements ValueValidator{
	private String firstName;
	private String lastName;
	private String room;
	private int startHour;
	private int endHour;
	
	public Worker(String firstName, String lastName, String room, int startHour, int endHour) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.room = room;
		this.startHour = startHour;
		this.endHour = endHour;
	}
	
	public Worker(String firstName) {
		this.firstName = firstName;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getRoom() {
		return room;
	}
	
	public int getStartHour() {
		return startHour;
	}
	
	public int getEndHour() {
		return endHour;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void setAge(String room) {
		this.room = room;
	}
	
		
	public boolean isWorkerCorrect() {
		if (isTimeCorrect(startHour, endHour) && isTextCorrect(firstName) && isTextCorrect(lastName) && isRoomCorrect(room)) {
			return true;
		}
		else {return false;}
	}
}

