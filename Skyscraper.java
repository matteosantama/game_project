import java.awt.*;
import javax.swing.*;

public class Skyscraper extends TheGame {

    private int[][] theGrid = new int[5][5];
    private int[] possibleValues = {1, 2, 3, 4, 5};
    private int[] rightTargets = { 2, 3, 1, 2, 3 };
    private int[] leftTargets = { 2, 1, 2, 4, 2 };
    private int[] topTargets = { 2, 1, 5, 2, 2 };
    private int[] bottomTargets = { 3, 4, 1, 2, 3 };
    private SkyscraperButton[][] buttons = new SkyscraperButton[5][5];
    private MyFrame frame;
    
    public Skyscraper(MyFrame f) {
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
		buttons[row][col].set();
	    }
	}
    }

    
    public boolean isSolved() {
	int[] rightSeen = {0, 0, 0, 0, 0};
	int[] leftSeen = {0, 0, 0, 0, 0};
	int[] topSeen = {0, 0, 0, 0, 0};
	int[] bottomSeen = {0, 0, 0, 0, 0};

	//assigns values to leftSeen
	for (int row = 0; row < theGrid.length; row++) {
	    int largest = 0;
	    for (int col = 0; col < theGrid[row].length; col++) {
		if (theGrid[row][col] > largest) {
		    leftSeen[row]++;
		    largest = theGrid[row][col];
		}
	    }
	}
	//assigns values to topSeen
	for (int col = 0; col < theGrid[0].length; col++) {
	    int largest = 0;
	    for (int row = 0; row < theGrid.length; row++) {
		if (theGrid[row][col] > largest) {
		    topSeen[col]++;
		    largest = theGrid[row][col];
		}
	    }
	}
	//assigns values to rightSeen
	for (int row = 0; row < theGrid.length; row++) {
            int largest = 0;
            for (int col = theGrid[row].length-1; col >= 0; col--) {
                if (theGrid[row][col] > largest) {
                    rightSeen[row]++;
                    largest = theGrid[row][col];
                }
            }
	}
	//assigns values to bottomSeen
	for (int col = 0; col < theGrid[0].length; col++) {
            int largest = 0;
            for (int row = theGrid.length-1; row >= 0; row--) {
                if (theGrid[row][col] > largest) {
                    bottomSeen[col]++;
                    largest = theGrid[row][col];
                }
            }
	}
	//compares targets to seen
	boolean solved = true;
	for (int i = 0; i < 5; i++) {
	    if (topSeen[i]!=topTargets[i] || bottomSeen[i]!=bottomTargets[i] || rightSeen[i]!=rightTargets[i] || leftSeen[i]!=leftTargets[i]) {
		solved = false;
	    }
      	}
	return solved && noRepeats();
    }
    
    public boolean check() {
        return checkLeftTargets() && checkTopTargets() && noRepeats() && checkRightTargets() && checkBottomTargets();
    }

    public boolean fullCheck() {
	return isSolved();
    }

    //increments grid values up to 5, and then resets them back to -1
    public void setElement(int row, int col) {
	if (theGrid[row][col] == -1) {
	    theGrid[row][col] = 1;
	} else {
	    theGrid[row][col]++;
	}
	if (theGrid[row][col] > 5) {
	    theGrid[row][col] = -1;
	}
    }

    public int getElement(int row, int col) {
	return theGrid[row][col];
    }
    
    // checks to verify that seen is still less than the target
    private boolean checkLeftTargets() {
        for (int row = 0; row < theGrid.length; row++) {
            int largest = 0;
            int seen = 0;
            for (int col = 0; col < theGrid[row].length; col++) {
                if (theGrid[row][col] > largest) {
                    seen++;
                    largest = theGrid[row][col];
                }
            }
            if (seen > leftTargets[row]) {
                return false;
            }
        }
        return true;
    }
    
    // checks to verify that seen is still less than the target
    private boolean checkRightTargets() {
        for (int row = 0; row < theGrid.length; row++) {
            int largest = 0;
            int seen = 0;
            for (int col = theGrid[row].length-1; col >= 0; col--) {
                if (theGrid[row][col] > largest) {
                    seen++;
                    largest = theGrid[row][col];
                }
            }
            if (seen > rightTargets[row] && theGrid[row][theGrid[row].length-1] != -1) {
                return false;
            }
        }
        return true;
    }

    // checks to verify that seen is still less than the target    
    private boolean checkTopTargets() {
        for (int col = 0; col < theGrid[0].length; col++) {
            int largest = 0;
            int seen = 0;
            for (int row = 0; row < theGrid.length; row++) {
                if (theGrid[row][col] > largest) {
                    seen++;
                    largest = theGrid[row][col];
                }
            }
            if (seen > topTargets[col]) {
                return false;
            }
        }
        return true;
    }
    
    // checks to verify that seen is still less than the target
    private boolean checkBottomTargets() {
        for (int col = 0; col < theGrid[0].length; col++) {
            int largest = 0;
            int seen = 0;
            for (int row = theGrid.length-1; row >= 0; row--) {
                if (theGrid[row][col] > largest) {
                    seen++;
                    largest = theGrid[row][col];
                }
            }
            if (seen > bottomTargets[col]) {
                return false;
            }
        }
        return true;
    }
    
    private boolean noRepeats() {
        for (int row = 0; row < theGrid.length; row++) {
            int[] seen = new int[6];
            for (int col = 0; col < theGrid[row].length; col++) {
                if (theGrid[row][col] != -1) {
		    seen[theGrid[row][col]]++;
                }
            }
            for (int i = 1; i < seen.length; i++) {
                if (seen[i] > 1) {
                    return false;
                }
            }
        }
        for (int col = 0; col < theGrid[0].length; col++) {
            int[] seen = new int[6];
            for (int row = 0; row < theGrid.length; row++) {
                if (theGrid[row][col] != -1) {
		    seen[theGrid[row][col]]++;
                }
            }
            for (int i = 1; i < seen.length; i++) {
                if (seen[i] > 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // builds theGrid for the GUI
    public void buildGrid(Container largeContainer, JPanel centerContainer, JPanel bottomContainer) {	

	int topTarget = 0;
	int bottomTarget = 0;
	int leftTarget = 0;
	int rightTarget = 0;
	for (int row = 0; row < 7; row++) {
	    for (int col = 0; col < 7; col++) {
		if ((row==0 && col==0) || (row==0 && col==6) || (row==6 && col==0) || (row==6 && col==6)) {
		    centerContainer.add(Box.createRigidArea(new Dimension(5,0)));
		} else if (row==0) {
		    JLabel target = new JLabel(Integer.toString(topTargets[topTarget]));
		    target.setHorizontalAlignment(JTextField.CENTER);
		    centerContainer.add(target);
		    topTarget++;
		} else if (col==0) {
		    JLabel target = new JLabel(Integer.toString(leftTargets[leftTarget]));
		    target.setHorizontalAlignment(JTextField.CENTER);
		    centerContainer.add(target);
		    leftTarget++;
		} else if (col==6) {
		    JLabel target = new JLabel(Integer.toString(rightTargets[rightTarget]));
		    target.setHorizontalAlignment(JTextField.CENTER);
		    centerContainer.add(target);
		    rightTarget++;
		} else if (row==6) {
		    JLabel target = new JLabel(Integer.toString(bottomTargets[bottomTarget]));
		    target.setHorizontalAlignment(JTextField.CENTER);
		    centerContainer.add(target);
		    bottomTarget++;
		} else {
		    JButton jb = new SkyscraperButton(this, row-1, col-1);
		    centerContainer.add(jb);
		    buttons[row-1][col-1] = (SkyscraperButton) jb;
		}
	    }
	}

	JButton solve = new TheSolver("Solve",this,theGrid,possibleValues);
	bottomContainer.add(solve);

	largeContainer.add(bottomContainer, BorderLayout.SOUTH);
	largeContainer.add(centerContainer, BorderLayout.CENTER);
    }
}
