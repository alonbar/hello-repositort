package machine;

import state.ReceivedStateTypes;
import state.ReceivedSates;
import state.PrintSate;

public class StateReceiveFactory {
	
	public static ReceivedSates factory (String type, String stateID, String action) {
		if (ReceivedStateTypes.StatePrint.compareString(type)) 
		{
			return new PrintSate(ReceivedStateTypes.StatePrint, stateID, action);
		}
		return null;
				
	}
}
