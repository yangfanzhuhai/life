package testsuite;

import static junit.framework.Assert.assertEquals;

import java.io.IOException;

import org.junit.Rule;
import org.junit.Test;

import life.Life;

public class TestSuite {

  @Test
  public void checkLabel() throws IOException {
    Life game = new Life();
    assertEquals(0, game.readTurn());
  }

}

