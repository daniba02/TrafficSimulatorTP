package simulator.exceptions;


public class LimitException extends SimulatorException{

	public LimitException(String format) {
		super(format);
	}
	
	private static final long serialVersionUID = 1L;
}
