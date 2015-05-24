package machine;

import java.io.File;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import state.StateReceive;

public class StatePropertiesFileParser {
	
	public static ArrayList<StateReceive> parse (String propertiesPath)
	{
		ArrayList<StateReceive> statesList = new ArrayList<StateReceive>();
		 try {
			 
				File fXmlFile = new File(propertiesPath);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
				NodeList nList = doc.getElementsByTagName("StateReceive");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp); 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
						NodeList childNodes = eElement.getChildNodes();
						for(int childIndex = 0; childIndex < childNodes.getLength(); childIndex++) {
							Node child = childNodes.item(childIndex);
							//Upon new types of states you may add here the conditions
							if (child.getNodeType() == Node.ELEMENT_NODE) {
								System.out.println(child.getNodeName());
								statesList.add(StateReceiveFactory.factory(child.getNodeName(), 
															child.getAttributes().getNamedItem("sequence").toString(), 
															child.getAttributes().getNamedItem("action").toString()));		
							}			 
						}
					}
			    }
		 } catch (Exception e) {
				e.printStackTrace();
			    }
		return statesList;
	  }
}
