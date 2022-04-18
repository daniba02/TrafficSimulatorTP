package simulator.view;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class JunctionsTableModel extends AbstractTableModel implements TrafficSimObserver{

	
	private static final long serialVersionUID = 1L;
	private List<Junction> _junctions;
	private String[] _colNames = { "Id", "Green", "Queues"};
	
	public JunctionsTableModel(Controller ctrl) {
		update();
		ctrl.addObserver(this);
	}
	
	public void setEventsList(List<Junction> roads) {
		_junctions = roads;
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
		return _junctions == null ? 0 : _junctions.size();
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
			s = _junctions.get(rowIndex).getId();
			break;
		case 1:
			//s = _junctions.get(rowIndex).getGreenLightIndex();
			s = verde(_junctions.get(rowIndex));
			break;
		case 2:
			s = _junctions.get(rowIndex).getInRoads();
			break;
		}
		
		return s;
	}
	
	public String verde(Junction j) {
		
		if (j.getGreenLightIndex() == -1) {
			return "NONE";
		}
		else return j.getInRoads().get(j.getGreenLightIndex()).toString();
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		this._junctions = map.getJunctions();
		update();
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		this._junctions = map.getJunctions();
		update();
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		this._junctions = map.getJunctions();
		update();
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		this._junctions = map.getJunctions();
		update();
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		this._junctions = map.getJunctions();
		update();
		
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
