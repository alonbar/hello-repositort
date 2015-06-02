package machine;
/**
 * This is the machine class. It is the entry point to the program.
 * The machine will load the information of it's last run and will load the XML that defines the
 * different transitions and states that are defined.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Scanner;
import events.Event;
import events.eventTypes;
import state.State;
import state.ReceivedSates;
import state.StateAtrributes;

public class FSM {
	private static String BEGIN_STATE = "q0";
	private static String BACKUP_FILE = "src/resources/fsm_status2.properties";
	private State currentState;
	private boolean isOn;
	//The machineBackup file is where the current state is stored so that if the machine goes down 
	//and up it will be able to continue from where it left off.
	File machineBackup;
	//This map is the heart of the machine. It holds all the states mapped according to their ID.
	HashMap<String, ReceivedSates> transitions;
	
	public void init (String stateBackupFile, String statesTransitionRules) {
		machineBackup = new File(stateBackupFile);
		try {
			//Reading the last state of the machine;
			//Reading the transitions of state machine and updating the transitions.
			transitions = StateFileParser.parse(statesTransitionRules);
			currentState = loadLastState(machineBackup);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		this.isOn = true;
	}

	/**
	 * This method will update the current state according to the last run (if there was one) with
	 * data that was relevant the the last run.
	 * @param fin file to read events from
	 * @throws IOException
	 */
	private State loadLastState(File fin) throws IOException {
		if (machineBackup.exists() == false) {
			return transitions.get(BEGIN_STATE);
		} 
		else {
			machineBackup.createNewFile();
			FileInputStream fileInput = new FileInputStream(machineBackup);
			Properties properties = new Properties();
			properties.load(fileInput);
			//get the correct state according to the last run. 
			currentState = transitions.get(properties.getProperty(StateAtrributes.stateID.toString(), BEGIN_STATE));
			//update the state with details from the previous run.
			currentState.updateState(properties);
			return currentState;
		}
	}
	
	/**
	 * This method will wait for the next coming event and then process it.
	 */
	private void waiting () {
		while (this.isOn) {
			Event currentEvent = getEvent();
			if (currentEvent != null){
				processEvent(currentEvent);
			}
		}
	}
	
	/**
	 * This method will process the event that the machine just received.
	 * @param currentEvent
	 */
	private void processEvent (Event currentEvent) {
		if (currentEvent.getEventType().equals(eventTypes.SHUTDOWN)) {
			shutDown();
		}
		else {
			/*
			 * Eeach State knows its' logic. The current state will receive the new event and the 
			 * transitions and will return the next state. 
			 */
			currentState = currentState.processEvent(currentEvent, transitions);
			
			/*
			 * Each state knows how to backup it's own unique data; 
			 */
			Properties properties = new Properties();
			currentState.backupState(properties);
			updateBackupFile(properties);
			System.out.println("moved to: " + currentState.getStateID());
		}
	}
	
	/*
	 * This method might change according to the application that is using this FSM framework. 
	 */
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
		
		System.out.println("Shuting down");
	}
	
	public void updateBackupFile (Properties properties) {
		try {
			FileOutputStream fileOut = new FileOutputStream(machineBackup);
			properties.store(fileOut, "fsm status");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main (String [] args) {
		if (args.length < 1)
		{
			System.out.println("missing state transition rules file");
			return;
		}
		FSM machine = new FSM();
		machine.init(BACKUP_FILE, args[0]);
		machine.waiting();
	}
}

