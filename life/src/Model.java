public class Model extends java.util.Observable {
	private final int rows;
	private Cell[][] cells;

	public Model(int therows) {
		rows = therows;
		cells = new Cell[rows][rows];
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
		notifyObservers(this);
	}

	public Cell[][] getCells() {
		return cells;
	}
}
