package life;

import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

import javax.swing.*;

import life.Life.Colour;

public class View implements java.util.Observer {
	private JLabel countLabel;
	private JSlider slide;

	private JButton clearButton;
	private JButton stepButton;
	private JButton runButton;

	private JButton buttons[][];
	private Controller controller;
	private final JApplet applet;
	private final Container contentPane;

	View(JApplet applet) {
		this.applet = applet;
		contentPane = applet.getContentPane();
	}

	public void createGUI() {
		// create the GUI components

		// create a countLabel
		countLabel = new JLabel("turns: 0", SwingConstants.CENTER);

		// create and initialise slider
		initSlide();

		// create four function buttons and add them into buttonPane
		JPanel buttonPane = initButtonPane();

		// create cell panel
		JPanel cellPane = initCellPane();

		// add and arrange the components

		// add the countLabel to the JApplet
		contentPane.add(countLabel, BorderLayout.NORTH);

		// add the slider to the JApplet
		contentPane.add(slide, BorderLayout.EAST);

		// add the cell panel to the JApplet
		contentPane.add(cellPane, BorderLayout.CENTER);

		// add the buttonPane to the JApplet
		contentPane.add(buttonPane, BorderLayout.SOUTH);

	}

	private final void initSlide() {
		// create a slider with range from 1 to 10
		// and initial value 1
		slide = new JSlider(JSlider.VERTICAL, 1, 10, 1);

		// define the scale to be shown on the slide
		slide.setMajorTickSpacing(1);
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

		// create button panel for the four function buttons
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));

		// Lay out the buttons from left to right
		buttonPane.add(clearButton);
		buttonPane.add(stepButton);
		buttonPane.add(runButton);

		return buttonPane;
	}

	protected JButton newGridButton(final int r, final int c) {
		JButton button = new JButton();
		button.setBackground(Color.GRAY);

		/*
		 * for display in Mac OS X button.setOpaque(true);
		 * button.setBorderPainted(false);
		 */

		// add MouseListener to button and send the
		// color change request to controller
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (SwingUtilities.isLeftMouseButton(event)) {
					//System.out.println("row " + r + " column " + c);
					controller.setCellColor(Colour.RED, r, c);
				} else if (SwingUtilities.isRightMouseButton(event)) {
					controller.setCellColor(Colour.GREEN, r, c);
				} else if (SwingUtilities.isMiddleMouseButton(event)) {
					controller.setCellColor(Colour.GREY, r, c);
				}
			}
		});
		return button;
	}

	private JPanel initCellPane() {

		// create the buttons for cells
		// no individual buttons added for now
		buttons = new JButton[Life.ROWS][Life.ROWS];

		// create a new panel and init layout
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(Life.ROWS, Life.ROWS));

		// update the individual buttons to represent individual cells
		// and add the buttons to the panel
		for (int i = Life.ROWS - 1; i >= 0; i--) {
			for (int j = 0; j < Life.ROWS; j++) {
				buttons[i][j] = newGridButton(i, j);
				panel.add(buttons[i][j]);
			}
		}
		// add the panel to the frame
		contentPane.add(panel, BorderLayout.CENTER);

		return panel;
	}

	private void updateTurnCount(int turnCount) {
		countLabel.setText("turns: " + Integer.toString(turnCount));
	}

	protected void updateCellPanel(Cell[][] cells) {
		for (int i = 0; i < Life.ROWS; i++) {
			for (int j = 0; j < Life.ROWS; j++) {
				Colour newColour = cells[i][j].getColor(); 
				switch (newColour) {
				case GREY:
					buttons[i][j].setBackground(Color.GRAY);
					break;
				case GREEN:
					buttons[i][j].setBackground(Color.GREEN);
					break;
				case RED:
					buttons[i][j].setBackground(Color.RED);
					break;
				}
			}
		}

	}

	@Override
	public void update(Observable object, Object arg) {
		Model model = (Model) object;
		updateTurnCount(model.getTurnCount());
		updateCellPanel((Cell[][]) arg);
		applet.validate();
	}

	public void addController(Controller controller) {
		this.controller = controller;

		// add controller as listeners to the function buttons
		clearButton.addActionListener(controller);
		stepButton.addActionListener(controller);
		runButton.addActionListener(controller);

		// add controller as ChangeListener to slide
		slide.addChangeListener(controller);
	}

}
