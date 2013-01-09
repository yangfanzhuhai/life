public class Model extends java.util.Observable {
	private final int rows;
	private Cell[][] cells;
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
		
		//cells[5][6].setGreen();
		//cells[7][6].setRed();
		setChanged();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				notifyObservers(model);
			}
		});
	}
	
	protected final void setCellColor(Colour color, int r, int c) {
		cells[r][c].setColor(color);
		setChanged();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				notifyObservers(model);
			}
		});
	}

	protected int getTurnCount() {
		return turnCount;
	}
	
	protected Cell[][] getCells() {
		return cells;
	}
}
