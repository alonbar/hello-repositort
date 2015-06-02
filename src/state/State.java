package state;

import java.util.HashMap;
import java.util.Properties;

import events.Event;
/**
 * This abstract class represents a state and the API all other iheriting classes should implement.
 * @author ALONBA
 *
 */
public abstract class State {
	protected ReceivedStateTypes type;
	protected String stateID;
	public abstract void action ();
	public abstract State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions);
	/**
	 * This method returns a property file that represents the state's current "state" 
	 * @param properties
	 */
	public abstract void backupState(Properties properties);
	
	/**
	 * This method will update the object according to the properties file.
	 * @param properties
	 */
	public abstract void updateState(Properties properties);
	public String getStateType () {
		return type.toString();
	}
	public String getStateID() {
		return stateID;
	}
}
