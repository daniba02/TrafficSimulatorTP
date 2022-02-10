package simulator.model;

import simulator.exceptions.LimitException;
import simulator.exceptions.SimulatorException;

public class InterCityRoad extends Road{

	InterCityRoad(String id, Junction srcJunc, Junction destJunc, int maxSpeed, int contLimit, int length,
			Weather weather) throws SimulatorException {
		super(id, srcJunc, destJunc, maxSpeed, contLimit, length, weather);
		// TODO Auto-generated constructor stub
	}

	@Override
	void reduceTotalContamination() {
		totalCO2 = ((100 - xValue(weather)) * totalCO2) / 100;
	}

	@Override
	void updateSpeedLimit() {
		if (totalCO2 > contLimit) {
			speedLimit = maxSpeed / 2;
		}
		else speedLimit = maxSpeed;
		
	}

	@Override
	int calculateVehicleSpeed(Vehicle v) {
		int speed = 0;
		
		if (weather.name()== "STORM") {
			speed = (speedLimit*8)/10;
		}
		else {
			speed = speedLimit;
		}
		return speed;
	}
	
	int xValue(Weather w) {
		
		int x = 0;
		if (w.name() == "SUNNY") {
			x = 2;
		}
		else if (w.name() == "CLOUDY") {
			x = 3;
		}
		else if (w.name() == "RAINY") {
			x = 10;
		}
		else if (w.name() == "WINDY") {
			x = 15;
		}
		else if (w.name() == "STORM") {
			x = 20;
		}
		return x;
	}

}
