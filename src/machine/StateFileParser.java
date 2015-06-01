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

public class StateFileParser {
	
	public static HashMap<String, ReceivedSates> parse (String propertiesPath) {
		HashMap<String, ReceivedSates> transitions = null;
		ArrayList<ReceivedSates> acceptingStates = null;
		 try {
			 
				File fXmlFile = new File(propertiesPath);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				NodeList transitionsToProcess = doc.getElementsByTagName("state");
				transitions = new HashMap<String, ReceivedSates>();
				parseTransitions(transitionsToProcess, transitions, acceptingStates);
				
		 } catch (Exception e) {
				e.printStackTrace();
			    }
		 transitions.get("q1").action();
		return transitions;
	}

	private static void parseTransitions(NodeList transitionsToProcess, 
										 HashMap<String, ReceivedSates> transitions, 
										 ArrayList<ReceivedSates> acceptingStates)
	{
		for (int i = 0; i < transitionsToProcess.getLength(); i++) {
			Node nNode = transitionsToProcess.item(i); 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				System.out.println(eElement.getAttributes().getNamedItem("stateID").toString());
				String stateID = eElement.getAttributes().getNamedItem("stateID").toString();
				NodeList childNodes = eElement.getChildNodes();
				int leng = childNodes.getLength();
				int counter = 0;
				HashMap<String, String> stateMap = new HashMap<String, String>();
				for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
					Node child = childNodes.item(childIndex);
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						String key = childNodes.item(childIndex).getAttributes().getNamedItem("eventType").getNodeValue();
						String val = childNodes.item(childIndex).getAttributes().getNamedItem("nextState").getNodeValue();
						stateMap.put(key, val);
					}
				}
				ReceivedSates current = null;
				if (eElement.getAttribute("isAccepting").equals("true")) {
					String type = eElement.getAttribute("type");
					current = StateReceiveFactory.factory(eElement.getAttribute("type"), eElement.getAttribute("stateID"),stateMap, eElement.getAttribute("action"));
					transitions.put(eElement.getAttribute("stateID"), current);
				}
				else {
					current = new ReceivedSates(ReceivedStateTypes.RegularState, eElement.getAttribute("stateID"),stateMap);
					transitions.put(eElement.getAttribute("stateID"), new ReceivedSates(current));
				}
				counter++;

			}
		}
		
	}
}
