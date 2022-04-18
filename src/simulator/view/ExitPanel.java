package simulator.view;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ExitPanel extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExitPanel(){
		super();
		initGUI();
	}
	
	private void initGUI() {
		
		JDialog d = new JDialog();
		JPanel panel = new JPanel(new BorderLayout());
		//setContentPane(panel);
		JPanel supPanel = new JPanel();
		JPanel infPanel = new JPanel();
		
		JLabel text = new JLabel("Are you sure you want to leave??");
		//text.setAlignmentX(CENTER_ALIGNMENT);
		supPanel.add(text);
		
		JButton noButton = new JButton("No");
		noButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ExitPanel.this.setVisible(false);
			}
		});
		infPanel.add(noButton);

		JButton yesButton = new JButton("Yes");
		yesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				ExitPanel.this.setVisible(false);
			}
		});
		infPanel.add(yesButton);
	
		panel.add(supPanel, BorderLayout.NORTH);
		panel.add(infPanel, BorderLayout.SOUTH);
		
		add(panel);
		setBounds(600, 400, 500, 100);
		setVisible(true);
	}
}
