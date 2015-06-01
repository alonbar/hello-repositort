package machine;

import java.util.HashMap;

import state.ReceivedStateTypes;
import state.ReceivedSates;
import state.PrintSate;

public class StateReceiveFactory {
	
	public static ReceivedSates factory (String type, String stateID,HashMap<String,String> map, String action) {
		if (ReceivedStateTypes.StatePrint.compareString(type)) { 
			return new PrintSate(ReceivedStateTypes.StatePrint,stateID,map,action);
		}
		else if (ReceivedStateTypes.StatePrintLastEvent.compareString(type)) {
			return new PrintSate(ReceivedStateTypes.StatePrint,stateID,map,action);
		}
		return null;
				
	}
}
