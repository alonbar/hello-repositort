package state;
import java.util.HashMap;
import java.util.Properties;

/**
 * This state is an example of how you can expand the fsm without changing any internal logic of 
 * the machine.
 * One the fsm will get to this state , it will print a string that was predefined in the state's 
 * XML.
 * @author ALONBA
 *
 */
public class StatePrint extends ReceivedSates {
		
	protected String message;
	
	public StatePrint (ReceivedStateTypes type, String stateID, HashMap<String, String> map, String message, boolean isAcceptingState) {
		super(type,stateID,map,isAcceptingState);
		this.message = message;
	}
	
	public StatePrint (StatePrint other) {
		this(other.type,other.stateID, other.transitionLocalMap,other.message, other.isAcceptingState);
		}
	@Override
	public void action() {
		System.out.println(this.message);
	}
	
	@Override
	public void backupState(Properties properties) {
		properties.setProperty(StateAtrributes.action.toString(), this.message);
		super.backupState(properties);
	}
	
	public void updateState(Properties properties) {
		this.message = properties.getProperty(StateAtrributes.action.toString());
		super.backupState(properties);
	}

}
