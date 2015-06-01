package state;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import events.Event;
import events.eventTypes;

public class ReceivedSates extends State {
	protected ReceivedStateTypes type;
	protected String stateID;
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
	public void processEvent(Event currentEvent){}
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
}
