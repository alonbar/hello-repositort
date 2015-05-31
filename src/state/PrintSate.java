package state;
import java.util.ArrayList;
import events.Event;
import events.eventTypes;

public class PrintSate extends ReceivedSates {
	
	
	private String message;
	
	public PrintSate (ReceivedStateTypes type, String stateID, String message) {
		super(type, stateID);
		this.message = message;
	}
	@Override
	public void action() {
		System.out.println(this.message);
	}
	@Override
	public boolean processEvent(Event currentEvent) {
//		this.eventsBuffer.add(currentEvent);
		// TODO Auto-generated method stub
		return false;
	}

}
