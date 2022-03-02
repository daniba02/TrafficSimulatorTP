package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import simulator.exceptions.LimitException;
import simulator.exceptions.SimulatorException;
import simulator.exceptions.StatusException;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{

	
	private List<Junction> itinerary;
	private List<Junction> privItinerary;
	private int maxSpeed;
	private int speed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contClass;
	private int co2;
	private int distance;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
		
		try {
			compruebaSpeed(maxSpeed);
			compruebaContClass(contClass);
			compruebaItinerario(itinerary);
			this.maxSpeed = maxSpeed;
			this.contClass = contClass; 
			this.itinerary = itinerary;
			this.privItinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
		}
		catch (IllegalArgumentException ex){
			throw new IllegalArgumentException (ex.getMessage());
		}
	}

	@Override
	void advance(int time) {
		int lastLocation = location;
		int c;
		if(status == VehicleStatus.TRAVELING) {
			if (location + speed < road.getLength()) { 
				location = location + speed;
			}
			else {
				location = road.getLength();
			}
			
			c = (contClass * (location - lastLocation));
			co2 = co2 + c;
			try {
				road.addContaminacion(c);
			} catch (SimulatorException e) {
				e.printStackTrace();
			}
			if (location >= road.getLength()) {
				status = VehicleStatus.WAITING;
			}
		}
	}
	
	void moveToNextRoad() throws StatusException{
		
	}

	@Override
	public JSONObject report() {
		System.out.println("id" + ":" + super._id);
		System.out.println("speed" + ":" + speed);
		System.out.println("distance" + ":" + distance);
		System.out.println("co2" + ":" + co2);
		System.out.println("class" + ":" + contClass);
		System.out.println("status" + ":" + status.name());
		
		if (status != VehicleStatus.ARRIVED || status != VehicleStatus.PENDING) {
			System.out.println("road" + ":" + road);
			System.out.println("location" + ":" + location);
		}
		return null;
	}
	
	void setSpeed(int s) throws IllegalArgumentException{
		compruebaSpeed(s);
		if (s > maxSpeed) {
			speed = maxSpeed;
		}
		else speed = s;
	}
	
	void setContaminationClass(int c) throws IllegalArgumentException {
		compruebaContClass(c);
		contClass = c;
	}
	
	void compruebaSpeed(int maxSpeed) throws IllegalArgumentException {
		if (maxSpeed < 0) {
			throw new IllegalArgumentException("La velocidad tiene que ser positiva");
		}
	}
	
	void compruebaContClass(int contClass) throws IllegalArgumentException {
		if (contClass < 0 || contClass > 10) {
			throw new IllegalArgumentException("El grado de contaminacion tiene que estar entre 0 y 10");
		}
	}
	
	public void compruebaItinerario(List<Junction> itinerary) throws IllegalArgumentException{
		if (itinerary.size()<2) {
			throw new IllegalArgumentException("El tamaño de la lista tiene que ser mínimo 2");
		}
	}

	public List<Junction> getItinerary() {
		return itinerary;
	}

	public int getMaxSpeed() {
		return maxSpeed;
	}

	public int getSpeed() {
		return speed;
	}

	public VehicleStatus getEstado() {
		return status;
	}

	public Road getCarretera() {
		return road;
	}

	public int getLocalizacion() {
		return location;
	}

	public int getContClass() {
		return contClass;
	}

	public int getCo2() {
		return co2;
	}
	
	void velocidad0() {
		if (status.name()!= "TRAVELING") {
			speed = 0;
		}
	}
	
	@Override
	public int compareTo(Vehicle v) { 
		if (location < v.location) { 
			return -1; } 
		if (location > v.location) { 
			return 1; } 
		return 0; 
	}

}
