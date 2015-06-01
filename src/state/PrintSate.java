package state;
import java.util.ArrayList;
import java.util.HashMap;

import events.Event;
import events.eventTypes;

public class PrintSate extends ReceivedSates {
	
	
	private String message;
	
	public PrintSate (ReceivedStateTypes type, String stateID, HashMap<String, String> map, String message) {
		super(type,stateID,map,true);
		this.message = message;
	}
	
	public PrintSate (PrintSate other) {
		this(other.type,other.stateID, other.transitionLocalMap,other.message);
		}
	@Override
	public void action() {
		System.out.println(this.message);
	}

}
