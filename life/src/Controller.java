import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller implements java.awt.event.ActionListener,
		ChangeListener {
	private final Model model;
	private boolean isRunning;
	private int runSpeed;

	public Controller(Model m) {
		model = m;
		isRunning = false;
		runSpeed = 1;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		// the button pressed
		final JButton sent = (JButton) event.getSource();

		// the button's label
		final String label = sent.getText();

		if (label.equals("Clear")) {
			initModel();
		} else if (label.equals("Step")) {
			model.step();
		} else if (label.equals("Run")) {
			isRunning = true;
			model.setRunning();
			while (isRunning) {
				
			}
		} else if (label.equals("Pause")) {
			isRunning = false;

		} else if (label.equals("Quit")) {
			if (isRunning == false) {
				System.exit(0);
			}
		}
	}

	@Override
	public void stateChanged(final ChangeEvent event) {
		final JSlider source = (JSlider)event.getSource();
		// The listener is called when the slider moves
		// it only changes the label at the end of the movement
		if (!source.getValueIsAdjusting()) {
			runSpeed = (int)source.getValue();
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
