public class Model extends java.util.Observable {
	private final int rows;
	private Cell[][] cells;
	private Cell[][] newCells;
	private Model model;
	private int turnCount;

	protected Model(int therows) {
		rows = therows;
		cells = new Cell[rows][rows];
		model = this;
	}

	protected final void initDefault() {
		turnCount = 0;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				cells[i][j] = new Cell();
			}
		}
		updateView();
	}

	protected final void setCellColor(Colour color, int r, int c) {
		cells[r][c].setColor(color);
		updateView();
	}
	
	protected final void setRunning() {
		
	}

	protected final void step() {
		newCells = new Cell[rows][rows];

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < rows; j++) {
				int red = 0;
				int green = 0;

				// count the number of red cells and green cells in the
				// eight neighbouring cells
				for (int x = -1; x <= 1; x++) {
					for (int y = -1; y <= 1; y++) {
						
						// checks the color of the neighbouring cell
						if (!(x == 0 && y == 0)) {
							int r = i + x;
							int c = j + y;

							// wrapping for corner cases and border cases
							if (r == -1) 	{r = rows - 1;}
							if (c == -1) 	{c = rows - 1;}
							if (r == rows) 	{r = 0;}
							if (c == rows) 	{c = 0;}

							Colour color = cells[r][c].getColor();
							//System.out.println("Cell " + r + " " + c + color);
							if (color == Colour.RED) {
								red++;
							} else if (color == Colour.GREEN) {
								green++;
							}
						}
					}
				}
				newCells[i][j] = getNewInnerCell(cells[i][j], red, green);

				/*System.out.println("Cell " + i + " " + j + "\n"
						+ "Original Colour:" + cells[i][j].getColor() + "\n"
						+ "Red " + red + "\n" + "Green " + green + "\n"
						+ "New Color " + newCells[i][j].getColor()
						+ "\n_______________");
				*/
			}
		}

		cells = newCells;
		turnCount++;
		updateView();
	}

	// determines the new cell color by life rules
	private Cell getNewInnerCell(Cell originalCell, int red, int green) {
		
		int aliveNeighbours = red + green;
		Cell newInnerCell = new Cell();

		/*
		 * life rules
		 * 
		 * 1. Any live cell with fewer than two live neighbours dies
		 * 
		 * 2. Any live cell with two or more neighbours lives on to the next
		 * generation
		 * 
		 * 3. Any live cell with more than three live neighbours dies
		 * 
		 * 4. Any dead cell with exactly three live neighbours becomes a live
		 * cell
		 */

		if (originalCell.isAlive()) {
			if (aliveNeighbours < 2) {
				newInnerCell.setColor(Colour.GRAY);
			} else if (aliveNeighbours == 2 || aliveNeighbours == 3) {
				newInnerCell.setColor(originalCell.getColor());
			} else if (aliveNeighbours > 3) {
				newInnerCell.setColor(Colour.GRAY);
			}

		} else if (aliveNeighbours == 3) {
			/*
			 * When a new cell is born, it inherits the majority colour of its
			 * surrounding cells when there are equal number of red and green
			 * surrounding cells, the new cell inherits red colour
			 */
			if (red >= green) {
				newInnerCell.setColor(Colour.RED);
			} else {
				newInnerCell.setColor(Colour.GREEN);
			}
		}
		return newInnerCell;
	}

	protected int getTurnCount() {
		return turnCount;
	}

	protected Cell[][] getCells() {
		return cells;
	}

	private void updateView() {
		setChanged();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				notifyObservers(model);
			}
		});
	}

}
