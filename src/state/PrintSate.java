package state;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

import events.Event;
import events.eventTypes;

public class PrintSate extends ReceivedSates {
	
	
	protected String message;
	
	public PrintSate (ReceivedStateTypes type, String stateID, HashMap<String, String> map, String message, boolean isAcceptingState) {
		super(type,stateID,map,isAcceptingState);
		this.message = message;
	}
	
	public PrintSate (PrintSate other) {
		this(other.type,other.stateID, other.transitionLocalMap,other.message, other.isAcceptingState);
		}
	@Override
	public void action() {
		System.out.println(this.message);
	}
	
	@Override
	public void backupState(Properties properties) {
		properties.setProperty(StateAtrributes.action.toString(), this.message);
		super.backupState(properties);
	}

}
