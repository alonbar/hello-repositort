package state;

import java.io.File;
import java.util.ArrayList;

import events.Event;
import events.eventTypes;

public class ReceivedSates extends State {
	private ReceivedStateTypes type;
	protected File stateFileLog;
	protected String stateID;
	protected String [] transitionLocalMap;
	public ReceivedSates(ReceivedSates other) {
		this(other.type,other.stateID, other.transitionLocalMap);
	}
	public ReceivedSates(ReceivedStateTypes type, String stateID, String [] map) {
		this(type,stateID);
		for (int i = 0; i < eventTypes.length; i++) {
			transitionLocalMap[i] = map[i];
		}
	}
	public ReceivedSates(ReceivedStateTypes type, String stateID) {
		this.stateID = stateID;
		this.type = type;
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
