package simulator.control;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import simulator.factories.Factory;
import simulator.model.Event;
import simulator.model.TrafficSimulator;

public class Controller {
	
	
	private TrafficSimulator sim;
	private Factory<Event> eventsFactory;
	
	public Controller(TrafficSimulator sim, Factory<Event> eventsFactory){
	
		if (sim == null || eventsFactory == null) {
			throw new IllegalArgumentException("Los argumentos no pueden ser nulos");
		}
		this.sim = sim;
		this.eventsFactory = eventsFactory;
	}
	
	public void run(int n, OutputStream out) {
		
		JSONObject object = new JSONObject();
		JSONArray array = new JSONArray();
		
		for (int i = 0; i < n; i++) {
			sim.advance();
			array.put(sim.report());
		}
		object.put("states", array);
		PrintStream p = new PrintStream(out);
		p.println(object);
		
	}
	
	public void reset() {
		sim.reset();
	}
	
	public void loadEvents(InputStream in) {
		
		JSONObject jo = new JSONObject(new JSONTokener(in));
		
		if (!jo.has("events")) {
			
			throw new IllegalArgumentException("No tiene eventos");
		}
		else {
			JSONArray ja = jo.getJSONArray("events");
			
			for(int i = 0; i< ja.length(); i++) {
				
				Event e;
				e = eventsFactory.createInstance(ja.getJSONObject(i));
				sim.addEvent(e);
			}
		}
		
	}
	
	
	
}

