package events;

public class Event {
	private eventTypes type;
	
	public Event (eventTypes type) {
		this.type = type;
	}
	
	public eventTypes getEventType () 	{
		return type;
	}
}
