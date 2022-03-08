package simulator.model;

public class CityRoad extends Road{

	CityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length, Weather weather) {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		totalCO2 -= xValue(weather);
			compruebaLimit(totalCO2);
	}

	@Override
	void updateSpeedLimit() {
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int speed;
		speed = ((11-v.getContClass())*speedLimit)/11;
		return speed;
	}
	
	int xValue(Weather w) {
		int x = 2;
		if (w.name() == "SUNNY" || w.name() == "STORM") {
			x = 10;
		}
		return x;
	}

}
