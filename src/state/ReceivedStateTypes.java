package state;

public enum ReceivedStateTypes{

	StatePrint("StatePrint"),
	RegularState("RegularState");

	private final String stateVal;       

	private ReceivedStateTypes(final String stateVal) {
	    this.stateVal= stateVal;
	}

	ReceivedStateTypes(){ 
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
	
	public boolean compareString(final String value){
	    return stateVal.toString().equals(value);
	}
}

