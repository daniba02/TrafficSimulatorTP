package simulator.factories;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.factories.Builder;
import simulator.misc.Pair;
import simulator.model.Event;
import simulator.model.NewSetContClassEvent;
import simulator.model.SetWeatherEvent;
import simulator.model.Weather;

public class SetContClassEventBuilder extends Builder<Event>{

	public SetContClassEventBuilder() {
		super("set_cont_class");
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		
		JSONArray ja = new JSONArray();
		ja = data.getJSONArray("info");
		
		List<Pair<String, Integer>> list = new ArrayList<>();
		
		for(int i = 0; i < ja.length(); i++) {
			
			String vehicle;
			Integer in;
			
			vehicle = ja.getJSONObject(i).getString("vehicle").toString();
			in = ja.getJSONObject(i).getInt("class");
			
			list.add(new Pair<String, Integer>(vehicle, in));
		}
		
		return new NewSetContClassEvent(data.getInt("time"), list);
	}

}
