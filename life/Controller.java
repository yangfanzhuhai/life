package life;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import life.Life.Colour;

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
				step();
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
				clear();
			} else if (label.equals("Step")) {
				step();
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

	// change cell[r][c] to color
	public void setCellColor(Colour color, int r, int c) {
		if (isRunning == false) {
			model.setCellColor(color, r, c);
		}
	}

	// read the colour of the cell at coord (x,y)
	public Colour readCell(int x, int y) {
		return model.getCellColor(x, y);
	}
	
	// read the turn count
	public int readTurn() {
		return model.getTurnCount();
	}

	// kill the cell at coord (x,y)
	public void kill(int x, int y) {
		setCellColor(Colour.GREY, x, y);
	}

	// create a live sell with the specified colour at coord (x,y)
	public void resurrect(int x, int y, Colour c) {
		setCellColor(c, x, y);
	}

	// clear the board and reset the turn count to 0
	public void clear() {
		model.initDefault();
	}

	// step the simulation through one application of the rules and increment
	// the turn counter
	public void step() {
		model.step();
	}


}
