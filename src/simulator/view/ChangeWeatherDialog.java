package simulator.view;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import simulator.control.Controller;
import simulator.misc.Pair;
import simulator.model.NewSetContClassEvent;
import simulator.model.Road;
import simulator.model.SetWeatherEvent;
import simulator.model.Vehicle;
import simulator.model.Weather;

public class ChangeWeatherDialog extends JDialog{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _status;
	private JComboBox<Road> _roads;
	private DefaultComboBoxModel<Road> _roadsModel;
	
	private JComboBox<Weather> weather;
	private DefaultComboBoxModel<Weather> weatherModel;
	private Controller ctrl;
	
	public ChangeWeatherDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		this.ctrl = ctrl;
		initGUI();
	}
	
	public void initGUI() {
		
		_status = 0;
		setTitle("Change CO2 class");
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		setContentPane(panel);
		
		JLabel text1 = new JLabel("Roads: ");
		JLabel text2 = new JLabel("Weather: ");
		JLabel text3 = new JLabel("Ticks: ");
		
		JSpinner numTicks = new JSpinner(new SpinnerNumberModel(1, 1, 1000, 1));
		
		JLabel text0 = new JLabel("Schedule an event to change the CO2 class of a vehicle after"
				+ "a given number of simulation ticks from now");
		text0.setAlignmentX(CENTER_ALIGNMENT);
		
		panel.add(text0);
		
		panel.add(Box.createRigidArea(new Dimension(0, 20)));

		JPanel viewsPanel = new JPanel();
		viewsPanel.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(viewsPanel);
		
		panel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);
		panel.add(buttonsPanel);
		
		viewsPanel.add(text1);
		
		_roadsModel = new DefaultComboBoxModel<>();
		_roads = new JComboBox<>(_roadsModel);

		viewsPanel.add(_roads);
		
		viewsPanel.add(text2);
		
		weatherModel = new DefaultComboBoxModel<>();
		weather = new JComboBox<>(weatherModel);
		
		viewsPanel.add(weather);
		
		viewsPanel.add(text3);
		viewsPanel.add(numTicks);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeWeatherDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_roadsModel.getSelectedItem() != null || _roadsModel != null) {
					if(ControlPanel.getTicks()!=0) {
						List<Pair<String, Weather>> list = new ArrayList<>();
						Road r = (Road) _roadsModel.getSelectedItem();
						String road = r.toString();
						Weather w = (Weather) weatherModel.getSelectedItem();
						Integer t = (Integer) numTicks.getValue();
						t = t + ControlPanel.getTicks();
						list.add(new Pair<String, Weather>(road, w));
						ctrl.addEvent(new SetWeatherEvent(t, list));
					}
					ChangeWeatherDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);

		setBounds(400,400,650,150);
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public void open(List<Road> veh, List<Weather> i) {

		// update the comboxBox model -- if you always use the same no
		// need to update it, you can initialize it in the constructor.
		//
		_roadsModel.removeAllElements();
		for (Road v : veh)
			_roadsModel.addElement(v);
		
		weatherModel.removeAllElements();
		for (Weather v : i)
			weatherModel.addElement(v);

		// You can chenge this to place the dialog in the middle of the parent window.
		// It can be done using uing getParent().getWidth, this.getWidth(),
		// getParent().getHeight, and this.getHeight(), etc.
		//
		//setLocation(getParent().getLocation().x + 10, getParent().getLocation().y + 10);

		setVisible(true);
		//return _status;
	}
	
	Weather getWeather() {
		return(Weather) weatherModel.getSelectedItem();
	}
	
	Road getRoad() {
		return(Road) _roadsModel.getSelectedItem();
	}
}
