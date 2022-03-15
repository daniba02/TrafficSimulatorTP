package simulator.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public abstract class Road extends SimulatedObject{

	private Junction src;
	private Junction dest;
	private int length;
	protected int maxSpeed;
	protected int speedLimit;
	protected int contLimit;
	protected Weather weather;
	protected int totalCO2 = 0;
	private List<Vehicle> vehicles;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,
			int contLimit, int length, Weather weather){
		super(id);
		this.src = srcJunc;
		this.dest = destJunc;
		
			compruebaSpeed(maxSpeed);
			compruebaLimit(contLimit);
			compruebaLength(length);
			compruebaNull(srcJunc);
			compruebaNull(destJunc);
			compruebaNull(weather);
			this.maxSpeed = maxSpeed;
			this.contLimit = contLimit;
			this.length = length;
			this.weather = weather;
			this.vehicles = new ArrayList<>();
			destJunc.addIncommingRoad(this);
			srcJunc.addOutGoingRoad(this);
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);


	@Override
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v: vehicles) {
			

			v.setSpeed(calculateVehicleSpeed(v));
			v.advance(time);
		}
		
		Collections.sort(vehicles);
		//Ordenar la lista
	}
	
	void enter(Vehicle v) {
		if (v.getSpeed() != 0) {
			throw new IllegalArgumentException("La velocidad tiene que ser 0");
		}
		else if(v.getLocation() != 0) {
			throw new IllegalArgumentException("La localizacion tiene que ser 0");
		}
		else vehicles.add(v);
	}
	
	void exit(Vehicle v) {
		vehicles.remove(v);
	}
	
	void setWeather(Weather w) throws IllegalArgumentException {
		try {
			compruebaNull(w);
			weather = w;
		}
		catch (IllegalArgumentException e) {
			throw new IllegalArgumentException(e.getMessage());
		}
	}
	
	void addContamination(int c){
		
		if (c < 0) {
			throw new IllegalArgumentException("El valor tiene que ser mayor que 0");
		}
		totalCO2 += c;
	}

	@Override
	public JSONObject report() {
		
		JSONObject road = new JSONObject();
		
		road.put("id",  super._id);
		road.put("speedLimit", speedLimit);
		road.put("weather", weather.name());
		road.put("co2", totalCO2);
		
		JSONArray ja = new JSONArray();
		
		for(Vehicle v: vehicles) {
			ja.put(v.getId());
		}
		
		road.put("vehicles",  ja);
		
		return road;
	}
	
	void compruebaSpeed(int maxSpeed) throws IllegalArgumentException {
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("La velocidad tiene que ser positiva");
		}
	}
	
	void compruebaLimit(int speedLimit) throws IllegalArgumentException {
		if (speedLimit <= 0) {
			throw new IllegalArgumentException("El limite tiene que ser positivo");
		}
	}
	
	void compruebaLength(int length) throws IllegalArgumentException {
		if (length <= 0) {
			throw new IllegalArgumentException("La distancia tiene que ser positiva");
		}
	}
	
	void compruebaNull(Junction j) throws IllegalArgumentException {
		if (j == null) {
			throw new IllegalArgumentException("El valor no puede ser nulo");
		}
	}
	
	void compruebaNull(Weather w) throws IllegalArgumentException {
		if (w == null) {
			throw new IllegalArgumentException("El tiempo no puede ser nulo");
		}
	}

	public Junction getDest() {
		return dest;
	}

	public Junction getSrc() {
		return src;
	}

	public int getLength() {
		return length;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getSpeedLimit() {
		return speedLimit;
	}

	public int getContLimit() {
		return contLimit;
	}

	public Weather getWeather() {
		return weather;
	}

	public int getTotalCO2() {
		return totalCO2;
	}

	public List<Vehicle> getVehicles() {
		return Collections.unmodifiableList(vehicles);
	}
}
