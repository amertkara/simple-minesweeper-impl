package com.amertkara.minesweeper;
 
/**
* A mine-field of N x M squares is represented by N lines of exactly M 
* characters each. The character '*' represents a mine * and the character '.' 
* represents no-mine. Lines are separated by '\n'
*
* Example mine-field string (as input to setMineField()): "*...\n..*.\n...."
* (a 3 x 4 mine-field of 12 squares, 2 of which are mines)
*
* getHintField() produces a hint-field of identical dimensions as the 
* mineFiled() where each square is a * for a mine or the number of adjacent 
* mine-squares if the square does not contain a mine.
*
* Example hint-field string (as returned by getHintField(): "*211\n12*1\n0111" 
* (for the above input)
*
*/
public interface MineSweeper {
 
    /**
     * Initializes the mine-field
     *
     * @param minefieldstring containing the mines (see interface javadoc for 
     * representation)
     */
    void setMineField(String mineField);
 
    /**
     * Produces a hint-field for the current mine-filed (see interface javadoc 
     * for details)
     *
     * @return a string representation of the hint-field
     * @throws IllegalStateException if the mine-field has not been initialized 
     * (i.e. setMineField() has not been called)
     */
    String getHintField() throws IllegalStateException;
 
}