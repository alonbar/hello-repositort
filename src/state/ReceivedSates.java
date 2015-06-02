package state;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import events.Event;
import events.eventTypes;

public class ReceivedSates extends State {
	

	protected HashMap<String, String>  transitionLocalMap;
	protected boolean isAcceptingState;
	public ReceivedSates(ReceivedSates other) {
		this(other.type,other.stateID, other.transitionLocalMap);
	}
	public ReceivedSates(ReceivedStateTypes type, String stateID, HashMap<String, String> map) {
		this(type,stateID);
		transitionLocalMap = new HashMap<>();
		transitionLocalMap.putAll(map);
	}
	public ReceivedSates(ReceivedStateTypes type, String stateID) {
		this.stateID = stateID;
		this.type = type;
		this.isAcceptingState = false;
	}
	
	public ReceivedSates(ReceivedStateTypes type, String stateID, HashMap<String, String> map, boolean isAccepting) {
		this(type,stateID,map);
		this.isAcceptingState = isAccepting;
	}
	public String getStateID(){
		return this.stateID;
	}
	@Override
	public void action() {
		// TODO Auto-generated method stub
		
	};
	@Override
	public int hashCode() {
		return stateID.hashCode();	
	}
	
	@Override
	public boolean equals(Object obj) {
	    if (this == obj)
	        return true;
	    if (obj == null)
	        return false;
	    if (getClass() != obj.getClass())
	        return false;
	    ReceivedSates other = (ReceivedSates) obj;
	    if (this.stateID.equals(other.stateID))
	        return true;
	    else {
	    	return false;
	    }
	}
	@Override
	public State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions) {
		String str = currentEvent.getEventType().toString();
		String nextState = transitionLocalMap.get(str);
		ReceivedSates retState = transitions.get(nextState);
		if (retState.isAcceptingState == true) {
			retState.action();
//			retState = transitions.get("q0");
		}
		System.out.println("moving to state: " + retState.getStateID());
		return retState;
	}       
}
