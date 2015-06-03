package state;

/**
 * When a new type of state is defined, it should be declared in this file so that the parser will
 * be able to recognize it.
 * @author ALONBA
 *
 */
public enum ReceivedStateTypes{

	StatePrint("StatePrint"),
	StatePrintLastXEvents("StatePrintLastXEvents"),
	RegularState("RegularState"),
	StatePrintSelfLoop("StatePrintSelfLoop");

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

