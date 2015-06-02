package machine;

import java.util.HashMap;
import org.w3c.dom.NamedNodeMap;
import state.ReceivedStateTypes;
import state.ReceivedSates;
import state.PrintSate;
import state.StateAtrributes;
import state.StatePrintLastXEvent;

/**
 * This class is all of the received states, which are states that are predefined in the staet XML,
 * and process them into the correct state object.
 * When a new state is defined this class should be updated.
 * @author ALONBA
 */
public class StateReceiveFactory {
	static String TRUE = "true";
	public static ReceivedSates factory (NamedNodeMap stateAttributes, HashMap<String,String> map) {
		String stateType = null;
		boolean isAcceptingState = false;
		
		if (stateAttributes.getNamedItem(StateAtrributes.isAccepting.toString()).getNodeValue().equals(TRUE)) {
			isAcceptingState = true;
		}
		
		//If the xml does not specify what type is the state, then by default is will be a RegularState
		if (stateAttributes.getNamedItem(StateAtrributes.type.toString())  == null) {
			stateType = ReceivedStateTypes.RegularState.toString();
		}
		else {
			stateType = stateAttributes.getNamedItem(StateAtrributes.type.toString()).getNodeValue();
		}

		//going over all the defined states to find the correct state to create.
		if (ReceivedStateTypes.StatePrint.compareString(stateType)) { 
			return new PrintSate(ReceivedStateTypes.StatePrint,
								 stateAttributes.getNamedItem(StateAtrributes.stateID.toString()).getNodeValue(),
								 map,
								 stateAttributes.getNamedItem(StateAtrributes.action.toString()).getNodeValue(),
								 isAcceptingState);
		}
		
		else if (ReceivedStateTypes.StatePrintLastXEvents.compareString(stateType)) {
			int waitForXevents = Integer.parseInt(stateAttributes.getNamedItem(StateAtrributes.waitForXevents.toString()).getNodeValue());
			return new StatePrintLastXEvent(ReceivedStateTypes.StatePrintLastXEvents, 
											stateAttributes.getNamedItem(StateAtrributes.stateID.toString()).getNodeValue(), 
											map, 
											stateAttributes.getNamedItem(StateAtrributes.action.toString()).getNodeValue(), 
											waitForXevents,
											isAcceptingState);
		}
		
		else if (ReceivedStateTypes.RegularState.compareString(stateType)) {
			return new ReceivedSates(ReceivedStateTypes.RegularState, 
									 stateAttributes.getNamedItem(StateAtrributes.stateID.toString()).getNodeValue(), 
									 map);
		}
		return null;
				
	}
}
