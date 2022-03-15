package simulator.factories;

import org.json.JSONArray;
import org.json.JSONObject;

import simulator.model.DequeuingStrategy;
import simulator.model.Event;
import simulator.model.LightSwitchingStrategy;
import simulator.model.NewJunctionEvent;

public class NewJunctionEventBuilder extends Builder<Event>{

	
	private Factory<LightSwitchingStrategy>lssFactory;
	private Factory<DequeuingStrategy> dqsFactory;
	
	public NewJunctionEventBuilder(Factory<LightSwitchingStrategy>lssFactory, Factory<DequeuingStrategy> dqsFactory) {
		super("new_junction");
		
		this.lssFactory = lssFactory;
		this.dqsFactory = dqsFactory;
	}

	@Override
	protected Event createTheInstance(JSONObject data) {
		// TODO Auto-generated method stub
		
		int time;
		String id;
		
		time = data.getInt("time");
		id = data.getString("id");
		
		JSONArray ja = new JSONArray();
		
		ja = data.getJSONArray("coor");
		
		LightSwitchingStrategy lsStrategy = lssFactory.createInstance(data.getJSONObject("ls_strategy"));
        DequeuingStrategy dqStrategy = dqsFactory.createInstance(data.getJSONObject("dq_strategy"));
		
		return new NewJunctionEvent(time, id, lsStrategy, dqStrategy, ja.getInt(0), ja.getInt(1));
	}

}
