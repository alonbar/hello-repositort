package state;
import java.util.ArrayList;
import events.Event;

public class PrintSate extends StateReceive {
	
	private String sequence;
	private String message;
	public PrintSate (String sequence, String action) {
		this.message = action;
		this.sequence = sequence;
	}
	@Override
	public void action() {
		System.out.println(this.message);

	}

}
