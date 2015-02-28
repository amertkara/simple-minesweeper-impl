package com.amertkara.minesweeper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Implements the basic functionalities of a MineSweeper interface.
 * 
 * @version 0.0.1 27 February 2015
 * @author A. Mert Kara
 *
 */
public class SimpleMineSweeper implements MineSweeper{
	
	/** mineChar represents a mine */
	private char mineChar;
	
	/** emptyChar represents a empty filed (no mine) */
	private char emptyChar;
	
	/** linebreakChar represents end of row in the matrix */
	private char linebreakChar;
	
	/** matrix holds the mine field */
	private char[][] matrix;
	
	/** number of rows in the matrix */
	private int sizeRow;
	
	/** number of column in the matrix */
	private int sizeCol;
	
	/**
	 * Default Constructor
	 * 
	 * Initializes mineChar, emptyChar and linebreakChar to defaults.
	 */
	public SimpleMineSweeper(){
		this.mineChar = '*';
		this.emptyChar = '.';
		this.linebreakChar = '\n';
	}
	
	/**
	 * Parameterized Constructor
	 * 
	 * Initializes minerChar, emtpyChar and linebreakChar to custom chars.
	 * These variables should be unique otherwise it would affect the matrix
	 * structure.
	 * 
	 * @param mineChar
	 * @param emptyChar
	 * @param linebreakChar
	 * @throws IllegalArgumentException if the variables are not different.
	 */
	public SimpleMineSweeper(char mineChar, char emptyChar, char linebreakChar) 
			throws IllegalArgumentException{
		/* 
		 * Check if the variables are unique by inserting them into a HashSet 
		 * which only holds unique variables.
		 */  
		if(new HashSet<Character>(Arrays.asList(mineChar, emptyChar, 
												linebreakChar)).size() < 3) {
			throw new IllegalArgumentException("All variables must be "
											   + "different.");
		}
		
		this.mineChar = mineChar;
		this.emptyChar = emptyChar;
		this.linebreakChar = linebreakChar;
	}

	/**
	 * Getter for matrix 
	 * 
	 * @return Matrix variable.
	 */
	public char[][] getMatrix(){
		return this.matrix;
	}
	
	/**
	 * Getter for sizeRow
	 * 
	 * @return Row count of the mine field
	 */
	public int getSizeRow(){
		return this.sizeRow;
	}
	
	/**
	 * Getter for sizeCol
	 * 
	 * @return Column count of the mine field
	 */
	public int getSizeCol(){
		return this.sizeCol;
	}
	
	/**
	 * Implements the abstract setMineField method.
	 * 
	 * The mine field string is checked for illegal characters and also the
	 * size of the rows are compared to form a proper matrix.
	 *  
	 * @param mineField contains the mine field.
	 * @see MineSweeper#setMineField(String)
	 * @throws IllegalArgumentException If the mineField variable contains
	 * illegal characters or the size of the rows are not equal.
	 */
	public void setMineField(String mineField) throws IllegalArgumentException {
		String[] rows;
		
		/*
		 * Run a simple regular expression to check mineField doesn't contain 
		 * illegal characters.
		 */
		if (!mineField.matches("^[" + this.emptyChar + this.mineChar + 
								this.linebreakChar + "]+$")){
			throw new IllegalArgumentException("mineField string must contain: "
												+ this.mineChar + ", " 
												+ this.emptyChar + ", " 
												+ this.linebreakChar);
		}
		
		/* Split rows by the line end. */
		rows = mineField.split(String.valueOf(this.linebreakChar));
		
		/* Get the number of rows and initiate the matrix. */
		this.sizeRow = rows.length;
		this.matrix = new char[this.sizeRow][];
		
		/* Get each row string and insert the matrix as a char array */
		for (int i = 0; i < rows.length; i++) {
			/* Check for illegal row lengths */
			if (i > 0 && rows[i].length() != rows[i-1].length()){
				throw new IllegalArgumentException("All the rows must be of "
												   + "the same length.");
			}
			this.matrix[i] = rows[i].toCharArray();
		}
		
		/* Finally pick the first row and set it as the matrix column number */
		this.sizeCol = this.matrix[0].length;	
	}
	
