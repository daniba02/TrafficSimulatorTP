package simulator.model;

import java.util.List;

import simulator.misc.Pair;

public class SetWeatherEvent extends Event{

	private List<Pair<String,Weather>> ws;
	
	public SetWeatherEvent(int time, List<Pair<String,Weather>> ws) {
		
		super(time);
		
		if (ws == null) {
			 throw new IllegalArgumentException("EL valor no puede ser nulo");
		}
		this.ws = ws;
	}

	@Override
	
	
	void execute(RoadMap map) {
		// TODO Auto-generated method stub
			
		for(Pair<String,Weather> w: ws) {
			
			Road r = map.getRoad(w.getFirst());
			
			if (r == null) {
				throw new IllegalArgumentException("La carretera es nula");
			}
			else {
				r.setWeather(w.getSecond());
			}
		}
	}
	
	@Override
	public String toString() {
		String s = "";
		
		for(Pair<String,Weather> w: ws) {
			
			s+="(" + w.getFirst().toString() + ",";
			s+=w.getSecond().toString()+ ")";
		}
		return "Change Weather: ["+s+"]";
	}
	
}
