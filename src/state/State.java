package state;

import java.util.HashMap;

import events.Event;

public abstract class State {
	protected ReceivedStateTypes type;
	protected String stateID;
	public abstract void action ();
	public abstract State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions);
	public String getStateType () {
		return type.toString();
	}
	public String getStateID() {
		return stateID;
	}
}
