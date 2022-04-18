package simulator.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Junction;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;
import simulator.model.Vehicle;
import simulator.model.VehicleStatus;
import simulator.model.Weather;

public class MapByRoadComponent extends JComponent implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final int _JRADIUS = 10;
	
	private static final Color _BG_COLOR = Color.WHITE;
	private static final Color _JUNCTION_COLOR = Color.BLUE;
	private static final Color _JUNCTION_LABEL_COLOR = new Color(200, 100, 0);
	private static final Color _GREEN_LIGHT_COLOR = Color.GREEN;
	private static final Color _RED_LIGHT_COLOR = Color.RED;
	
	private RoadMap _map;

	private Image _car;
	private Image weather;
	private Image contClass;
	
	public MapByRoadComponent(Controller ctrl) {
		setPreferredSize(new Dimension(300, 200));
		initGUI();
		ctrl.addObserver(this);
	}
	
	private void initGUI() {
		_car = loadImage("car.png");
	}
	
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D g = (Graphics2D) graphics;
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		// clear with a background color
		g.setColor(_BG_COLOR);
		g.clearRect(0, 0, getWidth(), getHeight());

		if (_map == null || _map.getJunctions().size() == 0) {
			g.setColor(Color.red);
			g.drawString("No map yet!", getWidth() / 2 - 50, getHeight() / 2);
		} else {
			updatePrefferedSize();
			drawMap(g);
		}
	}
	
	private void drawMap(Graphics g) {
		drawRoads(g);
		//drawVehicles(g);
		//drawJunctions(g);
	}
	
	private void drawRoads(Graphics g) {
		for (int i = 0; i < _map.getRoads().size(); i++) {
			
			Road r = _map.getRoads().get(i);
			// the road goes from (x1,y1) to (x2,y2)
			int x1 = 50;
			int y =(i+1)*50;
			int x2 = getWidth()-100;
			int cont = 0;

			// choose a color for the arrow depending on the traffic light of the road
			Color arrowColor = _RED_LIGHT_COLOR;
			int idx = r.getDest().getGreenLightIndex();
			if (idx != -1 && r.equals(r.getDest().getInRoads().get(idx))) {
				arrowColor = _GREEN_LIGHT_COLOR;
			}

			// choose a color for the road depending on the total contamination, the darker
			// the
			// more contaminated (wrt its co2 limit)
			int roadColorValue = 200
					- (int) (200.0 * Math.min(1.0, (double) r.getTotalCO2() / (1.0 + (double) r.getContLimit())));
			Color roadColor = new Color(roadColorValue, roadColorValue, roadColorValue);

			// draw line from (x1,y1) to (x2,y2) with arrow of color arrowColor and line of
			// color roadColor. The size of the arrow is 15px length and 5 px width
			
			weather = drawWeather(r.getWeather());
			cont = (int) Math.floor(Math.min((double) r.getTotalCO2()/(1.0 + (double) r.getContLimit()),1.0) / 0.19);
					
			g.setColor(Color.black);
			g.drawLine(x1, y, x2, y);
			g.setColor(_JUNCTION_COLOR);
			g.fillOval(x1 - _JRADIUS, y - 4, _JRADIUS, _JRADIUS);
			g.setColor(arrowColor);
			g.fillOval(x2, y -4, _JRADIUS, _JRADIUS);
			g.setColor(Color.black);
			g.drawString(r.toString(), 20, y + 4);
			g.drawImage(weather, x2 + 20, y - 16, 32, 32, this);
			g.setColor(Color.ORANGE);
			g.drawString(r.getSrc().toString(), x1 - _JRADIUS, y - 10);
			g.drawString(r.getDest().toString(), x2, y - 10);
			g.drawImage(drawContClass(cont), x2 + 60, y - 16, 32, 32, this);
			drawVehicles(g, r.getVehicles(), y);
		}
	}
	
	private Image drawWeather(Weather weather) {
		Image i = null;
		if (weather == Weather.SUNNY) {
			try {
				return ImageIO.read(new File("resources/icons/sun.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (weather == Weather.CLOUDY) {
			try {
				return ImageIO.read(new File("resources/icons/cloud.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (weather == Weather.RAINY) {
			try {
				return ImageIO.read(new File("resources/icons/rain.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (weather == Weather.WINDY) {
			try {
				return ImageIO.read(new File("resources/icons/wind.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else if (weather == Weather.STORM) {
			try {
				return ImageIO.read(new File("resources/icons/storm.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return i;
	}
	
	private Image drawContClass(int c) {
		Image i = null;
		
		try {
			return ImageIO.read(new File("resources/icons/cont_" + c + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}
	
	private void drawVehicles(Graphics g, List<Vehicle> vehicles, int y) {
		int i = 0;
		for (Vehicle v : vehicles) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				Road r = v.getRoad();
	
				
				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relatively to the length of the road, and
				// the location on the vehicle.
				int x1 = 50;
				int x2 = getWidth()-100;
				int y2 = r.getDest().getY();
				
				int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength()));

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				g.drawImage(_car, x, y - 12, 16, 16, this);
				i++;
			}
		}
	}
	
	private void drawVehicles(Graphics g) {
		int i = 0;
		for (Vehicle v : _map.getVehicles()) {
			if (v.getStatus() != VehicleStatus.ARRIVED) {

				Road r = v.getRoad();
	
				
				// The calculation below compute the coordinate (vX,vY) of the vehicle on the
				// corresponding road. It is calculated relatively to the length of the road, and
				// the location on the vehicle.
				int x1 = 50;
				int x2 = getWidth()-100;
				int y =(i+1)*50;
				int y2 = r.getDest().getY();
				
				int x = x1 + (int) ((x2 - x1) * ((double) v.getLocation() / (double) r.getLength()));

				// Choose a color for the vehcile's label and background, depending on its
				// contamination class
				g.drawImage(_car, x, y - 12, 16, 16, this);
				i++;
			}
		}
	}
	
	private void updatePrefferedSize() {
		int maxW = 200;
		int maxH = 200;
		for (Junction j : _map.getJunctions()) {
			maxW = Math.max(maxW, j.getX());
			maxH = Math.max(maxH, j.getY());
		}
		maxW += 20;
		maxH += 20;
		if (maxW > getWidth() || maxH > getHeight()) {
			setPreferredSize(new Dimension(maxW, maxH));
			setSize(new Dimension(maxW, maxH));
		}
	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	public void update(RoadMap map) {
		SwingUtilities.invokeLater(() -> {
			_map = map;
			repaint();
		});
	}

	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
		
	}

	@Override
	public void onError(String err) {
		
	}
	
	
}
