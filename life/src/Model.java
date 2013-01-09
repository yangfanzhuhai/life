public class Model extends java.util.Observable {
	private final int rows;
	private Cell[][] cells;
	private Model model;

	protected Model(int therows) {
		rows = therows;
		cells = new Cell[rows][rows];
		model = this;
	}

	protected final void initDefault() {
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
	
	protected final void setRedCell(int r, int c) {
		cells[r][c].setRed();
		setChanged();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				notifyObservers(model);
			}
		});
	}
	
	protected final void setGreenCell(int r, int c) {
		cells[r][c].setGreen();
		setChanged();
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				notifyObservers(model);
			}
		});
	}

	protected Cell[][] getCells() {
		return cells;
	}
}
