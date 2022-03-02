package simulator.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import simulator.exceptions.LimitException;
import simulator.exceptions.SimulatorException;

public class Junction extends SimulatedObject{
	
	private List<Road> road;
	private Map<Junction,Road> mapa;
	private List<List<Vehicle >> colas;
	private int semaforo;
	private int cambiosemaforo;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;
	
	
	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) throws SimulatorException {
		super(id);
		try {
			compruebaNull(lsStrategy);
			compruebaNull(dqStrategy);
			compruebaLimit(xCoor);
			compruebaLimit(yCoor);
			this.lsStrategy = lsStrategy;
			this.dqStrategy = dqStrategy;
			this.xCoor = xCoor;
			this.yCoor = yCoor;
		}
		catch(SimulatorException e) {
			throw new SimulatorException(e.getMessage());
		}
	}

	@Override
	void advance(int time) {
		
		dqStrategy.dequeue(q)
		
	}

	@Override
	public JSONObject report() {
		// TODO Auto-generated method stub
		return null;
	}
	
	void compruebaLimit(int length) throws LimitException {
		if (length < 0) {
			throw new LimitException("La distancia tiene que ser positiva");
		}
	}
	
	void compruebaNull(LightSwitchingStrategy lsStrategy) throws SimulatorException {
		if (lsStrategy == null) {
			throw new SimulatorException("El valor no puede ser nulo");
		}
	}
	
	void compruebaNull(DequeuingStrategy dqStrategy) throws SimulatorException {
		if (dqStrategy == null) {
			throw new SimulatorException("El valor no puede ser nulo");
		}
	}
	
	void addIncommingRoad(Road r) throws SimulatorException {
		List<Road> aux = new LinkedList<Road>();
		
		if (r.getDest() != this) {
			throw new SimulatorException("No pertenece al cruce");
		}
		else aux.add(r);
	}

	void addOutGoingROad(Road r) {
		//Hay que hacerla
	}
	
	void enter(Vehicle v) {
		
		try {
			v.getCarretera().enter(v);
		} catch (SimulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Mirar si es con un try/catch o lanzando excepcion
	}
	
	Road roadTo(Junction j) {
		
		Road r;
		
		return null;
		
		//No esta hecha
	}

}