	/**
	 * Gets the neighbor cells of a given cell position.
	 * 
	 * This method is mainly used to find the neighbor mines but it can be used 
	 * for other scenarios that require finding neighbor cells.
	 * 
	 * @param posRow 0-based row number of the cell.
	 * @param posCol 0-based column number of the cell.
	 * @return A list of neighbor cells starting from the top-left corner and 
	 * goes row by row.
	 * @throws IllegalArgumentException If the given cell position is outside 
	 * the matrix boundaries.
	 */
	private ArrayList<Character> getNeighbors(int posRow, int posCol) 
			throws IllegalArgumentException{
		ArrayList<Character> neighbours = new ArrayList<Character>();
		
		/* Check if the given cell position in the matrix boundaries. */
		if (posRow > this.sizeRow - 1 || posRow < 0 || 
				posCol > this.sizeCol -1 || posCol < 0){
			throw new IllegalArgumentException("The cell must be inside the "
											   + "matrix boundaries.");
		}
		
		/* 
		 * By finding upper/lower row/column boundaries, find the sub-matrix
		 * around the cell. 
		 */
		int rowLowerBound = (posRow - 1 >= 0) ? posRow - 1 : posRow;
		int rowUpperBound = (posRow + 1 <= this.sizeRow - 1) ? posRow + 1 
															 : posRow;
		int columnLowerBound = (posCol - 1 >= 0) ? posCol - 1 : posCol;
		int columnUpperBound = (posCol + 1 <= this.sizeCol - 1) ? posCol + 1 
																: posCol;
		
		/* Loop over the cells of the sub-matrix, ignore the cell's itself. */
		for (int r = rowLowerBound; r <= rowUpperBound; r++) {
			for (int c = columnLowerBound; c <= columnUpperBound; c++) {
				if (c != posCol || r != posRow){
					neighbours.add(this.matrix[r][c]);
				}
			}
		}
		
		return neighbours;
	}
	
	/**
	 * Gets the neighbor mine count for a given cell.
	 * 
	 * @param posRow 0-based row number of the cell.
	 * @param posCol 0-based column number of the cell.
	 * @return Number of mines around the cell.
	 */
	private int getNeighborMineCount(int posRow, int posCol){
		int mineCount = 0;
		
		/* Get the neighbors and check for mines */
		for (char c : this.getNeighbors(posRow, posCol)){
			if (c == this.mineChar){
				mineCount++;
			}
		}
		
		return mineCount;
	}

	/**
	 * Implements the abstract getHintField method.
	 * 
	 * The mine field string is checked for illegal characters and also the
	 * size of the rows are compared to form a proper matrix.
	 *  
	 * @see MineSweeper#getHintField()
	 * @throws IllegalStateException If the mineField is not initialized.
	 */
	public String getHintField() throws IllegalStateException {
		String hintField = "";
		
		if(this.matrix == null){
			throw new IllegalStateException("Minefield has not been "
											+ "initialized yet.");
		}
		
		/* Loop over the entire matrix from the top left corner and find each 
		 * empty cell's neighbor mine count. 
		 */
		int lastRow = this.sizeRow - 1;
		for (int r = 0; r < this.sizeRow; r++) {
			for (int c = 0; c < this.sizeCol; c++) {
				if (this.matrix[r][c] == this.mineChar){
					hintField += this.mineChar;
				}
				else{
					hintField += Character.forDigit(this.getNeighborMineCount(r, c), 
													10);
				}
			}
			if (r != lastRow){
				hintField += this.linebreakChar;
			}
		}
		
		return hintField;
	}
	
}
