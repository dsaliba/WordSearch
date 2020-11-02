import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Implements a 3-d word search puzzle program.
 */
public class WordSearch3D {
	public WordSearch3D () {
	}

	final Random rng = new Random();

	/**
	 * Searches for all the words in the specified list in the specified grid.
	 * You should not need to modify this method.
	 * @param grid the grid of characters comprising the word search puzzle
	 * @param words the words to search for
	 * @return a list of lists of locations of the letters in the words
	 */
	public int[][][] searchForAll (char[][][] grid, String[] words) {
		final int[][][] locations = new int[words.length][][];
		for (int i = 0; i < words.length; i++) {
			locations[i] = search(grid, words[i]);
		}
		return locations;
	}


	/**
	 * Searches for the specified word in the specified grid.
	 * @param grid the grid of characters comprising the word search puzzle
	 * @param word the word to search for
	 * @return If the grid contains the
	 * word, then the method returns a list of the (3-d) locations of its letters; if not, return null.
	 */
	public int[][] search (char[][][] grid, String word) {
		// TODO: implement me
		char currentLetter = word.charAt(0);
		for (int x = 0; x < grid.length; x++) {
			for (int y = 0; y < grid[x].length; y++) {
				for (int z=0; z < grid[x][y].length; z++) {
					if (grid[x][y][z]==currentLetter) {
						//Create vectors for all directions words could go
						for (int i = -1; i < 2; i++) {
							for (int j = -1; j<2; j++) {
								for (int k = -1; k < 2; k++) {
									if (i == 0 && j == 0 && k == 0) continue;
									int curX = x+i;
									int curY = y+j;
									int curZ = z+k;
									//Step along vectors for each letter and return locations
									for (int c = 1; c < word.length(); c++) {
										if (curX < 0 || curY < 0 || curZ < 0 || curX >=grid.length || curY >= grid[x].length || curZ >= grid[x][y].length) break;
										if (word.charAt(c) == grid[curX][curY][curZ]) {
											//Run if match
											if (c == word.length()-1) {
												int[][] ret = new int[word.length()][];
												for (int r = 0; r < ret.length; r++) {
													ret[r] = new int[] {x+(r*i), y+(r*j), z+(r*k)};
												}
												return ret;
											}
											//Move to next space and letter
											curX += i;
											curY += j;
											curZ += k;
										} else {
											break;
										}
									}
								}
							}
						}

					}
				}
			}
		}
		return null;
	}


