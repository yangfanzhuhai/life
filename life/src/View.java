import java.awt.*;
import java.awt.event.*;
import java.util.Observable;

import javax.swing.*;

public class View implements java.util.Observer {
	private final int rows;
	private final JLabel countLabel;
	private JButton buttons[][];
	private JPanel panel;
	private final JFrame frame;
	private Controller controller;

	View(final int therows) {
		rows = therows;
		// create the GUI components

		// create the countLabel
		countLabel = new JLabel("0", SwingConstants.CENTER);

		// create the buttons
		// no individual buttons added for now
		buttons = new JButton[rows][rows];

		// create a new panel and init layout
		panel = new JPanel();
		panel.setLayout(new GridLayout(rows, rows));

		// create the frame and init size
		frame = new JFrame();
		frame.setSize(rows * 25, rows * 25);

		// add and arrange the components

		// add the countLabel to the frame
		frame.add(countLabel, BorderLayout.NORTH);

		// add the panel to the frame
		frame.add(panel, BorderLayout.CENTER);

		// The program exits when the window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
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

		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent event) {
				if (SwingUtilities.isLeftMouseButton(event)) {
					controller.setRedCell(r, c);
				} else if (SwingUtilities.isRightMouseButton(event)) {
					controller.setGreenCell(r, c);
				}
			}
		});
		return button;
	}

	@Override
	public void update(Observable o, Object arg) {
		Model model = (Model) arg;
		updatePanel(model.getCells());
		frame.validate();
	}

	public void addController(Controller controller) {
		this.controller = controller;
	}
}
