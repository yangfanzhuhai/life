package life;
import javax.swing.JApplet;

public class Life extends JApplet {

	public static final int ROWS = 30;
	Controller controller;
	View view;

	// enum for managing the basic three colours of the cells
	// RED and GREEN cells are alive, GREY cells are dead
	public enum Colour {
		RED, GREEN, GREY;
	}

  public Life() {
		// creates the MVC
		Model model = new Model();
		view = new View(this);
		controller = new Controller(model);

		// tells model about view
		// model will notify view of changes
		model.addObserver(view);

		// tells view about controller,
		// view will notify controller of user actions
		view.addController(controller);
  }
  
	// called by the html page to initilise the applet
	public void init() {

		// Execute a job on the event-dispatching thread:
		// creating this applet's GUI.
		try {
			javax.swing.SwingUtilities.invokeAndWait(new Runnable() {
				public void run() {
					// initialises the View
					view.createGUI();
				}
			});
		} catch (Exception e) {
			System.err.println("createGUI didn't successfully complete");
		}

	} // end of init

	/*
	 * The autotest will assume the following interfaces on the class life.Life
	 * You are free to implement these methods in any way you wish Note: we are
	 * assuming a standard coordinate system, that is with (0,0) referring to
	 * the bottom left cell, x being col no. and y being row no.
	 */

	// read the colour of the cell at coord (x,y)
	public Colour readCell(int x, int y) {
		return controller.readCell(y, x);
	}

	// read the turn count
	public int readTurn() {
		return controller.readTurn();
	}

	// kill the cell at coord (x,y)
	public void kill(int x, int y) {
		controller.kill(y, x);
	}

	// create a live sell with the specified colour at coord (x,y)
	public void resurrect(int x, int y, Colour c) {
		controller.resurrect(y, x, c);
	}

	// clear the board and reset the turn count to 0
	public void clear() {
		controller.clear();
	}

	// step the simulation through one application of the rules and increment
	// the turn counter
	public void step() {
		controller.step();
	}

}
