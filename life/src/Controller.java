import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller implements java.awt.event.ActionListener,
		ChangeListener {
	private final Model model;
	private boolean isRunning;
	private int runSpeed;
	private Timer timer;
	private final ActionListener runTaskPerformer;

	public Controller(Model m) {
		model = m;
		isRunning = false;
		runSpeed = 1;
		runTaskPerformer = new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				model.step();
			}
		};
	}
	
	// when a function button is clicked
	@Override
	public void actionPerformed(ActionEvent event) {
		// the button pressed
		final JButton sent = (JButton) event.getSource();

		// the button's label
		final String label = sent.getText();

		if (isRunning == false) {
			if (label.equals("Clear")) {
				initModel();
			} else if (label.equals("Step")) {
				model.step();
			} else if (label.equals("Quit")) {
				System.exit(0);
			} else if (label.equals("Run")) {
				isRunning = true;
				sent.setText("Pause");
				startNewTimer();
			}
		}

		if (isRunning == true) {
			if (label.equals("Pause")) {
				isRunning = false;
				sent.setText("Run");
				timer.stop();
			}
		}
	}

	// when the slider value is adjusted
	@Override
	public void stateChanged(final ChangeEvent event) {
		final JSlider source = (JSlider) event.getSource();
		// The listener is called when the slider moves
		// it only changes the label at the end of the movement
		if (!source.getValueIsAdjusting()) {
			runSpeed = (int) source.getValue();
			if (isRunning) {
				timer.stop();
				startNewTimer();
			}
		}
	}
	
	// start a new timer with the updated runSpeed
	private void startNewTimer(){
		timer = new Timer(2000 / runSpeed, runTaskPerformer);
		timer.start();
	}
	
	// clear button : kills all cells and resets the turn count to 0
	public void initModel() {
		model.initDefault();
	}

	// change cell[r][c] to color
	public void setCellColor(Colour color, int r, int c) {
		if (isRunning == false) {
			model.setCellColor(color, r, c);
		}
	}

}
