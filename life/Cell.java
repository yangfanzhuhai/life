package life;

import life.Life.Colour;

public class Cell {
	private Colour color;
	private int x;
	private int y;

	public Cell(int row, int column) {
		color = Colour.GREY;
		x = row;
		y = column;
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
		return x;
	}
	
	public int getColumn() {
		return y;
	}
}
