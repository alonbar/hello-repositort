package machine;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
	
	public static HashMap<String, ReceivedSates> parse (String propertiesPath)
	{
		HashMap<String, ReceivedSates> transitions = null;
		ArrayList<ReceivedSates> acceptingStates = null;
		 try {
			 
				File fXmlFile = new File(propertiesPath);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				
				NodeList acceptingStatesToProcess = doc.getElementsByTagName("acceptingStates");
				
				NodeList transitionsToProcess = doc.getElementsByTagName("state");
				NodeList transitionsToProcess2 =  doc.getElementsByTagName("state");
				int j = transitionsToProcess2.getLength();
				transitions = new HashMap<String, ReceivedSates>();
				acceptingStates = parseAcceptingStates(acceptingStatesToProcess);
				parseTransitions(transitionsToProcess, transitions, acceptingStates);
				
		 } catch (Exception e) {
				e.printStackTrace();
			    }
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
				String [] stateMap = new String[eventTypes.length];
				for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
					Node child = childNodes.item(childIndex);
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						stateMap[counter] = childNodes.item(childIndex).getAttributes().getNamedItem("nextState").toString();
					}
				}
				ReceivedSates acceptionStateCandidate = null;
				acceptionStateCandidate= isAcceptingState(acceptingStates, currentStateID);
				if (acceptionStateCandidate != null) {
					transitions.add(acceptionStateCandidate);
				}
				else {
					
					transitions.add(new ReceivedSates(ReceivedStateTypes.RegularState, childNodes.item(childIndex).getAttributes().getNamedItem("stateID").toString()));
				}
				counter++;

			}
		}		
	}
	
	private static ReceivedSates isAcceptingState(ArrayList<ReceivedSates> acceptingStates, 
												  String stateID) {
		for (ReceivedSates temp : acceptingStates) {
			if (temp.getStateID().equals(stateID)){
				return temp;
			}
		}
		return null;
	}
	
	private static ArrayList<ReceivedSates> parseAcceptingStates(NodeList acceptingStates) {
		ArrayList<ReceivedSates> statesList = new ArrayList<ReceivedSates>();  
		int j = acceptingStates.getLength();
		for (int i = 0; i < acceptingStates.getLength(); i++) {
			Node nNode = acceptingStates.item(i); 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
 				Element eElement = (Element) nNode;
				NodeList childNodes = eElement.getChildNodes();
				for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
					Node child = childNodes.item(childIndex);
					//Upon new types of states you may add here the conditions
					if (child.getNodeType() == Node.ELEMENT_NODE) {
						System.out.println(child.getNodeName());
						statesList.add(StateReceiveFactory.factory(child.getNodeName(), 
													child.getAttributes().getNamedItem("stateID").toString(), 
													child.getAttributes().getNamedItem("action").toString()));		
					}			 
				}
			}
		}
		return statesList;
	}
}
