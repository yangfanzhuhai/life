
public class Cell {
	private Colour color;

	public Cell() {
		color = Colour.GRAY;
	}

	public void setColor(Colour c) {
		color = c;
	}

	public Colour getColor() {
		return color;
	}

	public boolean isAlive() {
		return color != Colour.GRAY;
	}
}
