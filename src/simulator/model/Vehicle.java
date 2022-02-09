package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

import simulator.exceptions.LimitException;
import simulator.exceptions.SimulatorException;
import simulator.exceptions.StatusException;

public class Vehicle extends SimulatedObject{

	
	private List<Junction> itinerary;
	private int maxSpeed;
	private int speed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contClass;
	private int co2;
	private int distance;
	
	protected Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) throws SimulatorException {
		super(id);
		
		try {
			compruebaSpeed(maxSpeed);
			compruebaContClass(contClass);
			compruebaItinerario();
			
		}
		catch (SimulatorException ex){
			
		}
		finally {
			this.maxSpeed = maxSpeed;
			this.contClass = contClass;
		}
	}

	@Override
	void advance(int time) {
		if(status == VehicleStatus.TRAVELING) {
			if (location + speed < distance) { 
				// IMPORTANTE: En vez de distancia hay que usar la longitud de la carretera
				location = location + speed;
			}
			
			else {
				location = distance;
			}
			
			co2 += distance * contClass;
			if (location >= distance) {
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
	
	void setSpeed(int s) throws LimitException{
		compruebaSpeed(s);
		if (s > maxSpeed) {
			speed = s;
		}
		else speed = maxSpeed;
	}
	
	void setContaminationClass(int c) throws LimitException {
		compruebaContClass(c);
		contClass = c;
	}
	
	void compruebaSpeed(int maxSpeed) throws LimitException {
		if (maxSpeed < 0) {
			throw new LimitException("La velocidad tiene que ser positiva");
		}
	}
	
	void compruebaContClass(int contClass) throws LimitException {
		if (contClass < 0 || contClass > 10) {
			throw new LimitException("El grado de contaminacion tiene que estar entre 0 y 10");
		}
	}
	
	public void compruebaItinerario() {
		
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
	
	

}
