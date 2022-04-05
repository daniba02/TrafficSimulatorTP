package simulator.model;

public class NewRoadEvent extends Event{

	protected String id;
	protected String srcJunc;
	protected Junction src;
	protected String destJunc;
	protected Junction dest;
	protected int length;
	protected int co2Limit;
	protected int maxSpeed;
	protected Weather weather;
	
	NewRoadEvent(int time, String id, String srcJunc,
			String destJunc, int length, int co2Limit, int maxSpeed, Weather
			weather) {
		super(time);
		
		this.id = id;
		this.srcJunc = srcJunc;
		this.destJunc = destJunc;
		this.length = length;
		this.co2Limit = co2Limit;
		this.maxSpeed = maxSpeed;
		this.weather = weather;
		// TODO Auto-generated constructor stub
	}

	@Override
	void execute(RoadMap map) {
		
	}

}
