package simulator.model;

import java.util.List;

public class MostCrowdedStrategy implements LightSwitchingStrategy{

	private int timeSlot;
	public MostCrowdedStrategy(int timeSlot) {
		this.timeSlot = timeSlot;
	}

	@Override
	public int chooseNextGreen(List<Road> roads, List<List<Vehicle>> qs, int currGreen, int lastSwitchingTime,
			int currTime) {
		
		if (roads.isEmpty()) {
			return -1;
		}
		else if (currGreen == -1) {
			int ind = 0;
			List<Vehicle> aux;
			aux = qs.get(0);
			for (int i = 0; i < qs.size(); i++) {
				if(qs.get(i).size() > aux.size()) {
					ind = i;
				}
			}
			return ind;
		}
		else if (currTime-lastSwitchingTime < timeSlot) {
			return currGreen;
		}
		else return currGreen+1%roads.size();
	}
	

}

