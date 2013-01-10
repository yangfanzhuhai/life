import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class Controller implements java.awt.event.ActionListener {
	private final Model model;
	private boolean isRunning;

	public Controller(Model m) {
		model = m;
		isRunning = false;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// the button pressed
		final JButton sent = (JButton)event.getSource();
		
		// the button's label
		final String label = sent.getText();
		
		if (label.equals("Clear")) {
			initModel();
		} else if (label.equals("Step")) {
			model.step();
		} else if (label.equals("Run")) {
			isRunning = true;
			
		} else if (label.equals("Pause")) {
			isRunning = false;
			
		} else if (label.equals("Quit")) {
			System.exit(0);
		}
	}

	public void initModel() {
		model.initDefault();
	}

	public void setCellColor(Colour color, int r, int c) {
		if (isRunning == false) {
			model.setCellColor(color, r, c);
		}
	}
}
