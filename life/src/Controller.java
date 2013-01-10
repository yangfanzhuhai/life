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

	@Override
	public void actionPerformed(ActionEvent event) {
		// the button pressed
		final JButton sent = (JButton) event.getSource();

		// the button's label
		final String label = sent.getText();

		/*
		 * range R = 1..10
		 * 
		 * LIFE = LIFE[1], LIFE[s:R] = (slide[n:R] -> LIFE[n] | run ->
		 * RUNNING[s] | clear -> LIFE | step -> LIFE[s] | quit -> STOP),
		 * 
		 * RUNNING[s:R] = (slide[n:R] -> RUNNING[n] | pause -> LIFE[s]).
		 */

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
				startNewTimer(runSpeed);
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

	@Override
	public void stateChanged(final ChangeEvent event) {
		final JSlider source = (JSlider) event.getSource();
		// The listener is called when the slider moves
		// it only changes the label at the end of the movement
		if (!source.getValueIsAdjusting()) {
			runSpeed = (int) source.getValue();
			startNewTimer(runSpeed);
		}
	}
	
	private void startNewTimer(int speed){
		timer = new Timer(2000 / speed, runTaskPerformer);
		timer.start();
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
