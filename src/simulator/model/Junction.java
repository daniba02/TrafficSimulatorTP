package simulator.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

public class Junction extends SimulatedObject {

	private List<Road> roads;
	private Map<Junction, Road> mapa;
	private List<List<Vehicle>> colas;
	private Map<Road, List<Vehicle>> carreteraCola;
	private int semaforo= -1;
	private int cambiosemaforo = 0;
	private LightSwitchingStrategy lsStrategy;
	private DequeuingStrategy dqStrategy;
	private int xCoor;
	private int yCoor;

	Junction(String id, LightSwitchingStrategy lsStrategy, DequeuingStrategy dqStrategy, int xCoor, int yCoor) {
		super(id);
		compruebaNull(lsStrategy);
		compruebaNull(dqStrategy);
		compruebaLimit(xCoor);
		compruebaLimit(yCoor);
		this.lsStrategy = lsStrategy;
		this.dqStrategy = dqStrategy;
		this.xCoor = xCoor;
		this.yCoor = yCoor;
		this.roads = new ArrayList<>();
		this.mapa = new HashMap<>();
		this.colas = new ArrayList<>();
		this.carreteraCola = new HashMap<>();
	}

	@Override
	void advance(int time) {

		int aux;
		List<List<Vehicle>> listaAux = new LinkedList<List<Vehicle>>();

		for (List<Vehicle> list : colas) {
			listaAux.add(dqStrategy.dequeue(list));
		}

		colas.removeAll(listaAux);

		aux = lsStrategy.chooseNextGreen(roads, colas, semaforo, cambiosemaforo, time);

		if (aux != semaforo) {
			semaforo = aux;
			cambiosemaforo = time;
		}
	}

	@Override
	public JSONObject report() {

		JSONObject junction = new JSONObject();

		junction.put("id", super._id);

		if (semaforo == -1) {

			junction.put("green", "none");
		} else if (!roads.isEmpty()) {
			junction.put("green", roads.get(semaforo).getId());
		}

		JSONArray ja = new JSONArray();

		for (int i = 0; i < roads.size(); i++) {

			JSONObject j2 = new JSONObject();

			j2.put("road", roads.get(i).getId());

			JSONArray ja2 = new JSONArray();

			for (Vehicle v : carreteraCola.get(roads.get(i))) {

				ja2.put(v.getId());
			}

			j2.put("vehicles", ja2);
			ja.put(j2);
		}

		junction.put("queues", ja);

		return junction;
	}

	void compruebaLimit(int length) throws IllegalArgumentException {
		if (length < 0) {
			throw new IllegalArgumentException("La distancia tiene que ser positiva");
		}
	}

	void compruebaNull(LightSwitchingStrategy lsStrategy) throws IllegalArgumentException {
		if (lsStrategy == null) {
			throw new IllegalArgumentException("El valor no puede ser nulo");
		}
	}

	void compruebaNull(DequeuingStrategy dqStrategy) throws IllegalArgumentException {
		if (dqStrategy == null) {
			throw new IllegalArgumentException("El valor no puede ser nulo");
		}
	}

	void addIncommingRoad(Road r) throws IllegalArgumentException {
		List<Vehicle> aux = new LinkedList<Vehicle>();

		if (r.getDest() != this) {
			throw new IllegalArgumentException("No pertenece al cruce");
		} else {
			roads.add(r);

			for (Vehicle v : r.getVehicles()) {
				aux.add(v);
			}
			colas.add(aux);
			carreteraCola.put(r, aux);
		}
	}

	void addOutGoingRoad(Road r) {
		// Hay que hacerla

		mapa.put(r.getDest(), r);
	}

	void enter(Vehicle v) {

		try {
			v.getRoad().enter(v);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(e.getMessage());
		}

		// Mirar si es con un try/catch o lanzando excepcion
	}

	Road roadTo(Junction j) {

		Iterator<Junction> it = mapa.keySet().iterator();
		Road aux;

		while (it.hasNext()) {

			Junction key = (Junction) it.next();

			aux = mapa.get(key);

			if (aux.getSrc() == this && aux.getDest() == j) {
				return aux;
			}
		}

		return null;
	}

}
