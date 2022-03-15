package simulator.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class RoadMap {
	
	private List<Junction> cruces;
	private List<Road> carreteras; 
	private List<Vehicle> vehiculos;
	private Map<String, Junction> mapaCruces;
	private Map<String, Road> mapaCarreteras;
	private Map<String, Vehicle> mapaVehiculos;
	
	RoadMap(){
		
		this.cruces = new ArrayList<>();
		this.carreteras = new ArrayList<>();
		this.vehiculos = new ArrayList<>();
		this.mapaCruces = new HashMap<>();
		this.mapaCarreteras = new HashMap<>();
		this.mapaVehiculos = new HashMap<>();
	}
	
	void addJunction(Junction j) {

		compruebaKeyJ(j);
		cruces.add(j);
		mapaCruces.put(j.getId(), j);
		
	}
	void addRoad(Road r) {
		
		compruebaKeyC(r);
		
		if(!mapaCruces.containsKey(r.getDest().getId()) || !mapaCruces.containsKey(r.getSrc().getId())) {
			throw new IllegalArgumentException("La carretera no existe en el mapa");
		}
		
		//tenemos el cruce destino y el cruce origen, para comprobar si existen en el mapa de carreteras tenemos
		
		carreteras.add(r);
		mapaCarreteras.put(r.getId(), r);
		
	}
	void addVehicle(Vehicle v) {
		
		compruebaKeyV(v);
		
		for(int i = 0; i < v.getItinerary().size() - 1; i++) {
			if(v.getItinerary().get(i).roadTo(v.getItinerary().get(i + 1)) == null)
				throw new IllegalArgumentException("No existe el itinerario");
		}
		
		vehiculos.add(v);
		mapaVehiculos.put(v.getId(), v);
	}
	
	void compruebaKeyJ(Junction j) {
		if(mapaCruces.containsKey(j.getId())){
			throw new IllegalArgumentException("Ya existe el cruce");
		}
	}
	void compruebaKeyV(Vehicle v) {
		if(mapaVehiculos.containsKey(v.getId()))
			throw new IllegalArgumentException("Ya existe el id");
	}
	
	void compruebaKeyC(Road c) {
		if(mapaCarreteras.containsKey(c.getId()))
			throw new IllegalArgumentException("Ya existe el id");
	}
	public Junction getJunction(String id) {
		
		return mapaCruces.get(id);
	}
	
	public Road getRoad(String id) {
		
		return mapaCarreteras.get(id);
	}
	
	public Vehicle getVehicle(String id) {
		
		return mapaVehiculos.get(id);
	}
	
	public List<Junction>getJunctions(){
		
		return Collections.unmodifiableList(cruces);
	}
	public List<Road>getRoads(){
		
		return Collections.unmodifiableList(carreteras);
	}
	public List<Vehicle>getVehicles(){
		
		return Collections.unmodifiableList(vehiculos);
	}
	void reset() {
		
		cruces.clear();
		carreteras.clear();
		vehiculos.clear();
		mapaCruces.clear();
		mapaCarreteras.clear();
		mapaVehiculos.clear();
	}
	
	public JSONObject report() {
		
		JSONObject roadmap = new JSONObject();
		
		JSONArray ja = new JSONArray();
		JSONArray ja2 = new JSONArray();
		JSONArray ja3 = new JSONArray();
		
		for(Junction j: cruces) {
			
			ja.put(j.report());
		}
		
		for(Road r : carreteras) {
			
			ja2.put(r.report());
		}
		
		for(Vehicle v : vehiculos) {
			
			ja3.put(v.report());
		}
		
		roadmap.put("junctions", ja);
		roadmap.put("roads", ja2);
		roadmap.put("vehicles", ja3);
		
		return roadmap;
	}
}
