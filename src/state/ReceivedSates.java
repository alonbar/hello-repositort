package state;

import java.util.HashMap;
import java.util.Properties;
import events.Event;
/**
 * This class represent a simple state that can receive an event and decide which is the next 
 * relavent state.
 * @author ALONBA
 *
 */
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
	public void action() {};
	
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
	
	/**
	 * This method process event. It will ask the states mapping rules what is the next state
	 * according to the rules.
	 */
	@Override
	public State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions) {
		ReceivedSates retState = getNextState(currentEvent, transitions);
		//If it is an accepting state then executing the state action (if there is one)
		if (retState.getIsAcceptingState() == true) {
			retState.action();
		}
		return retState;
	}

	protected ReceivedSates getNextState(Event currentEvent, 
									   HashMap<String, ReceivedSates> transitions) {
		String str = currentEvent.getEventType().toString();
		//asking the state what is the appropriate next state according to the event.
		String nextState = this.transitionLocalMap.get(str);
		//Asking the transition table for the next state
		ReceivedSates retState = transitions.get(nextState);
		return retState;
	}
	
//	protected State getNextState (State state, HashMap<String, ReceivedSates> transitions)
	
	protected boolean getIsAcceptingState() {
		return this.isAcceptingState;
	}
	
	@Override	
	public void backupState(Properties properties) {
		properties.setProperty(StateAtrributes.stateID.toString(), this.stateID);
		properties.setProperty(StateAtrributes.type.toString(), this.type.toString());
		String isAcceptingStr = getStringFromBoolean(this.isAcceptingState);
		properties.setProperty(StateAtrributes.isAccepting.toString(), isAcceptingStr);			

	}
	
	@Override
	public void updateState(Properties properties) {
		this.stateID = properties.getProperty(StateAtrributes.stateID.toString());
		this.type = ReceivedStateTypes.valueOf(properties.getProperty(StateAtrributes.type.toString()));
		this.isAcceptingState = getBooleanFromString(properties.getProperty(StateAtrributes.isAccepting.toString()));
	}
	
	protected String getStringFromBoolean (boolean flag) {
		if (flag == true) {
			return  TRUE;
		} else {
			return FALSE;
		}
	}
	
	protected boolean getBooleanFromString (String flag) {
		if (flag.equals(TRUE)) {
			return  true;
		} else {
			return false;
		}
	}
	
}
