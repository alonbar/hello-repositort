package state;

import java.util.HashMap;
import java.util.Properties;

import events.Event;

public class StatePrintLastXEvent extends PrintSate {
	static String EVENTS_BUFFER = "eventsBuffer";
	static String WAIT_COUNTER = "waitCounter";
	static String MOVE_ON_FLAG = "moveOnFlag";
	private String eventBuffer;
	private int waitCounter;
	private int waitForXEvents;
	private boolean moveOnFlag;
	
	public StatePrintLastXEvent(ReceivedStateTypes type, 
								String stateID, 
								HashMap<String, String> map, 
								String message, 
								int waitForXEvents, 
								boolean isAcceptingState) {
		
		super(type,stateID,map,message, isAcceptingState);
		this.waitCounter = waitForXEvents;
		this.waitForXEvents = waitForXEvents;
		this.eventBuffer = new String();
		if (waitForXEvents > 0 ) {
			this.moveOnFlag= false;
		}
		else {
			this.moveOnFlag= true;
		}
	}
	
	@Override
	public void action() {
		System.out.println(this.message + " " + eventBuffer);
	}
	@Override
	public State processEvent(Event currentEvent, HashMap<String, ReceivedSates> transitions) {
		if (waitCounter > 0 && this.moveOnFlag == false )
		{
			this.waitCounter--;
			eventBuffer = eventBuffer + currentEvent.getEventType().toString();
			if (waitCounter == 0) {
				this.moveOnFlag = true;
				String str = currentEvent.getEventType().toString();
				String nextState = transitionLocalMap.get(str);
				ReceivedSates retState = transitions.get(nextState);
				if (this.isAcceptingState == true) {
					this.action();
				}
				this.waitCounter =this.waitForXEvents;  
				this.eventBuffer = "";
				return retState;
			}
			return this;
		}
		return super.processEvent(currentEvent, transitions);
	}

	@Override
	protected boolean getIsAcceptingState() {
		if (isAcceptingState == true && moveOnFlag == true) {
			return true;
		}
		return false;
	}
	
	@Override
	public void backupState(Properties properties) {
		String waitForXEventsStr = String.valueOf(this.waitForXEvents);
		String waitCounterStr = String.valueOf(this.waitCounter);
		properties.setProperty(StateAtrributes.waitForXevents.toString(), waitForXEventsStr);
		properties.setProperty(EVENTS_BUFFER, this.eventBuffer);
		properties.setProperty(WAIT_COUNTER, waitCounterStr);
		properties.setProperty(MOVE_ON_FLAG, getBooleanInString(this.moveOnFlag));
		super.backupState(properties);
	}

}
