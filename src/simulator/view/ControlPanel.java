package simulator.view;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import extra.jtable.EventEx;
import simulator.control.Controller;
import simulator.model.Event;
import simulator.model.Road;
import simulator.model.RoadMap;
import simulator.model.TrafficSimObserver;

public class ControlPanel extends JPanel implements TrafficSimObserver{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Controller ctrl;
	private EventsTableModel _model;
	private RoadsTableModel _roadsModel;
	private JTable _eventsTable;
	private List<Event> _events;
	private List<Road> _roads;
	private RoadMap _roadMap;
	private static int totalTicks = 0;
	private boolean _stopped;
	
	private JButton carpeta;
	private JButton hoja;
	private JButton weather;
	private JButton run;
	private JButton stop;
	private JButton exit;
	private JSpinner ticks;
	
	public ControlPanel(Controller ctrl) {
		
		this.ctrl = ctrl;
		ctrl.addObserver(this);
		initGUI();
	}
	
	public void initGUI () {
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		carpeta = createButton("resources/icons/open.png");
		carpeta.setToolTipText("Open examples");
		hoja = createButton("resources/icons/co2class.png");
		hoja.setToolTipText("Change CO2 class of a vehicle");
		weather = createButton("resources/icons/weather.png");
		weather.setToolTipText("Change weather of a road");
		run = createButton("resources/icons/run.png");
		run.setToolTipText("Run the simulator");
		stop = createButton("resources/icons/stop.png");
		stop.setToolTipText("Stop the simulator");
		exit = createButton("resources/icons/exit.png");
		exit.setToolTipText("Exit the simulator");
		ticks = new JSpinner(new SpinnerNumberModel(1, 1, 10000, 1));
		ticks.setToolTipText("Simulation tick to run: 1 - 10000");
		
		_model = new EventsTableModel(ctrl);
		_roadsModel = new RoadsTableModel(ctrl);
		_eventsTable = new JTable(_model);

		// the actual events list
		_events = new ArrayList<Event>();
		_roads = new ArrayList<Road>();
		
		carpeta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				//JFrame frame = new JFrame("Open");
				JFileChooser file = new JFileChooser("C:\\Users\\danie\\eclipse-workspace\\TrafficSimulatorTP.zip_expanded\\TrafficSimulatorTP\\resources\\examples");
				    
				int status = file.showOpenDialog(null);
				
				if (status == JFileChooser.APPROVE_OPTION) {
					
					ctrl.reset();
					InputStream input;
					try {
						input = new FileInputStream(file.getSelectedFile());
						//ctrl.loadEvents(input);
						//_model.setEventsList(ctrl.getSim().getEvents());
						//_model.update();
						//_roadsModel.setRoadsList(ctrl.getSim().getRoads());
						//_roadsModel.update();
						ctrl.reset();
						ctrl.loadEvents(input);
						System.out.println("Hola");
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						System.out.println("Adiosda");
					}
					
					
				} else if (status == JFileChooser.CANCEL_OPTION) {
				     System.out.println("canceled");

				}
			}	
		});
		
		hoja.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0){
				
				new ChangeCO2Class(ctrl);
			}	
		});
		
		weather.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ChangeWeather(ctrl);
			}	
		});
		
		run.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0){
				_stopped = false;
				Integer time = (Integer) ticks.getValue();
				//Integer time = 100;
				enableToolBar(false);
				run_sim(time);
				totalTicks += time;
			}	
		});
		
		stop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0){
				stop();
			}	
		});
		
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				new ExitPanel();
			}	
		});
		
		
		JLabel tickstext =  new JLabel(" Ticks: ");
		
		JTextField textfield = new JTextField(10);
		textfield.setEditable(true);
		
		JPanel leftPanel = createLateralPanel ();
		leftPanel.add(carpeta);
		mainPanel.add(Box.createHorizontalGlue());
		leftPanel.add(hoja);
		leftPanel.add(weather);
		mainPanel.add(Box.createHorizontalGlue());
		leftPanel.add(run);
		leftPanel.add(stop);
		leftPanel.add(tickstext);
		leftPanel.add(ticks);
		
		JPanel rightPanel = createLateralPanel ();
		rightPanel.add(exit);
		
		mainPanel.add(leftPanel,BorderLayout.WEST);
		mainPanel.add(rightPanel,BorderLayout.EAST);
		mainPanel.setPreferredSize(new Dimension(1000, 50));
		
		this.add(mainPanel);
		//this.setSize(new Dimension(2000, 2000));
		this.setVisible(true);
	}
	
	private JButton createButton(String dir) {
		
		JButton button = new JButton();
		button.setBackground(Color.white);
		button.setIcon(new ImageIcon(dir));
		button.setPreferredSize(new Dimension(50, 50));
		return button; 
	}
	
	private JPanel createLateralPanel (){
		JPanel sidePanel = new JPanel();
		sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.X_AXIS));
		//sidePanel . setBackground(color); //especifica el color del panel
		//sidePanel . add(this . createButton(caption));
		return sidePanel ;
		}
	
	private void run_sim(int n) {
		if (n > 0 && !_stopped) {
			try {
				ctrl.run(1);
			} catch (Exception e) {
				// TODO show error message
				_stopped = true;
				return;
			}
			SwingUtilities.invokeLater(new Runnable() {
			
				@Override
				public void run() {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					run_sim(n - 1);
				}
			});
		} else {
			enableToolBar(true);
			_stopped = true;
		}
	}
	
	private void stop() {
		_stopped = true;
	}
	
	private void enableToolBar(boolean bool) {
		
		this.carpeta.setEnabled(bool);
		this.hoja.setEnabled(bool);
		this.weather.setEnabled(bool);
		this.run.setEnabled(bool);
		this.exit.setEnabled(bool);
		this.ticks.setEnabled(bool);
		
	}
	
	public static int getTicks() {
		return totalTicks;
	}
	
	public static void resetTicks() {
		totalTicks = 0;
	}

	public void update(RoadMap map) {
		this._roadMap = map;
	}


	@Override
	public void onAdvanceStart(RoadMap map, List<Event> events, int time) {
		
	}

	@Override
	public void onAdvanceEnd(RoadMap map, List<Event> events, int time) {
		update(map);
		//totalTicks = time;
		
	}

	@Override
	public void onEventAdded(RoadMap map, List<Event> events, Event e, int time) {
		update(map);
		//totalTicks = time;
		
	}

	@Override
	public void onReset(RoadMap map, List<Event> events, int time) {
		resetTicks();
		update(map);
		//totalTicks = time;
		
	}

	@Override
	public void onRegister(RoadMap map, List<Event> events, int time) {
		update(map);
		//totalTicks = time;
	}

	@Override
	public void onError(String err) {
		
	}
}
