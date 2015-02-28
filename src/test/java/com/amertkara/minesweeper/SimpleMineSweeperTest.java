package com.amertkara.minesweeper;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * Tests the different functional 
 * 
 * @version 0.0.1 27 February 2015
 * @author A. Mert Kara
 */
public class SimpleMineSweeperTest extends TestCase {
	
	/* MineField strings for different scenarios */
	private final String properMineField = "*...\n..*.\n....";
	private final String properHintField = "*211\n12*1\n0111";
	private final String illegalCharMineField = "*...\n..*.\n.&..";
	private final String unequalRowsMineField = "*...\n..*..\n....";
	private final char[][] properMatrix = {{'*', '.', '.', '.'},
										   {'.', '.', '*', '.'},
										   {'.', '.', '.', '.'}};
	
	/* The object under test */
	private SimpleMineSweeper simpleMineSweeper;

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		this.simpleMineSweeper = new SimpleMineSweeper();
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	/* (non-Javadoc)
	 * Tests the parameterized constructor for non-unique arguments and 
	 * vice-versa.
	 */
	public void testConstructor(){
		
		try {
			new SimpleMineSweeper('*', '.', '*');
			TestCase.fail("Non-unique parameters should cause a fail!");
		} catch (IllegalArgumentException expected) {
			System.out.println("Expected: " + expected.getMessage());
		}
		
		try{
			new SimpleMineSweeper('*', '.', '\n');
		} catch (IllegalArgumentException unexpected) {
			System.err.println(unexpected.getMessage());
			TestCase.fail("Unique parameters shouldn't cause a fail!");
		}
		
	}

	/* (non-Javadoc)
	 * Tests setMineField for different mineField string scenarios.
	 */
	public void testSetMineField(){
		
		/* Test for illegal characters inside the mineField string */
		try {
			simpleMineSweeper.setMineField(this.illegalCharMineField);
			TestCase.fail("Unexpected characters inside the mineField should"
						  + "cause a fail!");
		} catch (IllegalArgumentException expected) {
			System.out.println("Expected: " + expected.getMessage());
		}
		
		/* Test for unequal rows */
		try {
			simpleMineSweeper.setMineField(this.unequalRowsMineField);
			TestCase.fail("Unequal rows should cause a fail!");
		} catch (IllegalArgumentException expected) {
			System.out.println("Expected: " + expected.getMessage());
		}
		
		/* Test with a proper mineField string */
		try {
			simpleMineSweeper.setMineField(this.properMineField);
			
			assertTrue(Arrays.deepEquals(properMatrix, 
										 simpleMineSweeper.getMatrix()));
		} catch (Exception unexpected) {
			System.err.println(unexpected.getMessage());
			TestCase.fail("Proper mineField string shouldn't cause a fail!");
		}
		
	}
	
	/* (non-Javadoc)
	 * Tests getHintField
	 */
	public void testGetHintField(){
		
		/* Uninitialized mine field should throw IllegalStateException */
		try {
			new SimpleMineSweeper().getHintField();
		} catch (IllegalStateException expected) {
			System.out.println("Expected: " + expected.getMessage());
		}
		
		try {
			simpleMineSweeper.setMineField(properMineField);
			assertEquals(properHintField, simpleMineSweeper.getHintField());
		} catch (IllegalArgumentException unexpected) {
			TestCase.fail("getNeighors failed!");
			System.err.println(unexpected.getMessage());
		}
	}
	
}
