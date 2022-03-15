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
			int ind = -1;
			int max = -1;
			//List<Vehicle> aux;
			
			for (int i =0; i < qs.size(); i++) {
				if(qs.get(i).size() > max) {
					ind = i;
					max = qs.get(i).size();
				}
			}
			/*aux = qs.get(ind1);
			for (int i = 0; i < qs.size(); i++) {
				if(qs.get(i).size() > aux.size()) {
					ind = i;
				}
			}*/
			return ind;
		}
		else if (currTime-lastSwitchingTime < timeSlot) {
			return currGreen;
		}
		else {
			int ind = -1;
			int max = -1;
			
			for (int i = currGreen+1; i %qs.size() != currGreen; i++) {
				if(qs.get(i%qs.size()).size() > max) {
					ind = i%qs.size();
					max = qs.get(i%qs.size()).size();
				}
		
			}
			if(qs.get(ind).size() < qs.get(currGreen).size()) {
				ind = currGreen;
			}
			return ind;
		}
	}
	

}

