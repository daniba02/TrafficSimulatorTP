package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetWeatherEventBuilder extends Builder<Event>{

	public SetWeatherEventBuilder() {
		super("set_weather");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		
		JSONArray ja = new JSONArray();
		ja = data.getJSONArray("info");
		
		List<Pair<String, Weather>> list = new ArrayList<>();
		
		for(int i = 0; i < ja.length(); i++) {
			
			String road;
			Weather weather;
			
			road = ja.getJSONObject(i).getString("road").toString();
			weather = Weather.valueOf(ja.getJSONObject(i).getString("weather").toString().toUpperCase());
			list.add(new Pair<String, Weather>(road, weather));
		}

		return new SetWeatherEvent(data.getInt("time"), list);
	}

}