	/**
	 * Tries to create a word search puzzle of the specified size with the specified
	 * list of words.
	 * @param words the list of words to embed in the grid
	 * @param sizeX size of the grid along first dimension
	 * @param sizeY size of the grid along second dimension
	 * @param sizeZ size of the grid along third dimension
	 * @return a 3-d char array if successful that contains all the words, or <tt>null</tt> if
	 * no satisfying grid could be found.
	 */
	public char[][][] make (String[] words, int sizeX, int sizeY, int sizeZ) {
        for (int a=0; a < 1000; a++){ //runs whole thing 1000 times
            char[][][] grid = new char[sizeX][sizeY][sizeZ]; //creates empty grid
            boolean cantPlaceWord = false; //helps determine whether or not to fill in empty spaces at the end
            for (int b=0; b < words.length; b++){ //goes through each word in the list
                boolean placedWord = false; //helps determine whether to break out of the outerLoop or not
                outerLoop:
                for (int c=0; c < 1000; c++){ //runs 1000 times
                    int randomPositionX = rng.nextInt(sizeX);
                    int randomPositionY = rng.nextInt(sizeY);
                    int randomPositionZ = rng.nextInt(sizeZ);
                    for (int i=-1; i < 2; i++){
                        for (int j=-1; j < 2; j++){
                            for (int k=-1; k < 2; k++){
                                if (i==0 && j==0 && k==0) continue;
                                if (grid[randomPositionX][randomPositionY][randomPositionZ] == 0
                                        || grid[randomPositionX][randomPositionY][randomPositionZ] == words[b].charAt(0)){
                                    for (int l = 1; l < words[b].length(); l++){ //goes through each letter in the rest of the word to see if they will fit in the given direction
                                        if (randomPositionX + (l * i) >= sizeX || randomPositionY + (l * j) >= sizeY || randomPositionZ + (l * k) >= sizeZ //sees if the letter will fall outside the graph in the positive direction
                                                || randomPositionX + (l * i) < 0 || randomPositionY + (l * j) < 0 || randomPositionZ + (l * k) < 0){       //sees if the letter will fall outside the graph in the negative direction
                                            break; //if any of those is true, the letter cannot fit, so we break out of the smaller loop while keeping placedWord = false
                                        }else if (!(grid[randomPositionX + (l * i)][randomPositionY + (l * j)][randomPositionZ + (l*k)] == 0                      //sees if the position the letter will fall at is empty
                                                || grid[randomPositionX + (l * i)][randomPositionY + (l * j)][randomPositionZ + (l*k)] == words[b].charAt(l))) {  //sees if the position the letter will fall at already has that letter in it
                                            break; //if neither of those is true, the letter cannot fit, so we break out of the smaller loop while keeping placedWord = false
                                        }else if (l == words[b].length() - 1){ //if we've reached the last letter, and have not broken out of the loop, then the word can fit
                                            placedWord = true;
                                            for (int d = 0; d < words[b].length(); d++){ //this loop places the word in the location
                                                grid[randomPositionX + (d * i)][randomPositionY + (d * j)][randomPositionZ + (d * k)] = words[b].charAt(d);
                                            }
                                            break outerLoop;
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if (placedWord) break;
                }
                if (!placedWord){
                    cantPlaceWord = true;
                    break;
                }
            }
            if (!cantPlaceWord) {
                for (int i = 0; i < sizeX; i++){
                    for (int j = 0; j < sizeY; j++){
                        for (int k = 0; k < sizeZ; k++){
                            if (grid[i][j][k] == 0){
                                grid[i][j][k] = (char) (rng.nextInt(26) + 'a');
                            }
                        }
                    }
                }
                return grid;
            }
        }
	    return null;
	}

	/**
	 * Exports to a file the list of lists of 3-d coordinates.
	 * You should not need to modify this method.
	 * @param locations a list (for all the words) of lists (for the letters of each word) of 3-d coordinates.
	 * @param filename what to name the exported file.
	 */
	public static void exportLocations (int[][][] locations, String filename) {
		// First determine how many non-null locations we have
		int numLocations = 0;
		for (int i = 0; i < locations.length; i++) {
			if (locations[i] != null) {
				numLocations++;
			}
		}

		try (final PrintWriter pw = new PrintWriter(filename)) {
			pw.print(numLocations);  // number of words
			pw.print('\n');
			for (int i = 0; i < locations.length; i++) {
				if (locations[i] != null) {
					pw.print(locations[i].length);  // number of characters in the word
					pw.print('\n');
					for (int j = 0; j < locations[i].length; j++) {
						for (int k = 0; k < 3; k++) {  // 3-d coordinates
							pw.print(locations[i][j][k]);
							pw.print(' ');
						}
					}
					pw.print('\n');
				}
			}
			pw.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	/**
	 * Exports to a file the contents of a 3-d grid.
	 * You should not need to modify this method.
	 * @param grid a 3-d grid of characters
	 * @param filename what to name the exported file.
	 */
	public static void exportGrid (char[][][] grid, String filename) {
		try (final PrintWriter pw = new PrintWriter(filename)) {
			pw.print(grid.length);  // height
			pw.print(' ');
			pw.print(grid[0].length);  // width
			pw.print(' ');
			pw.print(grid[0][0].length);  // depth
			pw.print('\n');
			for (int x = 0; x < grid.length; x++) {
				for (int y = 0; y < grid[0].length; y++) {
					for (int z = 0; z < grid[0][0].length; z++) {
						pw.print(grid[x][y][z]);
						pw.print(' ');
					}
				}
				pw.print('\n');
			}
			pw.close();
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		}
	}

	/**
	 * Creates a 3-d word search puzzle with some nicely chosen fruits and vegetables,
	 * and then exports the resulting puzzle and its solution to grid.txt and locations.txt
	 * files.
	 */
	public static void main (String[] args) {
		final WordSearch3D wordSearch = new WordSearch3D();
		final String[] words = new String[] { "apple", "orange", "pear", "peach", "durian", "lemon", "lime", "jackfruit", "plum", "grape", "apricot", "blueberry", "tangerine", "coconut", "mango", "lychee", "guava", "strawberry", "kiwi", "kumquat", "persimmon", "papaya", "longan", "eggplant", "cucumber", "tomato", "zucchini", "olive", "pea", "pumpkin", "cherry", "date", "nectarine", "breadfruit", "sapodilla", "rowan", "quince", "toyon", "sorb", "medlar" };
		final int xSize = 10, ySize = 10, zSize = 10;
		final char[][][] grid = wordSearch.make(words, xSize, ySize, zSize);
		exportGrid(grid, "grid.txt");

		final int[][][] locations = wordSearch.searchForAll(grid, words);
		exportLocations(locations, "locations.txt");
	}
}
