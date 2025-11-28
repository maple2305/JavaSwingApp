package db.entity;

import java.time.LocalDateTime;

public class Event {
	private int eventId;
	private String eventName;
	private LocalDateTime eventDatetime;
	private String eventPlace;
	private String eventMemo;

	public Event(int eventId, String eventName, LocalDateTime eventDatetime, String eventPlace, String eventMemo) {
		this(eventName, eventDatetime, eventPlace, eventMemo);
		this.eventId = eventId;
	}

	public Event(String eventName, LocalDateTime eventDatetime, String eventPlace, String eventMemo) {
		this.eventName = eventName;
		this.eventDatetime = eventDatetime;
		this.eventPlace = eventPlace;
		this.eventMemo = eventMemo;
	}
	
	public Event() {
		
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public LocalDateTime getEventDatetime() {
		return eventDatetime;
	}

	public void setEventDatetime(LocalDateTime eventDatetime) {
		this.eventDatetime = eventDatetime;
	}

	public String getEventPlace() {
		return eventPlace;
	}

	public void setEventPlace(String eventPlace) {
		this.eventPlace = eventPlace;
	}

	public String getEventMemo() {
		return eventMemo;
	}

	public void setEventMemo(String eventMemo) {
		this.eventMemo = eventMemo;
	}

}
