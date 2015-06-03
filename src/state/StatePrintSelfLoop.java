package state;

import java.util.HashMap;
import java.util.Properties;

import events.Event;
import events.eventTypes;
/**
 * This state will loop through itself until waitCounter goes to zero
 * @author ALONBA
 *
 */
public class StatePrintSelfLoop extends StatePrint {
	private static String WAIT_COUNTER = "waitCounter";
	private static String EVENT_TO_WAIT_FOR = "EventToWait";
	private int waitForXEvents; 
	private int waitCounter;
	private eventTypes EventToWait; 
	public StatePrintSelfLoop (ReceivedStateTypes type, String stateID, 
							   HashMap<String, String> map, 
							   String message, 
							   boolean isAcceptingState, 
							   int waitForXEvents, 
							   eventTypes typeToWaitFor) {
		super(type,stateID, map,message, isAcceptingState);
		this.message = message;
		this.waitForXEvents = waitForXEvents;
		this.waitCounter = waitForXEvents; 
		this.EventToWait = typeToWaitFor;
	}
	
	@Override
	public State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions) {
		if (this.waitCounter == 0) {
			initializeCounter();
			return super.processEvent(currentEvent, transitions);
		}

		/*
		 * If the current event equals to the event that we should wait for then process this event
		 * according to the state logic else just initialize the state and process the event
		 * regularly without the waiting logic
		 */
		if (this.EventToWait.equals(currentEvent.getEventType())) {
			waitCounter--;
			if (this.waitCounter == 0 && this.getIsAcceptingState() == true) {
				action(); 
			}
			return this;
		}
		
		else {
			initializeCounter();
			return super.processEvent(currentEvent, transitions);
		}
		
	}
	
	private void initializeCounter() {
		this.waitCounter = this.waitForXEvents;
	}
	
	@Override
	public void backupState(Properties properties) {
		String waitForXEventsStr = String.valueOf(this.waitForXEvents);
		String waitCounterStr = String.valueOf(this.waitCounter);
		properties.setProperty(StateAtrributes.waitForXevents.toString(), waitForXEventsStr);
		properties.setProperty(WAIT_COUNTER, waitCounterStr);
		properties.setProperty(EVENT_TO_WAIT_FOR, this.EventToWait.toString());
		super.backupState(properties);
	}
	
	@Override
	public void updateState(Properties properties) {
		this.waitForXEvents =  Integer.parseInt(properties.getProperty(StateAtrributes.waitForXevents.toString()));
		this.waitCounter =  Integer.parseInt(properties.getProperty(WAIT_COUNTER));
		this.EventToWait = eventTypes.valueOf(properties.getProperty(EVENT_TO_WAIT_FOR));
		super.updateState(properties);
	}
}
