package simulator.model;

public class NewInterCityRoadEvent extends NewRoadEvent{
	
	
	public NewInterCityRoadEvent(int time, String id, String srcJunc,
			String destJunc, int length, int co2Limit, int maxSpeed, Weather
			weather) {
		
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	
			}

	@Override
	void execute(RoadMap map) {
		
		src= map.getJunction(srcJunc);
		dest = map.getJunction(destJunc);
		
		InterCityRoad r = new InterCityRoad(id, src, dest, maxSpeed, co2Limit, length, weather);
		map.addRoad(r);
	}

}
