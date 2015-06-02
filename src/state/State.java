package state;

import java.util.HashMap;
import java.util.Properties;

import events.Event;

public abstract class State {
	protected ReceivedStateTypes type;
	protected String stateID;
	public abstract void action ();
	public abstract State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions);
	public abstract void backupState(Properties properties);
	public String getStateType () {
		return type.toString();
	}
	public String getStateID() {
		return stateID;
	}
}
