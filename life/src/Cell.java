
public class Cell {
	private Colour color;

	public Cell() {
		color = Colour.GRAY;
	}

	public void setRed() {
		color = Colour.RED;
	}

	public void setGreen() {
		color = Colour.GREEN;
	}

	public Colour getColor() {
		return color;
	}

	public boolean isAlive() {
		return color != Colour.GRAY;
	}
}
