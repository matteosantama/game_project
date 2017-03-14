import java.awt.*;
import javax.swing.*;

public class Hitori extends TheGame {

    private int[][] theGrid = new int[5][5];
    private int[][] theNums =  {{4, 5, 1, 2, 4}, {5, 4, 3, 2, 1}, {3, 3, 2, 1, 4}, {1, 4, 5, 3, 2}, {5, 1, 2, 4, 4}};
    private HitoriButton[][] buttons = new HitoriButton[5][5];
    private int[] possibleValues = {0, 1};
    private MyFrame frame;

    public Hitori(MyFrame f) {
	frame = f;
	this.resetGrid();
    }
    
    //initializes grid values to -1
    public void resetGrid() {
	for (int row = 0; row < theGrid.length; row++) {
	    for (int col = 0; col < theGrid[row].length; col++) {
		theGrid[row][col] = -1;
	    }
	}
     }

    //called when "solve" button is clicked to display solution
    public void fillGrid() {
	for (int row = 0; row < theGrid.length; row++) {
	    for (int col = 0; col < theGrid[row].length; col++) {
		if (isSelected(row, col)) {
		    buttons[row][col].set();
		}
	    }
	}
    }

    public void setElement(int row, int col) {
	if (theGrid[row][col] == 1) {
	    theGrid[row][col] = 0;
	} else {
	    theGrid[row][col] = 1;
	}
    }

    public boolean isSelected(int row, int col) {
	if (theGrid[row][col] == 1) {
	    return true;
	} else {
	    return false;
	}
    }

    //checks japanese crossword constraint. Initializes a copy array
    private boolean japaneseCrossword() {
	int[][] copy = new int[theGrid.length][theGrid[0].length];
	for (int row = 0; row < copy.length; row++) {
	    for (int col = 0; col < copy[row].length; col++) {
		copy[row][col] = theGrid[row][col];
	    }
	}
	if (theGrid[0][0] != 1) {
	    if (japaneseCrosswordRecursive(copy, 0, 0)) 
		return true;
	    else 
		return false;
	} else {
	    if (japaneseCrosswordRecursive(copy, 0, 1)) 
		return true;
	    else
		return false;
	}
    }

    // recursively calls itself on all connected squares. Marks a square if its 
    // already "been" there by assigning it a value of 2.
    private boolean japaneseCrosswordRecursive(int[][] copy, int row, int col) {
	copy[row][col] = 2;
	if (col-1 >= 0 && copy[row][col-1] < 1)
	    japaneseCrosswordRecursive(copy,row,col-1);
	if (col+1 < copy[row].length && copy[row][col+1] < 1)
	    japaneseCrosswordRecursive(copy,row,col+1);
	if (row-1 >= 0 && copy[row-1][col] < 1)
	    japaneseCrosswordRecursive(copy,row-1,col);
	if (row+1 < copy.length && copy[row+1][col] < 1)
	    japaneseCrosswordRecursive(copy,row+1,col);
	if (allReached(copy))
	    return true;
	else
	    return false;
    }

    // compares theGrid and copy to see if all available squares
    // have been reached
    private boolean allReached(int[][] toCompare) {
	for (int row = 0; row < theGrid.length; row++) {
	    for (int col = 0; col < theGrid[row].length; col++) {
	      	if (theGrid[row][col] != 1 && toCompare[row][col] != 2) 
		    return false;
	    }
	}
	return true;
    }


    // checks all constraints
    public boolean isSolved() {
	for (int row = 0; row < theGrid.length; row++) {
	    int[] remaining = new int[theGrid[row].length+1];
	    for (int col = 0; col < theGrid[row].length; col++) {
		if (!isSelected(row, col)) {
		    remaining[theNums[row][col]]++;
		}
	    }
	    for (int i = 0; i < remaining.length; i++) {
		if (remaining[i] > 1) {
		    return false;
		}
	    }
	}
	for (int col = 0; col < theGrid[0].length; col++) {
	    int[] remaining = new int[theGrid.length+1];
	    for (int row = 0; row < theGrid.length; row++) {
		if (!isSelected(row, col)) {
		    remaining[theNums[row][col]]++;
		}
	    }
	    for (int i = 0; i < remaining.length; i++) {
		if (remaining[i] > 1) {
		    return false;
		}
	    }
	}
	return true && noAdjacents() && japaneseCrossword();
    }

    // implementation of TheGame method that gets called by the solver
    public boolean fullCheck() {
	return isSolved();
    }

    // checks adjacency constraint. Checks four corners first, then four sides,
    // then all the middle squares
    public boolean noAdjacents() {
	for (int row = 0; row < theGrid.length; row++) {
	    for (int col = 0; col < theGrid[row].length; col++) {
		int count = 0;
		if (row==0 && col==0) {
		    if (isSelected(row+1,col) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row,col+1) && isSelected(row,col)) {
			count++;
		    }
		} else if (row==0 && col==theGrid[row].length-1) {
		    if (isSelected(row,col-1) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row+1,col) && isSelected(row,col)) {
			count++;
		    }
		} else if (row==theGrid.length-1 && col==0) {
		    if (isSelected(row-1,col) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row,col+1) && isSelected(row,col)) {
			count++; 
		    }
		} else if (row==theGrid.length-1 && col==theGrid[row].length-1) {
		    if (isSelected(row-1,col) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row,col-1) && isSelected(row,col)) {
			count++;
		    }
		} else if (row==0) {
		    if (isSelected(row,col-1) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row,col+1) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row+1,col) && isSelected(row,col)) {
			count++;
		    }
		} else if (row==theGrid.length-1) {
		    if (isSelected(row,col-1) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row,col+1) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row-1,col) && isSelected(row,col)) {
			count++;
		    }
		} else if (col==0) {
		    if (isSelected(row-1,col) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row+1,col) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row,col+1) && isSelected(row,col)) {
			count++;
		    }
		} else if (col==theGrid[row].length-1) {
		    if (isSelected(row-1,col) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row+1,col) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row,col-1) && isSelected(row,col)) {
			count++;
		    }
		} else {
		    if (isSelected(row,col-1) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row,col+1) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row+1,col) && isSelected(row,col)) {
			count++;
		    }
		    if (isSelected(row-1,col) && isSelected(row,col)) {
			count++;
		    }
		}
		if (count >= 1) {
		    return false;
		}
	    }
	}
	return true;
    }
						
    // constraint checked by the solver
    public boolean check() {
	return noAdjacents();
    }
    
    //creates the GUI by adding buttons to the frame
    public void buildGrid(Container largeContainer, JPanel centerContainer, JPanel bottomContainer) {

	int i = 0;
	int j = 0;
	for (int row = 0; row < 7; row++) {
	    for (int col = 0; col < 7; col++) {
		if (row == 0 || row == 6 || col == 0 || col == 6) {
		    centerContainer.add(Box.createRigidArea(new Dimension(5,0)));
		} else {
		    JButton jb = new HitoriButton(Integer.toString(theNums[j][i]),this,row-1,col-1);
		    centerContainer.add(jb);
		    buttons[j][i] = (HitoriButton) jb;
		    i++;
		    if (i > 4) {
			j++;
			i = 0;
		    }
		}
	    }
	}

	JButton solve = new TheSolver("Solve",this,theGrid,possibleValues);
	bottomContainer.add(solve);

	largeContainer.add(bottomContainer, BorderLayout.SOUTH);
	largeContainer.add(centerContainer, BorderLayout.CENTER);
    }

}