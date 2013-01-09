
public class Life {

	public static void main(final String[] args) {
		int rows;
		final int therows;
		final Model model;
		final Controller controller;

		// takes in the default grid size, default 30, minimum 4
		rows = 30;
		if (args.length > 0) {
			try {
				rows = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err.println("The first parameter "
						+ "should be a number.");
				System.exit(1);
			}
			if (rows < 4) {
				System.err.println("The minimum row size " + "should be 4.");
				System.exit(2);
			}
		}

		therows = rows;

		// initialises the Model
		model = new Model(therows);

		// initialises the Controller
		controller = new Controller(model);

		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				// initialises the View
				final View view = new View(therows);
				
				// start up a new thread for code that is irrelavent
				// to updating the GUI
				Thread buildMVCthread = new Thread() {
					public void run() {
						// tells model about view
						// model will notify view of changes
						model.addObserver(view);

						// tells view about controller,
						// view will notify controller of user actions
						view.addController(controller);
					}
				};

				buildMVCthread.start();

				// initlises every cell in the model
				controller.initModel();
			}
		});
	}
}
