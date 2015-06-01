package state;

import java.util.HashMap;

import events.Event;

public abstract class State {
	public abstract void action ();
	public abstract State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions);
}
