package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Observable;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class VehiclesTableModel extends AbstractTableModel implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Vehicle> _vehiculos;
	private String[] _colNames = { "Id", "Location", "Itinerary", "CO2 Class", "Max Speed", "Speed", "Total CO2", "Distance" };
	
	public VehiclesTableModel(Controller ctrl) {
		update();
		ctrl.addObserver(this);
	}
	
	public void setEventsList(List<Vehicle> vehicles) {
		_vehiculos = vehicles;
		update();
	}
	
	public void update() {
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public String getColumnName(int col) {
		return _colNames[col];
	}
	
	@Override
	public int getColumnCount() {
		return _colNames.length;
	}

	@Override
	// método obligatorio
	//
	// the number of row, like those in the events list
	public int getRowCount() {
		return _vehiculos == null ? 0 : _vehiculos.size();
	}

	@Override
	// método obligatorio
	// así es como se va a cargar la tabla desde el ArrayList
	// el índice del arrayList es el número de fila pq en este ejemplo
	// quiero enumerarlos.
	//
	// returns the value of a particular cell 
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _vehiculos.get(rowIndex).getId();
			break;
		case 1:
			s = _vehiculos.get(rowIndex).getRoad() + ":" + _vehiculos.get(rowIndex).getLocation();
			break;
		case 2:
			s = _vehiculos.get(rowIndex).getItinerary();
			break;
		case 3:
			s = _vehiculos.get(rowIndex).getContClass();
			break;
		case 4:
			s = _vehiculos.get(rowIndex).getMaxSpeed();
			break;
		case 5:
			s = _vehiculos.get(rowIndex).getSpeed();
			break;
		case 6:
			s = _vehiculos.get(rowIndex).getTotalCO2();
			break;
		case 7:
			s = _vehiculos.get(rowIndex).getDistance();
			break;
		}
		
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._vehiculos = map.getVehicles();
		update();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._vehiculos = map.getVehicles();
		update();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._vehiculos = map.getVehicles();
		update();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._vehiculos = map.getVehicles();
		update();
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}


}
