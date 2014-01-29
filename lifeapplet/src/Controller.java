import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Controller {
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
				stepModel();
			}
		};
		timer = new Timer(2000 / runSpeed, runTaskPerformer);
	}
	
	// step the simulation through one application of the rules and increment
	// the turn counter
	private void stepModel() {
		model.step();
	}

	// called when the runSpeed is updated via user interaction
	public void runSpeedChanged(int newSpeed) {
		runSpeed = newSpeed;
		timer.setDelay(2000 / runSpeed);
	}

	// change cell[r][c] to color
	public void setCellColor(Colour color, int r, int c) {
		if (isRunning == false) {
			model.setCellColor(color, r, c);
		}
	}

	// read the colour of the cell at coord (x,y)
	public Colour readCell(int x, int y) 
			throws ArrayIndexOutOfBoundsException {
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
		if (isRunning == false) {
			model.initDefault();
		}
	}

	// when the step() function is called by the user
	public void step() {
		if (isRunning == false) {
			stepModel();
		}
	}
	
	// perform step() continuously at the selected time interval
	public void run() {
		if (isRunning == false) {
			isRunning = true;
			timer.restart();
		}

	}

	// pause running
	public void pause() {
		if (isRunning == true) {
			isRunning = false;
			timer.stop();
		}
	}

}
