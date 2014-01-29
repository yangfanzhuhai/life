import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class Tests {
	
	private Life createNewGame() {
		Life game = new Life();
		game.initView();
		return game;
	}
	
	@Test
	public void testTurn() {
		Life game = createNewGame();	
		assertEquals("Turn is not 0 at game start.", 0, game.readTurn());
	}
	
	@Test 
	public void testStep() {
		Life game = createNewGame();
		game.step();
		assertEquals("Turn is not 1 after one step.", 1, game.readTurn());
	}
	
	@Test
	public void testClear() {
		Life game = createNewGame();
		game.resurrect(1, 2, Colour.RED);
		game.resurrect(4, 5, Colour.GREEN);
		game.clear();
		
		for (int i = 0; i < Life.ROWS; i++) {
			for (int j = 0; j < Life.ROWS; j++) {
				assertEquals("Clear is not working.", game.readCell(i, j), 
						Colour.GREY);
			}
		}
	}
	
	@Test
	public void testSetCellColorOutOfBound() {
		Life game = createNewGame();
		game.resurrect(-1, -2, Colour.RED);
		game.resurrect(1, 2, Colour.RED);
		assertEquals("SetCellColour IndexOutOfBoundsException is not catched.", 
				game.readCell(1, 2), Colour.RED);
	}
	
	@Test(expected=IndexOutOfBoundsException.class)
	public void testReadCellColorOutOfBound() {
		Life game = createNewGame();
		game.readCell(-1, -2);
	}	
	

}
