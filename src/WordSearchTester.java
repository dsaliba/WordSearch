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
     * Verifies that make can generate a simple 2D puzzle
     */
    public void testMake2D () {
        final String[] words = new String[] { "java", "lisp" };
        final char[][][] grid = _wordSearch.make(words, 1, 2, 4);
        final char[][] row = grid[0];
        assertTrue((row[0][0] == 'j' && row[0][1] == 'a' && row[0][2] == 'v' && row[0][3] == 'a'
                                   && row[1][0] == 'l' && row[1][1] == 'i' && row[1][2] == 's' && row[1][3] == 'p') ||
                           (row[0][3] == 'j' && row[0][2] == 'a' && row[0][1] == 'v' && row[0][0] == 'a'
                                   && row[1][3] == 'l' && row[1][2] == 'i' && row[1][1] == 's' && row[1][0] == 'p') ||
                           (row[0][0] == 'l' && row[0][1] == 'i' && row[0][2] == 's' && row[0][3] == 'p'
                                   && row[1][0] == 'j' && row[1][1] == 'a' && row[1][2] == 'v' && row[1][3] == 'a') ||
                           (row[0][3] == 'l' && row[0][2] == 'i' && row[0][1] == 's' && row[0][0] == 'p'
                                   && row[1][3] == 'j' && row[1][2] == 'a' && row[1][1] == 'v' && row[1][0] == 'a') ||
                           (row[0][0] == 'j' && row[0][1] == 'a' && row[0][2] == 'v' && row[0][3] == 'a'
                                   && row[1][3] == 'l' && row[1][2] == 'i' && row[1][1] == 's' && row[1][0] == 'p') ||
                           (row[0][3] == 'j' && row[0][2] == 'a' && row[0][1] == 'v' && row[0][0] == 'a'
                                   && row[1][0] == 'l' && row[1][1] == 'i' && row[1][2] == 's' && row[1][3] == 'p') ||
                           (row[0][0] == 'l' && row[0][1] == 'i' && row[0][2] == 's' && row[0][3] == 'p'
                                   && row[1][3] == 'j' && row[1][2] == 'a' && row[1][1] == 'v' && row[1][0] == 'a') ||
                           (row[0][3] == 'l' && row[0][2] == 'i' && row[0][1] == 's' && row[0][0] == 'p'
                                   && row[1][0] == 'j' && row[1][1] == 'a' && row[1][2] == 'v' && row[1][3] == 'a'));
    }

	@Test
	/**
	 * Verifies that make returns null when it's impossible to construct a puzzle.
	 */
	public void testMakeImpossible () {
		final String[] words = new String[] { "java" };
		final char[][][] grid = _wordSearch.make(words, 1, 1, 1);
		assertTrue(grid==null);
	}

    @Test
    /**
     * Verifies that make returns null when it's given an empty String array
     */
    public void testMakeEmpty() {
        final String[] words = new String[] {};
        final char[][][] grid = _wordSearch.make(words, 1, 1, 1);
        assertNotNull(grid);
    }

    @Test
    /**
     * Verifies that make can make a test given two words that are the same, a palindrome, and has only enough room for one copy
     */
    public void testMakePalindrome () {
        final String[] words = new String[] { "racecar", "racecar" };
        final char[][][] grid = _wordSearch.make(words, 1, 1, 7);
        final char[] row = grid[0][0];
        assertTrue((row[0] == 'r' && row[1] == 'a' && row[2] == 'c' && row[3] == 'e' && row[4] == 'c' && row[5] == 'a' && row[6] == 'r'));
    }

    @Test
    /**
     * Verifies that make will return null when given numbers
     */
    public void testMakeSpecialCharacters () {
        final String[] words = new String[] { "1", "2", "3" };
        final char[][][] grid = _wordSearch.make(words, 1, 1, 3);
        assertNull(grid);
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
	 *  Verifies that search works correctly in a tiny grid that is effectively 2D.
	 */
	public void testSearchSomeFail () {
		// Note: this grid is 1x2x3 in size
		final char[][][] grid = new char[][][] { { { 'a', 'b', 'c' },
				{ 'd', 'f', 'e' } } };
		final int[][][] locations = _wordSearch.searchForAll(grid, new String[] {"be", "ad", "steve gosling"});
		assertNotNull(locations[0]);
		assertNotNull(locations[1]);
		assertNull(locations[2]);
	}

	@Test
	/**
	 *  Verifies that search works correctly when there is no match in a tiny grid that is effectively 2D.
	 */
	public void testSearchFailSimple () {
		// Note: this grid is 1x2x3 in size
		final char[][][] grid = new char[][][] { { { 'a', 'b', 'c' },
				{ 'd', 'f', 'e' } } };
		final int[][] location = _wordSearch.search(grid, "fizzbuzz");
		assertNull(location);
	}

	@Test
	/**
	 *  Verifies that search works correctly when a fragment of a word doesnt exist past the edge
	 */
	public void testFailOffEdge () {
		// Note: this grid is 1x2x3 in size
		final char[][][] grid = new char[][][] { { { 'a', 'b', 'c' },
				{ 'd', 'f', 'e' } } };
		final int[][] location = _wordSearch.search(grid, "fender");
		assertNull(location);
	}

	@Test
	/**
	 *  Verifies that search respects bounds in a tiny grid that is effectively 2D. Assuming the search goes L to R and top to bottom
	 */
	public void testSearchOutOfBoundsTopLeftToBottomRight () {
		// Note: this grid is 1x2x3 in size
		final char[][][] grid = new char[][][] { { { 'c', 'c', 'a' },
				{ 'c', 'a', 't' } } };
		final int[][] location = _wordSearch.search(grid, "cat");
		assertNotNull(location);
	}

	@Test
	/**
	 *  Verifies that search respects bounds in a tiny grid that is effectively 2D. Assuming the search goes R to L and bottom to top
	 */
	public void testSearchOutOfBoundsBottomRightTopLeft () {
		// Note: this grid is 1x2x3 in size
		final char[][][] grid = new char[][][] { { { 't', 'a', 'c' },
				{ 'a', 'c', 'c' } } };
		final int[][] location = _wordSearch.search(grid, "cat");
		assertNotNull(location);
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
