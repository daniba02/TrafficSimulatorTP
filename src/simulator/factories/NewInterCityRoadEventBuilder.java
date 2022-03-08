package simulator.factories;

import org.json.JSONObject;

import simulator.model.Event;
import simulator.model.NewInterCityRoadEvent;
import simulator.model.Weather;

public class NewInterCityRoadEventBuilder extends Builder<Event>{

	public NewInterCityRoadEventBuilder() {
		super("new_inter_city_road");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		int time, length, co2Limit, maxSpeed;
		String id, src, dest, weather;
		
		time = data.getInt("time");
		id = data.getString("id");
		src = data.getString("src");
		dest = data.getString("dest");
		length = data.getInt("length");
		co2Limit = data.getInt("co2Limit");
		maxSpeed = data.getInt("maxSpeed");
		weather = data.getString("weather");
		
		return new NewInterCityRoadEvent(time, id, src, dest, length, co2Limit, maxSpeed, Weather.valueOf(weather.toUpperCase()));
	}

}
