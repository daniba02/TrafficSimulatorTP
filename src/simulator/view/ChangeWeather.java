package simulator.view;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Weather;

public class ChangeWeather extends JFrame implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Road> _roads;
	private Controller ctrl;
	
	public ChangeWeather(Controller ctrl) {
		super("Custom Dialog Example");
		ctrl.addObserver(this);
		this.ctrl = ctrl;
		initGUI();
	}

	protected void initGUI() {

		// if you're in a JPanel class, you can use the following
		//
		// (Frame) SwingUtilities.getWindowAncestor(this)
		//
		// in order to get the parent JFrame. Then pass it to the constructor
		// of MyDialogWindow instead of 'this'
		//

		ChangeWeatherDialog dialog = new ChangeWeatherDialog(this, ctrl);
		
		List<Road> roads = new ArrayList<Road>();
		List<Weather> weather = new ArrayList<Weather>();
		
		weather.add(Weather.SUNNY);
		weather.add(Weather.CLOUDY);
		weather.add(Weather.RAINY);
		weather.add(Weather.WINDY);
		weather.add(Weather.STORM);

		dialog.open(_roads, weather);
		//int status = dialog.open2(numeros);
		/*
		if (status == 0) {
			System.out.println("No se ha escogido ninguno");
		} else {
			System.out.println("Your favorite dish is: " + dialog.getRoad());
			System.out.println("Your vehicle is: " + dialog.getWeather());
		}*/
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this._roads = map.getRoads();
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this._roads = map.getRoads();
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		// TODO Auto-generated method stub
		this._roads = map.getRoads();
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this._roads = map.getRoads();
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		this._roads = map.getRoads();
	}

	@Override
	public void onError(String err) {
		// TODO Auto-generated method stub
		
	}
}
