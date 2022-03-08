package simulator.model;

public class NewCityRoadEvent extends NewRoadEvent{

	public NewCityRoadEvent(int time, String id, String srcJunc, String
			destJunc, int length, int co2Limit, int maxSpeed, Weather weather){
		
		super(time, id, srcJunc, destJunc, length, co2Limit, maxSpeed, weather);
	}

	@Override
	void execute(RoadMap map) {
		
		src= map.getJunction(srcJunc);
		dest = map.getJunction(destJunc);
		CityRoad r = new CityRoad(id, src, dest, maxSpeed, co2Limit, length, weather);
		map.addRoad(r);
	}
}
