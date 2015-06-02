package machine;

import java.util.HashMap;

import javax.sound.sampled.ReverbType;

import org.w3c.dom.NamedNodeMap;

import state.ReceivedStateTypes;
import state.ReceivedSates;
import state.PrintSate;
import state.StateAtrributes;
import state.StatePrintLastXEvent;

public class StateReceiveFactory {
	static String TRUE = "true";
	public static ReceivedSates factory (NamedNodeMap nodeAttributes, HashMap<String,String> map) {
		String currentNodeType = null;
		boolean isAcceptingState = false;
		if (nodeAttributes.getNamedItem(StateAtrributes.isAccepting.toString()).getNodeValue().equals(TRUE)) {
			isAcceptingState = true;
		}
		if (nodeAttributes.getNamedItem(StateAtrributes.type.toString())  == null) {
			currentNodeType = ReceivedStateTypes.RegularState.toString();
		}
		else {
			currentNodeType = nodeAttributes.getNamedItem(StateAtrributes.type.toString()).getNodeValue();
		}
		
		if (ReceivedStateTypes.StatePrint.compareString(currentNodeType)) { 
			return new PrintSate(ReceivedStateTypes.StatePrint,
								 nodeAttributes.getNamedItem(StateAtrributes.stateID.toString()).getNodeValue(),
								 map,
								 nodeAttributes.getNamedItem(StateAtrributes.action.toString()).getNodeValue(),
								 isAcceptingState);
		}
		
		else if (ReceivedStateTypes.StatePrintLastXEvents.compareString(currentNodeType)) {
			int waitForXevents = Integer.parseInt(nodeAttributes.getNamedItem(StateAtrributes.waitForXevents.toString()).getNodeValue());
			return new StatePrintLastXEvent(ReceivedStateTypes.StatePrintLastXEvents, 
											nodeAttributes.getNamedItem(StateAtrributes.stateID.toString()).getNodeValue(), 
											map, 
											nodeAttributes.getNamedItem(StateAtrributes.action.toString()).getNodeValue(), 
											waitForXevents,
											isAcceptingState);
		}
		
		else if (ReceivedStateTypes.RegularState.compareString(currentNodeType)) {
			return new ReceivedSates(ReceivedStateTypes.RegularState, 
									 nodeAttributes.getNamedItem(StateAtrributes.stateID.toString()).getNodeValue(), 
									 map);
		}
		return null;
				
	}
}
