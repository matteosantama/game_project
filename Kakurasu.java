import java.awt.*;
import javax.swing.*;
import java.util.Scanner;
import java.io.*;

public class Kakurasu extends TheGame {

    private int[][] theGrid = new int[5][5];
    private int[] rowTargets;
    private int[] colTargets;
    private int[][] runningSums = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
    private KakurasuButton[][] buttons = new KakurasuButton[5][5];
    private Scanner infile;
    private int[] possibleValues = {0,1};
    private MyFrame frame;


    public Kakurasu(MyFrame f) {
	frame = f;
	infile = openFile("KakurasuBoard.txt");
	this.resetGrid();
    }

    // catches FileNotFoundException
    private Scanner openFile(String s) {
	try {
	    Scanner firstRead = new Scanner(new java.io.FileReader(s));
	    int count = getCount(firstRead);
	    Scanner secondRead = new Scanner(new java.io.FileReader(s));
	    assignTargets(secondRead, count);
	    return secondRead;
	}
	catch (java.io.FileNotFoundException e) {
	    System.out.println("File not found. Quitting program.");
	    System.exit(1);
	    return null;
	}
    }

    // determines how long the file is, and then closes the file
    private int getCount(Scanner file) {
	int count = 0;
	while (file.hasNextInt()) {
	    count++;
	    file.next();
	}
	file.close();
	return count;
    }

    // assigns values from the file to the target arrays
    private void assignTargets(Scanner file, int count) {
	rowTargets= new int[count/2];
	colTargets = new int[count/2];
	for (int rT = 0; rT < rowTargets.length; rT++) {
	    if (file.hasNextInt())
		rowTargets[rT] = file.nextInt();
	}
	for (int cT = 0; cT < colTargets.length; cT++) {
	    if (file.hasNextInt()) 
		colTargets[cT] = file.nextInt();
	}
	file.close();
    }
        
    //initializes grid values to -1
    public void resetGrid() {
	for (int row = 0; row < theGrid.length; row++) {
	    for (int col = 0; col < theGrid[row].length; col++) {
		theGrid[row][col] = -1;
	    }
	}
    }

    // called when solver button is used to display solution
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
    
    //checks constraints
    public boolean isSolved() {
	for (int i = 0; i < 2; i++) {
	    for (int j = 0; j < runningSums[i].length; j++) {
		if (i == 0 && runningSums[0][j] != rowTargets[j])
		    return false;
		else if (i == 1 && runningSums[1][j] != colTargets[j])
		    return false;
	    }
	}
	return true;
    }

    public void addToSum(int row, int col) {
	runningSums[0][row] += col+1;
	runningSums[1][col] += row+1;
    }

    public void subtractFromSum(int row, int col) {
	runningSums[0][row] -= col+1;
	runningSums[1][col] -= row+1;
    }
    
    private boolean checkRows() {
	boolean correct = true;
	for (int row = 0; row < theGrid.length; row++) {
	    int sum = 0;
	    for (int col = 0; col < theGrid[row].length; col++) {
		if (isSelected(row, col)) {
		    sum = sum + col + 1;
		}
	    }
	    if (sum > rowTargets[row])
		correct = false;
	}
	return correct;
    }
    
    private boolean checkCols() {	
	boolean correct = true;
	for (int col = 0; col < theGrid[0].length; col++) {
	    int sum = 0;
	    for (int row = 0; row < theGrid.length; row++) {
		if (isSelected(row, col)) {
		    sum = sum + row + 1;
		}
		}
	    if (sum > colTargets[col]) 
		correct = false;
	}
	return correct;
    }
    
    
    public boolean check() {
	return checkRows() && checkCols();
    }

    //checks all constraints
    public boolean fullCheck() {
	for (int row = 0; row < theGrid.length; row++) {
	    int sum = 0;
	    for (int col = 0; col < theGrid[row].length; col++) {
		if (isSelected(row, col)) {
		    sum = sum + col + 1;
		}
	    }
	    if (sum != rowTargets[row])
		return false;
	}
	for (int col = 0; col < theGrid[0].length; col++) {
	    int sum = 0;
	    for (int row = 0; row < theGrid.length; row++) {
		if (isSelected(row, col)) {
		    sum = sum + row + 1;
		}
		}
	    if (sum != colTargets[col]) 
		return false;
	}
	return true;
    }

    // creates game by adding buttons to the GUI
    public void buildGrid(Container largeContainer, JPanel centerContainer, JPanel bottomContainer) {
	int colNum = 1;
	int rowNum = 1;
	int rowTarget = 0;
	int colTarget = 0;
	for (int row = 0; row < 7; row++) {
	    for (int col = 0; col < 7; col++) {
		if ((row==0 && col==0) || (row==0 && col==6) || (row==6 && col==0) || (row==6 && col==6)) {
		    centerContainer.add(Box.createRigidArea(new Dimension(5,0)));
		} else if (row==0) {
		    JLabel target = new JLabel(Integer.toString(rowNum));
		    target.setHorizontalAlignment(JTextField.CENTER);
		    centerContainer.add(target);
		    rowNum++;
		} else if (col==0) {
		    JLabel target = new JLabel(Integer.toString(colNum));
		    target.setHorizontalAlignment(JTextField.CENTER);
		    centerContainer.add(target);
		    colNum++;
		} else if (col==6) {
		    JLabel target = new JLabel(Integer.toString(rowTargets[rowTarget]));
		    target.setHorizontalAlignment(JTextField.CENTER);
		    centerContainer.add(target);
		    rowTarget++;
		} else if (row==6) {
		    JLabel target = new JLabel(Integer.toString(colTargets[colTarget]));
		    target.setHorizontalAlignment(JTextField.CENTER);
		    centerContainer.add(target);
		    colTarget++;
		} else {
		    JButton jb = new KakurasuButton(this, row-1, col-1,frame);
		    centerContainer.add(jb);
		    buttons[row-1][col-1] = (KakurasuButton) jb;
		}
	    }
	}				       

	JButton solve = new TheSolver("Solve",this,theGrid,possibleValues);
	bottomContainer.add(solve);
	
	largeContainer.add(bottomContainer, BorderLayout.SOUTH);
	largeContainer.add(centerContainer, BorderLayout.CENTER);
    }

}