package simulator.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;
import simulator.model.TrafficSimObserver;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	
	private RoadMap mapaCarreteras;
	private  List<Event> events;
	private int time;
	private List<TrafficSimObserver> obs = new ArrayList<TrafficSimObserver>();
	//private TrafficSimObserver obs;
	
	public TrafficSimulator(){
		
		mapaCarreteras = new RoadMap();
		events = new SortedArrayList<>();
		time = 0;
		
	}
	
	public void addEvent(Event e) {
		
		events.add(e);
		for (TrafficSimObserver ob : obs )
			ob.onEventAdded(mapaCarreteras, events,e,  time);
		//obs.onEventAdded(mapaCarreteras, events, e, time);
		
		//queda ordenar la lista
	}
	
	public void advance() {
		
		time++;
		
		for (TrafficSimObserver ob : obs )
			ob.onAdvanceStart(mapaCarreteras, events, time);

		//obs.onAdvanceStart(mapaCarreteras, events, time);
		
		try {
			List<Event> eliminar = new ArrayList<Event>();
			for(Event e: events) {
				
				if (e.getTime() == time) {
					e.execute(mapaCarreteras);
					//events.remove(e);
					eliminar.add(e);
				}
			}
			events.removeAll(eliminar);
			
			// Aqui hay que eliminar los eventos de la lista de eventos
			
			
			for(Junction j: mapaCarreteras.getJunctions()) {
				j.advance(time);
			}
			for(Road r: mapaCarreteras.getRoads()) {
				r.advance(time);
			}
		}catch(IllegalArgumentException e) {
			for (TrafficSimObserver ob : obs )
				ob.onError(e.getMessage());
			//obs.onError(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
		
		for (TrafficSimObserver ob : obs )
			ob.onAdvanceEnd(mapaCarreteras, events, time);
		
		//obs.onAdvanceEnd(mapaCarreteras, events, time);
	}
	
	public void reset() {
		
		mapaCarreteras.reset();
		events.clear();
		time = 0;
		
		for (TrafficSimObserver ob : obs )
			ob.onReset(mapaCarreteras, events, time);
		
		//obs.onReset(mapaCarreteras, events, time);
	}
	
	public JSONObject report() {
		
		JSONObject traffic = new JSONObject();
		
		traffic.put("time", time);
		traffic.put("state", mapaCarreteras.report());
		
		return traffic;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		//obs = o;
		obs.add(o);
		for (TrafficSimObserver ob : obs )
			ob.onRegister(mapaCarreteras, events, time);
		//obs.onRegister(mapaCarreteras, events, time);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		
		obs.remove(o);
	}
	
	public List<Event> getEvents(){
		
		return events;
	}
	
	public List<Road> getRoads(){
		return mapaCarreteras.getRoads();
	}
	
	public RoadMap getMap() {
		return mapaCarreteras;
	}
}
