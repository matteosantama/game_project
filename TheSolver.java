import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class TheSolver extends JButton implements ActionListener {
	
    private TheGame g;
    private int[][] theGrid;
    private int[] possibleValues;
    
    public TheSolver(String text, TheGame game, int[][] grid, int[] pv) {
	super(text);
	g = game;
	theGrid = grid;
	possibleValues = pv;
	addActionListener(this);
    }
	
    
    private boolean label(int[][] theGrid, TheGame g, int[] possibleValues, int row, int col) {
	
	if (row == theGrid.length) {
	    return g.fullCheck();
	}
    
	for(int v : possibleValues) {
	    theGrid[row][col] = v;
	    if (g.check()) {
		int newcolumn = col + 1;
		int newrow = row;
		if (newcolumn == theGrid[row].length) {
		    newcolumn = 0;
    		    newrow = row + 1;
    		}
    		if (label(theGrid, g, possibleValues, newrow, newcolumn)) {
    		    return true;
    		}
    	    }
	}
	theGrid[row][col] = -1;
    	return false;
    }

    //method that gets called when "solve" button is clicked
    //uses fillGrid() to change theGUI
    public void actionPerformed(ActionEvent e) {
	g.resetGrid();
	boolean result = label(theGrid, g, possibleValues, 0, 0);
	if (result) {
	    g.fillGrid();
	}
    }
}