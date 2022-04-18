package simulator.view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;

public class ChangeCO2Class extends JFrame implements TrafficSimObserver{
	
	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private List<Vehicle> vehicles;
	private Controller ctrl;

	public ChangeCO2Class(Controller ctrl) {
		super("Custom Dialog Example");
		ctrl.addObserver(this);
		this.ctrl = ctrl;
		initGUI();
	}

	protected void initGUI() {

		ChangeCO2ClassDialog dialog = new ChangeCO2ClassDialog(this, ctrl);
		
		List<Vehicle> vehiculos = new ArrayList<Vehicle>();
		vehiculos = vehicles;
		
		List<Integer> numeros = new ArrayList<Integer>();
		for (int i = 0; i < 11; i++) {
			numeros.add(new Integer(i));
		}

		dialog.open(vehiculos, numeros);
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.vehicles = map.getVehicles();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.vehicles = map.getVehicles();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		this.vehicles = map.getVehicles();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.vehicles = map.getVehicles();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this.vehicles = map.getVehicles();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
