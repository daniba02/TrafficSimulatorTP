package simulator.exceptions;

public class SimulatorException extends Exception{
	
	
	private static final long serialVersionUID = 1L;
	private String s;

	public SimulatorException(String format) {
		s = format;
	}
	
	public String getMessage() {
		
		return s;
	}

}
