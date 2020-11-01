import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import java.util.*;

/**
 * Code to test <tt>WordSearch3D</tt>.
 */
public class WordSearchTester {
	private WordSearch3D _wordSearch;

	@Test
	/**
	 * Verifies that make can generate a very simple puzzle that is effectively 1d.
	 */
	public void testMake1D () {
		final String[] words = new String[] { "java" };
		// Solution is either java or avaj
		final char[][][] grid = _wordSearch.make(words, 1, 1, 4);
		final char[] row = grid[0][0];
		assertTrue((row[0] == 'j' && row[1] == 'a' && row[2] == 'v' && row[3] == 'a') ||
		           (row[3] == 'j' && row[2] == 'a' && row[1] == 'v' && row[0] == 'a'));
	}

	@Test
	/**
	 * Verifies that make returns null when it's impossible to construct a puzzle.
	 */
	public void testMakeImpossible () {
		// TODO: implement me
	}

        @Test
        /**
         *  Verifies that search works correctly in a tiny grid that is effectively 2D.
         */
        public void testSearchSimple () {
                // Note: this grid is 1x2x3 in size
                final char[][][] grid = new char[][][] { { { 'a', 'b', 'c' },
                                                           { 'd', 'f', 'e' } } };
                final int[][] location = _wordSearch.search(grid, "be");
                assertNotNull(location);
                assertEquals(location[0][0], 0);
                assertEquals(location[0][1], 0);
                assertEquals(location[0][2], 1);
                assertEquals(location[1][0], 0);
                assertEquals(location[1][1], 1);
                assertEquals(location[1][2], 2);
        }

	@Test
	/**
	 * Verifies that make can generate a grid when it's *necessary* for words to share
	 * some common letter locations.
	 */
	public void testMakeWithIntersection () {
		final String[] words = new String[] { "amc", "dmf", "gmi", "jml", "nmo", "pmr", "smu", "vmx", "yma", "zmq" };
		final char[][][] grid = _wordSearch.make(words, 3, 3, 3);
		assertNotNull(grid);
	}

	@Test
	/**
	 * Verifies that make returns a grid of the appropriate size.
	 */
	public void testMakeGridSize () {
		final String[] words = new String[] { "at", "it", "ix", "ax" };
		final char[][][] grid = _wordSearch.make(words, 17, 11, 13);
		assertEquals(grid.length, 17);
		for (int x = 0; x < 2; x++) {
			assertEquals(grid[x].length, 11);
			for (int y = 0; y < 2; y++) {
				assertEquals(grid[x][y].length, 13);
			}
		}
	}

	/* TODO: write more methods for both make and search. */

	@Before
	public void setUp () {
		_wordSearch = new WordSearch3D();
	}
}
