package state;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import events.Event;
import events.eventTypes;

public class ReceivedSates extends State {
	
	static String TRUE = "true";
	static String FALSE = "false";
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
		if (retState.getIsAcceptingState() == true) {
			retState.action();
		}
		return retState;
	}
	protected boolean getIsAcceptingState() {
		return this.isAcceptingState;
	}
	@Override
	public void backupState(Properties properties) {
		properties.setProperty(StateAtrributes.stateID.toString(), this.stateID);
		properties.setProperty(StateAtrributes.type.toString(), this.type.toString());
		String isAcceptingStr = getBooleanInString(this.isAcceptingState);
		properties.setProperty(StateAtrributes.isAccepting.toString(), isAcceptingStr);			

	}
	
	protected String getBooleanInString (boolean flag) {
		if (flag == true) {
			return  TRUE;
		} else {
			return FALSE;
		}
	}
}
