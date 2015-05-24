package machine;

import state.ReceivedStateTypes;
import state.StateReceive;
import state.PrintSate;

public class StateReceiveFactory {
	
	public static StateReceive factory (String type, String sequence, String action) {
		if (ReceivedStateTypes.StatePrint.compareString(type)) 
		{
			return new PrintSate(sequence, action);
		}
		return null;
				
	}
}
