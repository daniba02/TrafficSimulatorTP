package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;

public class TrafficSimulator {

	
	private RoadMap mapaCarreteras;
	private  List<Event> events;
	private int time;
	
	public TrafficSimulator(){
		
		mapaCarreteras = new RoadMap();
		events = new SortedArrayList<>();
		time = 0;
		
	}
	
	public void addEvent(Event e) {
		
		events.add(e);
		
		
		
		//queda ordenar la lista
	}
	
	public void advance() {
		
		time++;
		
		for(Event e: events) {
			
			if (e.getTime() == time) {
				e.execute(mapaCarreteras);
				events.remove(e);
			}
		}
		for(Junction j: mapaCarreteras.getJunctions()) {
			j.advance(time);
		}
		for(Road r: mapaCarreteras.getRoads()) {
			r.advance(time);
		}
	}
	
	public void reset() {
		
		mapaCarreteras.reset();
		events.clear();
		time = 0;
	}
	
	public JSONObject report() {
		
		JSONObject traffic = new JSONObject();
		
		traffic.put("time", time);
		traffic.put("state", mapaCarreteras.report());
		
		return traffic;
	}
}
