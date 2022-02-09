package simulator.model;

import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import simulator.exceptions.LimitException;
import simulator.exceptions.SimulatorException;

public abstract class Road extends SimulatedObject{

	private Junction dest;
	private Junction src;
	private int length;
	protected int maxSpeed;
	protected int speedLimit;
	protected int alarma;
	protected Weather weather;
	protected int totalCO2;
	private List<Vehicle> vehicle;
	
	Road(String id, Junction srcJunc, Junction destJunc, int maxSpeed,int contLimit, int length, Weather weather) {
		super(id);
		this.src = srcJunc;
		this.dest = destJunc;
			
	}
	
	abstract void reduceTotalContamination();
	abstract void updateSpeedLimit();
	abstract int calculateVehicleSpeed(Vehicle v);


	@Override
	void advance(int time) {
		reduceTotalContamination();
		updateSpeedLimit();
		for(Vehicle v: vehicle) {
			
			try {
				v.setSpeed(calculateVehicleSpeed(v));
			} catch (LimitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			v.advance(time);
		}
		
		//Ordenar la lista
	}
	
	void enter(Vehicle v) throws SimulatorException {
		if (v.getLocalizacion() != 0 || v.getSpeed() != 0) {
			throw new SimulatorException("La localizacion y la velocidad del vehiculo tienen que ser 0");
		}
		else vehicle.add(v);
	}
	
	void exit(Vehicle v) {
		vehicle.remove(v);
	}
	
	void setWeather(Weather w) throws SimulatorException {
		try {
			compruebaNull(w);
			weather = w;
		}
		catch (SimulatorException e) {
			throw new SimulatorException(e.getMessage());
		}
	}
	
	void addContaminacion(int c) throws SimulatorException {
		try {
			compruebaLimit(c);
			totalCO2 += c;
		}
		catch (LimitException e) {
			throw new SimulatorException(e.getMessage());
		}
	}

	@Override
	public JSONObject report() {
		System.out.println("id : " + super._id);
		System.out.println("speedLimit : " + speedLimit);
		System.out.println("weather : " + weather.name());
		System.out.println("co2 : " + totalCO2);
		System.out.print("vehicles : " + vehicle);
		return null;
	}
	
	void compruebaSpeed(int maxSpeed) throws LimitException {
		if (maxSpeed < 0) {
			throw new LimitException("La velocidad tiene que ser positiva");
		}
	}
	
	void compruebaLimit(int speedLimit) throws LimitException {
		if (speedLimit < 0) {
			throw new LimitException("El limite tiene que ser positivo");
		}
	}
	
	void compruebaLength(int length) throws LimitException {
		if (length < 0) {
			throw new LimitException("La distancia tiene que ser positiva");
		}
	}
	
	void compruebaNull(Junction j) throws SimulatorException {
		if (j == null) {
			throw new SimulatorException("El valor no puede ser nulo");
		}
	}
	
	void compruebaNull(Weather w) throws SimulatorException {
		if (w == null) {
			throw new SimulatorException("El tiempo no puede ser nulo");
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
		return alarma;
	}

	public Weather getWeather() {
		return weather;
	}

	public int getTotalCO2() {
		return totalCO2;
	}

	public List<Vehicle> getVehicle() {
		return Collections.unmodifiableList(vehicle);
	}
	
	

}
