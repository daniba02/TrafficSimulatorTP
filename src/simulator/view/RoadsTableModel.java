package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class RoadsTableModel extends AbstractTableModel implements TrafficSimObserver{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Road> _roads;
	private String[] _colNames = { "Id", "Length", "Weather", "Max Speed", "SpeedLimit", "Total CO2", "CO2Limit" };
	
	public RoadsTableModel(Controller ctrl) {
		update();
		ctrl.addObserver(this);
	}
	
	public void setRoadsList(List<Road> roads) {
		_roads = roads;
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
	
	public int getRowCount() {
		return _roads == null ? 0 : _roads.size();
	}

	@Override
	
	public Object getValueAt(int rowIndex, int columnIndex) {
		Object s = null;
		switch (columnIndex) {
		case 0:
			s = _roads.get(rowIndex).getId();
			break;
		case 1:
			s = _roads.get(rowIndex).getLength();
			break;
		case 2:
			s = _roads.get(rowIndex).getWeather();
			break;
		case 3:
			s = _roads.get(rowIndex).getMaxSpeed();
			break;
		case 4:
			s = _roads.get(rowIndex).getSpeedLimit();
			break;
		case 5:
			s = _roads.get(rowIndex).getTotalCO2();
			break;
		case 6:
			s = _roads.get(rowIndex).getContLimit();
			break;
		}
		
		return s;
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		//this._roads = map.getRoads();
		//update();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._roads = map.getRoads();
		update();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._roads = map.getRoads();
		update();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._roads = map.getRoads();
		update();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._roads = map.getRoads();
		update();
		
	}

	@Override
	public void onError(String err) {

	}
}
