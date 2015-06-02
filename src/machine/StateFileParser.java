package machine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import events.eventTypes;
import state.ReceivedSates;
import state.ReceivedStateTypes;
import state.StateAtrributes;
/**
 * This class is the state file parser.
 * The state file is the file that describe the different states and their transitions.
 * @author ALONBA
 *
 */
public class StateFileParser {
	
	public static HashMap<String, ReceivedSates> parse (String statesTransitionRules) {
		HashMap<String, ReceivedSates> transitions = null;
		ArrayList<ReceivedSates> acceptingStates = null;

		try {
				File fXmlFile = new File(statesTransitionRules);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				NodeList transitionsToProcess = doc.getElementsByTagName("state");
				transitions = new HashMap<String, ReceivedSates>();
				parseTransitions(transitionsToProcess, transitions);	
		 } catch (Exception e) {
				e.printStackTrace();
			    }
		 
		return transitions;
	}

	/**
	 * This method is where the actual parsing of the XML occurs. For each state the method will 
	 * create a hashMap containing the state's transition rules and will call the state factory
	 * with the state appropriate  attributes (the type of the state, the actions and etc...)
	 * @param transitionsToProcess
	 * @param transitions
	 */
	private static void parseTransitions(NodeList transitionsToProcess, 
										 HashMap<String, ReceivedSates> transitions) {
		//for each state in the xml
		for (int i = 0; i < transitionsToProcess.getLength(); i++) {
			Node nNode = transitionsToProcess.item(i); 
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				NodeList childNodes = eElement.getChildNodes();

				//building the current state map.
				HashMap<String, String> stateTransitions = new HashMap<String, String>();
				for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
					Node child = childNodes.item(childIndex);
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						String key = childNodes.item(childIndex).getAttributes().getNamedItem(StateAtrributes.eventType.toString()).getNodeValue();
						String val = childNodes.item(childIndex).getAttributes().getNamedItem(StateAtrributes.nextState.toString()).getNodeValue();
						stateTransitions.put(key, val);
					}
				}
				
				NamedNodeMap currentAttributes = eElement.getAttributes();
				//The factory will return the correct state according to the attributes. The state
				//will be initialized with its' local transition rules.
				ReceivedSates current = StateReceiveFactory.factory(currentAttributes, stateTransitions);
				transitions.put(eElement.getAttribute("stateID"), current);
			}
		}
	}
	
	public ReceivedSates parseBackupFile (String statesTransitionRules) {
		return null;
		
	}
}
