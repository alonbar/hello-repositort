package events;

public class Event {
	private eventTypes type;
	
	public Event (eventTypes type) {
		this.type = type;
	}
	public Event (String type) {
		switch (type) {
        case "A":
            this.type = eventTypes.A;
            break;
                
        case "B":
        	this.type = eventTypes.B;
            break;
                     
        case "C":
        	this.type = eventTypes.C;
            break;
        
        case "SHUTDOWN":
        	this.type = eventTypes.SHUTDOWN;
            break;
            
        default:
            this.type = eventTypes.NULL;
            break;
		}
	}
	
	
	public eventTypes getEventType () 	{
		return type;
	}
}
