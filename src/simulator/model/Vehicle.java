package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.JSONObject;

public class Vehicle extends SimulatedObject implements Comparable<Vehicle>{

	
	private List<Junction> itinerary;
	private int maxSpeed;
	private int speed;
	private VehicleStatus status;
	private Road road;
	private int location;
	private int contClass;
	private int co2;
	private int distance;
	private int indice;
	
	Vehicle(String id, int maxSpeed, int contClass, List<Junction> itinerary) {
		super(id);
			compruebaSpeed(maxSpeed);
			compruebaContClass(contClass);
			compruebaItinerario(itinerary);
			
			this.maxSpeed = maxSpeed;
			this.contClass = contClass; 
			this.itinerary = Collections.unmodifiableList(new ArrayList<>(itinerary));
			this.status = VehicleStatus.PENDING;
			this.location = 0;
			this.co2 = 0;
			this.distance = 0;
			this.indice = 0;
			this.speed = 0;
			this.road = null; 
	}

	@Override
	void advance(int time) {
		int lastLocation = location;
		int c;
		if(status == VehicleStatus.TRAVELING) {
			if ((location + speed) <= road.getLength()) { 
				location = location + speed;
			}
			else {
				location = road.getLength();
			}
			
			c = (contClass * (location - lastLocation));
			co2 = co2 + c;
			road.addContamination(c);
			
			if (location == road.getLength()) {
				road.getDest().enter(this); 
				indice++; 
				speed = 0;
				status = VehicleStatus.WAITING;
			}
		}
	}
	
	void moveToNextRoad(){
		
		
		if(status.name() == "PENDING" || status.name() == "WAITING") {
			if(road != null) { 
				road.exit(this);
			}
			if(indice == itinerary.size() - 1) {
				road = null;
				status = VehicleStatus.ARRIVED;
				speed = 0;
			}
			else {
				road = itinerary.get(indice).roadTo(itinerary.get(indice + 1));
				status = VehicleStatus.TRAVELING; 
				location = 0;
				road.enter(this);
			}
		}
		else {
			throw new IllegalArgumentException("El estado del vehiculo tiene que ser esperando o pendiente");
		}
	}

	@Override
	public JSONObject report() {
		
		JSONObject vehicle = new JSONObject();
		
		vehicle.put("id", _id);
		vehicle.put("speed", speed);
		vehicle.put("distance", distance);
		vehicle.put("co2", co2);
		vehicle.put("class", contClass);
		vehicle.put("status", status.name());
		
		if(status.name() != "PENDING" && status.name() != "ARRIVED") {
			vehicle.put("road", road.getId());
			vehicle.put("location", location);
		}
		
		return vehicle;
	}
	
	void setSpeed(int s) {
		if (s<0) {
			throw new IllegalArgumentException("La velocidad tiene que ser positiva");
		}
		else if(status.name()=="TRAVELING") {
			
			if (s >= maxSpeed) {
				speed = maxSpeed;
			}
			else speed = s;
		}
		
	}
	
	void setContClass(int c){
		compruebaContClass(c);
		contClass = c;
		
	}
	
	void compruebaSpeed(int maxSpeed){
		if (maxSpeed <= 0) {
			throw new IllegalArgumentException("La velocidad tiene que ser positiva");
		}
	}
	
	void compruebaContClass(int contClass){
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

	public VehicleStatus getStatus() {
		return status;
	}

	public Road getRoad() {
		return road;
	}

	public int getLocation() {
		return location;
	}

	public int getContClass() {
		return contClass;
	}

	public int getTotalCO2() {
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
		else if (location > v.location) { 
			return 1; } 
		else return 0; 
	}

}


