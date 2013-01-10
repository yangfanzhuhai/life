import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

import javax.swing.*;

public class View implements java.util.Observer {
	private final int rows;
	private final JLabel countLabel;
	private JSlider slide;

	private JButton clearButton;
	private JButton stepButton;
	private JButton runButton;
	private JButton quitButton;

	private final JButton buttons[][];
	private JPanel panel;
	private final JFrame frame;
	private Controller controller;

	View(final int therows) {
		rows = therows;
		// create the GUI components

		// create a countLabel
		countLabel = new JLabel("0", SwingConstants.CENTER);

		// create and initialise slider
		initSlide();

		// create four function buttons and add them into buttonPane
		JPanel buttonPane = initButtonPane();

		// create the buttons for cells
		// no individual buttons added for now
		buttons = new JButton[rows][rows];

		// create a new panel and init layout
		panel = new JPanel();
		panel.setLayout(new GridLayout(rows, rows));

		// create a frame and init size
		frame = new JFrame();
		frame.setSize(rows * 20, rows * 20 + 50);

		// add and arrange the components

		// add the countLabel to the frame
		frame.add(countLabel, BorderLayout.NORTH);

		// add the slider to the frame
		frame.add(slide, BorderLayout.EAST);

		// add the panel to the frame
		frame.add(panel, BorderLayout.CENTER);

		// add the buttonPane to the frame
		frame.add(buttonPane, BorderLayout.SOUTH);

		// The program exits when the window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private final void initSlide() {
		// create a slider with range from 1 to 10
		// and initial value 1
		slide = new JSlider(JSlider.VERTICAL, 1, 10, 1);

		// define the scale to be shown on the slide
		slide.setMajorTickSpacing(2);
		slide.setMinorTickSpacing(1);
		slide.setPaintTicks(true);
		slide.setPaintLabels(true);

		// create a border around the slider and its scale.
		slide.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 10));
	}

	private final JPanel initButtonPane() {
		// create four function buttons
		clearButton = new JButton("Clear");
		stepButton = new JButton("Step");
		runButton = new JButton("Run");
		quitButton = new JButton("Quit");

		// create button panel for the four function buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));

		// Lay out the buttons from left to right
		buttonPane.add(clearButton);
		buttonPane.add(stepButton);
		buttonPane.add(runButton);
		buttonPane.add(quitButton);

		return buttonPane;
	}

	private void updateTurnCount(int turnCount) {
		countLabel.setText(Integer.toString(turnCount));
	}

	protected void updatePanel(Cell[][] cells) {
		// remove the old panel
		frame.remove(panel);

		// create a new panel and init layout
		panel = new JPanel();
		panel.setLayout(new GridLayout(rows, rows));

		// update the individual buttons to represent individual cells
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				Colour color = cells[i][j].getColor();
				buttons[i][j] = gridButton(color, i, j);
			}
		}

		// add the buttons to the panel
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++)
				panel.add(buttons[i][j]);
		}

		// add the panel to the frame
		frame.add(panel, BorderLayout.CENTER);
	}

	protected JButton gridButton(final Colour color, final int r, final int c) {
		JButton button = new JButton();

		switch (color) {
		case GRAY:
			button.setBackground(Color.GRAY);
			break;
		case GREEN:
			button.setBackground(Color.GREEN);
			break;
		case RED:
			button.setBackground(Color.RED);
			break;
		}

		// button.setOpaque(true);
		// button.setBorderPainted(false);

		// add MouseListener to button and send the
		// color change request to controller
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (SwingUtilities.isLeftMouseButton(event)) {
					controller.setCellColor(Colour.RED, r, c);
				} else if (SwingUtilities.isRightMouseButton(event)) {
					controller.setCellColor(Colour.GREEN, r, c);
				} else if (SwingUtilities.isMiddleMouseButton(event)) {
					controller.setCellColor(Colour.GRAY, r, c);
				}
			}
		});
		return button;
	}

	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) arg;
		updateTurnCount(model.getTurnCount());
		updatePanel(model.getCells());
		frame.validate();
	}

	public void addController(Controller controller) {
		this.controller = controller;

		// add controller as listeners to the function buttons
		clearButton.addActionListener(controller);
		stepButton.addActionListener(controller);
		runButton.addActionListener(controller);
		quitButton.addActionListener(controller);

		// add controller as ChangeListener to slide
		slide.addChangeListener(controller);
	}
}
