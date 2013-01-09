import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class Controller implements java.awt.event.ActionListener {
	private final Model model;

	public Controller(Model m) {
		model = m;
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
			
		} else if (label.equals("Pause")) {
			
		} else if (label.equals("Quit")) {
			System.exit(0);
		}
	}

	public void initModel() {
		model.initDefault();
	}

	public void setCellColor(Colour color, int r, int c) {
		model.setCellColor(color, r, c);
	}
}
