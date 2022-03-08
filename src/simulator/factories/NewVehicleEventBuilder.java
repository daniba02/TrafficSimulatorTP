package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.NewVehicleEvent;

public class NewVehicleEventBuilder extends Builder<Event>{

	public NewVehicleEventBuilder() {
		super("new_vehicle");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		int time, maxSpeed, clase;
		String id;
		
		time =data.getInt("time");
		id= data.getString("id");
		maxSpeed = data.getInt("maxSpeed");
		clase = data.getInt("class");
		
		JSONArray ja = new JSONArray();
		
		ja = data.getJSONArray("itinerary");
		
		List<String> itinerary = new ArrayList<>();
		
		for(int i = 0; i < ja.length(); i++) {
			
			itinerary.add(ja.get(i).toString());
		}
		
		return new NewVehicleEvent(time, id, maxSpeed, clase, itinerary);
	}

}
