import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class Norinori extends TheGame {

    private int[][] theGrid = new int[7][7];
    private Color c1 = Color.gray;
    private Color c2 = Color.darkGray;
    private Color c3 = Color.lightGray;
    private Color c4 = Color.white;
    private Color[][] gameFill = {{c1,c2,c3,c3,c3,c3,c3}, {c1,c2,c4,c4,c4,c2,c2}, {c1,c1,c1,c2,c2,c2,c2}, {c4,c2,c2,c2,c2,c2,c2}, {c4,c3,c3,c4,c4,c4,c4}, {c4,c3,c3,c4,c1,c1,c2}, {c3,c3,c4,c4,c1,c1,c2}};
    private NorinoriButton[][] buttons = new NorinoriButton[7][7];
    private int[] possibleValues = {0, 1};
    private MyFrame frame;

    // 3D array that contains the row and coluns of each box in each room
    private int[][][] rooms = {{{0,0}, {1,0}, {2,0}, {2,1}, {2,2}}, {{0,1}, {1,1}}, {{0,2},
                                {0,3}, {0,4}, {0,5}, {0,6}}, {{1,2}, {1,3}, {1,4}}, {{1,5},
                                {1,6}, {2,3}, {2,4}, {2,5}, {2,6}, {3,1}, {3,2}, {3,3}, {3,4},
                                {3,5}, {3,6}}, {{3,0}, {4,0}, {5,0}}, {{4,1}, {4,2}, {5,1},
                                {5,2}, {6,0}, {6,1}}, {{4,3}, {4,4}, {4,5}, {4,6}, {5,3}, {6,2},
                                {6,3}}, {{5,4}, {5,5}, {6,4}, {6,5}}, {{5,6}, {6,6}}};


    public Norinori(MyFrame f) {
        frame = f;
        this.resetGrid();
    }

    // initializes all grid values to -1
    public void resetGrid() {
    	for (int row = 0; row < theGrid.length; row++) {
    	    for (int col = 0; col < theGrid[row].length; col++) {
    		theGrid[row][col] = -1;
    	    }
    	}
    }

    public void fillGrid() {
    	for (int row = 0; row < theGrid.length; row++) {
    	    for (int col = 0; col < theGrid[row].length; col++) {
    		if (theGrid[row][col] == 1) {
    		    buttons[row][col].set();
    		}
    	    }
    	}
    }

    public void setElement(int row, int col) {
    	if (theGrid[row][col] == 0 || theGrid[row][col] == -1) {
    	    theGrid[row][col] = 1;
    	} else {
    	    theGrid[row][col] = 0;
    	}
    }

    public boolean isSelected(int row, int col) {
    	if (theGrid[row][col] == 1) {
    	    return true;
    	} else {
    	    return false;
    	}
    }

    public boolean isSolved() {
        return onlyTwoTouching() && twoPerRoom();
    }

    // method that checks to see if a room has enough available boxes for
    // the requisite two checked boxes per room
    private boolean canHaveTwo(int[][] boxes) {
    	int remaining = 0;
    	int withValue = 0;
    	int selected = 0;
    	for (int i = 0; i < boxes.length; i++) {
    	    if (theGrid[boxes[i][0]][boxes[i][1]] == -1) {
    		remaining++;
    	    }
    	    if (theGrid[boxes[i][0]][boxes[i][1]] == 1) {
    		selected++;
    		withValue++;
    	    }
    	    if (theGrid[boxes[i][0]][boxes[i][1]] == 0) {
    		withValue++;
    	    }
    	}
    	if (remaining + selected >= 2) {
    	    return true;
    	}
    	return false;
    }

    // checks constraints
    public boolean check() {
    	for (int room = 0; room < rooms.length; room++) {
    	    if (!canHaveTwo(rooms[room])) {
    		return false;
    	}
	}

	for (int room = 0; room < rooms.length; room++) {
	    int box = 0;
	    int count = 0;
	    while (box < rooms[room].length) {
		if (theGrid[rooms[room][box][0]][rooms[room][box][1]]==1) {
		    count++;
		}
		box++;
	    }
	    if (count > 2) {
		return false;
	    }
	}
	return twoOrLessTouching();
    }

    // checks if each room has exactly two checked boxes
    private boolean twoPerRoom() {
	    for (int room = 0; room < rooms.length; room++) {
    	    int box = 0;
    	    int count = 0;
    	    while (box < rooms[room].length) {
    		if (theGrid[rooms[room][box][0]][rooms[room][box][1]]==1) {
    		    count++;
    		}
    		box++;
    	    }
    	    if (count != 2) {
    		return false;
    	    }
    	}
    	return true;
    }

    public boolean fullCheck() {
	       return isSolved();
    }

    // checks corner boxes, sides, and then all other boxes
    private boolean twoOrLessTouching() {
    	for (int row = 0; row < theGrid.length; row++) {
    	    for (int col = 0; col < theGrid[row].length; col++) {
        		int count = 0;
        		if (row==0 && col==0 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row+1,col))
                        count++;
        		    if (isSelected(row,col+1))
                        count++;
        		} else if (row==0 && col==theGrid[row].length-1 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row,col-1))
                        count++;
        		    if (isSelected(row+1,col))
                        count++;
        		} else if (row==theGrid.length-1 && col==0 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		} else if (row==theGrid.length-1 && col==theGrid[row].length-1 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		} else if (row==0 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		} else if (row==theGrid.length-1 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		} else if (col==0 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		} else if (col==theGrid[row].length-1 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		} else if (isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		}
        		if (count > 2) {
                    return false
        		}
    	    }
    	}
        return true
    }

    // checks corners, sides, and then all inner boxes
    private boolean onlyTwoTouching() {
    	boolean correct = true;
        	for (int row = 0; row < theGrid.length; row++) {
        	    for (int col = 0; col < theGrid[row].length; col++) {
        		int count = 0;
        		if (row==0 && col==0 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		} else if (row==0 && col==theGrid[row].length-1 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		} else if (row==theGrid.length-1 && col==0 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		} else if (row==theGrid.length-1 && col==theGrid[row].length-1 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		} else if (row==0 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		} else if (row==theGrid.length-1 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		} else if (col==0 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		} else if (col==theGrid[row].length-1 && isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		} else if (isSelected(row,col)) {
        		    count++;
        		    if (isSelected(row,col-1)) {
        			count++;
        		    }
        		    if (isSelected(row,col+1)) {
        			count++;
        		    }
        		    if (isSelected(row+1,col)) {
        			count++;
        		    }
        		    if (isSelected(row-1,col)) {
        			count++;
        		    }
        		}
        		if (count != 2 && count != 0) {
        		    correct = false;
        		}
    	    }
    	}
    	return correct;
    }

    // builds game by adding buttons to the GUI
    public void buildGrid(Container largeContainer, JPanel centerContainer, JPanel bottomContainer) {
	for (int row = 0; row < theGrid.length; row++) {
	    for (int col = 0; col < theGrid[row].length; col++) {
		JButton jb = new NorinoriButton(this, row, col, gameFill[row][col]);
		centerContainer.add(jb);
		buttons[row][col] = (NorinoriButton) jb;
	    }
	}

	JButton solve = new TheSolver("Solve",this,theGrid,possibleValues);
	bottomContainer.add(solve);

	largeContainer.add(bottomContainer, BorderLayout.SOUTH);
	largeContainer.add(centerContainer, BorderLayout.CENTER);
    }

}
