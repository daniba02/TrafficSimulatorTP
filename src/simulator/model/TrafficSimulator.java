package simulator.model;

import java.util.List;

import org.json.JSONObject;

import simulator.misc.SortedArrayList;
import simulator.model.TrafficSimObserver;

public class TrafficSimulator implements Observable<TrafficSimObserver>{

	
	private RoadMap mapaCarreteras;
	private  List<Event> events;
	private int time;
	private TrafficSimObserver obs;
	
	public TrafficSimulator(){
		
		mapaCarreteras = new RoadMap();
		events = new SortedArrayList<>();
		time = 0;
		
	}
	
	public void addEvent(Event e) {
		
		events.add(e);
		obs.onEventAdded(mapaCarreteras, events, e, time);
		
		
		//queda ordenar la lista
	}
	
	public void advance() {
		
		time++;
		
		obs.onAdvanceStart(mapaCarreteras, events, time);
		
		try {
			for(Event e: events) {
				
				if (e.getTime() == time) {
					e.execute(mapaCarreteras);
					//events.remove(e);
				}
			}
			
			// Aqui hay que eliminar los eventos de la lista de eventos
			
			
			for(Junction j: mapaCarreteras.getJunctions()) {
				j.advance(time);
			}
			for(Road r: mapaCarreteras.getRoads()) {
				r.advance(time);
			}
		}catch(IllegalArgumentException e) {
			obs.onError(e.getMessage());
			throw new IllegalArgumentException(e.getMessage());
		}
		
		
		obs.onAdvanceEnd(mapaCarreteras, events, time);
	}
	
	public void reset() {
		
		mapaCarreteras.reset();
		events.clear();
		time = 0;
		obs.onReset(mapaCarreteras, events, time);
	}
	
	public JSONObject report() {
		
		JSONObject traffic = new JSONObject();
		
		traffic.put("time", time);
		traffic.put("state", mapaCarreteras.report());
		
		return traffic;
	}

	@Override
	public void addObserver(TrafficSimObserver o) {
		obs = o;
		obs.onRegister(mapaCarreteras, events, time);
	}

	@Override
	public void removeObserver(TrafficSimObserver o) {
		// TODO Auto-generated method stub
		
	}
}
