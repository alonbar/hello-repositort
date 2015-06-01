package machine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import events.Event;
import events.eventTypes;
import state.State;
import state.ReceivedSates;

public class Machine {
	private State currentState;
	private StringBuilder eventsBuffer;
	private boolean isOn;
	File stateFileLog;
	HashMap<String, ReceivedSates> transitions;
	
	public Machine () {
		eventsBuffer = new StringBuilder();
	}
	public void init (String stateFilePath, String statePropertyFile) {
		stateFileLog = new File(stateFilePath);
		//reading from the events logs and then initializing it.
		try {
			//creates a new file only if the file doesn't exist
			fillBuffer(stateFileLog);
			transitions = StateFileParser.parse(statePropertyFile);
			System.out.println("printing transition");
			currentState = transitions.get("q0");
			
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		this.isOn = true;
	}

	/**
	 * This method will read from the events log file into the events buffer for 
	 * @param fin file to read events from
	 * @throws IOException
	 */
	private void fillBuffer(File fin) throws IOException {
		stateFileLog.createNewFile();
		BufferedReader br = new BufferedReader(new FileReader(fin));	 
		String line = null;
		while ((line = br.readLine()) != null) {
			eventsBuffer.append(line);
		}	
		System.out.println(eventsBuffer);
		br.close();
		stateFileLog.delete();
		stateFileLog.createNewFile();
	}
	
	private void waiting () {
		while (this.isOn) {
			Event currentEvent = getEvent();
			if (currentEvent != null){
				processEvent(currentEvent);
			}
		}
	}
	
	private void processEvent (Event currentEvent) {
		if (currentEvent.getEventType().equals(eventTypes.SHUTDOWN)) {
			shutDown();
		}
		else {
			currentState = currentState.processEvent(currentEvent, transitions);
		}
	}
	
	public Event getEvent () {
		 Scanner in = new Scanner(System.in);
		 String line = in.nextLine();
		 Event currentEvent = new Event(line);
		 if (currentEvent.getEventType() != eventTypes.NULL)
		 {
			 return currentEvent;
		 }
		return null;
		
	}
	
	private void shutDown(){
		isOn = false;		
	}
	
	
	public static void main (String [] args) {
		
		Machine m = new Machine();
		m.init("C:\\temp\\alon2.txt", "C:\\temp\\states.xml");
		m.waiting();
	}
}

