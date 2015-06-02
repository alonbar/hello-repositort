package state;

public enum StateAtrributes {

	isAccepting("isAccepting"),
	action("action"),
	type("type"),
	waitForXevents("waitForXevents"),
	eventType("eventType"),
	nextState("nextState"),
	stateID("stateID");	

	private final String stateVal;       

	private StateAtrributes(final String stateVal) {
	    this.stateVal= stateVal;
	}

	StateAtrributes(){ 
		stateVal=null; 
	}

/**
 * toString method.
 * 
 * @return Value of this Enum as String.
 */
	@Override
	public String toString(){
	   return stateVal;
	}
	
}



