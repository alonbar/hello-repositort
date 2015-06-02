package state;

import java.util.HashMap;

import events.Event;

public class StatePrintLastXEvent extends PrintSate {
	
	private String lastEvent;
	private int waitCounter;
	public StatePrintLastXEvent(ReceivedStateTypes type, String stateID, HashMap<String, String> map, String message, int waitFor, boolean isAcceptingState) {
		super(type,stateID,map,message, isAcceptingState);
		this.waitCounter = waitFor;
		if (waitFor > 0 && isAcceptingState == true) {
			isAcceptingState = false;
		}
	}
	
	@Override
	public void action() {
		System.out.println(this.message + lastEvent);
	}
	@Override
	public State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions) {
		lastEvent = currentEvent.getEventType().toString();
		if (waitCounter > 0)
		{
			this.waitCounter--;
			lastEvent = lastEvent + currentEvent.getEventType().toString();
			return this;
		}
		this.isAcceptingState = true;
		return super.processEvent(currentEvent, transitions);
	}       

}
