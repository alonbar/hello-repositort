package state;

import java.util.HashMap;

import events.Event;

public class StatePrintLastEvent extends PrintSate {
	
	private String lastEvent;
	
	public StatePrintLastEvent(ReceivedStateTypes type, String stateID, HashMap<String, String> map, String message) {
		super(type,stateID,map,message);
	}
	
	@Override
	public void action() {
		System.out.println(this.message + lastEvent);
	}
	@Override
	public State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions) {
		lastEvent = currentEvent.getEventType().toString();
		return super.processEvent(currentEvent, transitions);
	}       

}
