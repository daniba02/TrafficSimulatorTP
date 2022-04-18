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
import simulator.model.Vehicle;

public class ChangeCO2ClassDialog extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int _status;
	private JComboBox<Vehicle> _vehicles;
	private DefaultComboBoxModel<Vehicle> _vehiclesModel;
	
	private JComboBox<Integer> numbers;
	private DefaultComboBoxModel<Integer> numbersModel;
	private Controller ctrl;
	
	public ChangeCO2ClassDialog(Frame parent, Controller ctrl) {
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
		
		JLabel text1 = new JLabel("Vehicles: ");
		JLabel text2 = new JLabel("CO2 Class: ");
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
		
		_vehiclesModel = new DefaultComboBoxModel<>();
		_vehicles = new JComboBox<>(_vehiclesModel);
	

		viewsPanel.add(_vehicles);
		
		viewsPanel.add(text2);
		
		numbersModel = new DefaultComboBoxModel<>();
		numbers = new JComboBox<>(numbersModel);
		
		viewsPanel.add(numbers);
		
		viewsPanel.add(text3);
		viewsPanel.add(numTicks);
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				ChangeCO2ClassDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_vehiclesModel.getSelectedItem() != null || numbersModel != null) {
					_status = 1;
					if(ControlPanel.getTicks()!=0) {
						List<Pair<String, Integer>> list = new ArrayList<>();
						Vehicle v = (Vehicle) _vehiclesModel.getSelectedItem();
						String vehicle = v.toString();
						Integer in = (Integer) numbersModel.getSelectedItem();
						Integer t = (Integer) numTicks.getValue();
						t = t + ControlPanel.getTicks();
						list.add(new Pair<String, Integer>(vehicle, in));
						ctrl.addEvent(new NewSetContClassEvent(t, list));
						//_vehiclesModel.getSelectedItem()
					}
					
					
					ChangeCO2ClassDialog.this.setVisible(false);
				}
			}
		});
		buttonsPanel.add(okButton);

		setBounds(400,400,650,150);
		//setPreferredSize(new Dimension(500, 200));
		pack();
		setResizable(false);
		setVisible(false);
	}
	
	public void open(List<Vehicle> veh, List<Integer> i) {

		_vehiclesModel.removeAllElements();
		for (Vehicle v : veh)
			_vehiclesModel.addElement(v);
		
		numbersModel.removeAllElements();
		for (Integer v : i)
			numbersModel.addElement(v);

		setVisible(true);
	}
}
