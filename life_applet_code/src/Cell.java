public class Cell {
	private Colour color;
	private int row;
	private int column;

	public Cell(int x, int y) {
		color = Colour.GREY;
		row = x;
		column = y;
	}

	public void setColor(Colour c) {
		color = c;
	}

	public Colour getColor() {
		return color;
	}

	public boolean isAlive() {
		return color != Colour.GREY;
	}

	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
}
