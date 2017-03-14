import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SkyscraperButton extends JButton implements ActionListener {
   
    private int timesPushed = 0;
    private int row;
    private int col;
    private Skyscraper s;

    public SkyscraperButton(Skyscraper sky, int r, int c) {
	s = sky;
	row = r;
	col = c;
	addActionListener(this);
    }

    // sets grid element, updates text displayed by the button, and checks if
    // the puzzle is solved
    public void actionPerformed(ActionEvent e) {
	s.setElement(row, col);
	if (s.getElement(row,col) == -1) {
	    this.setText("");
	} else if (s.getElement(row,col) > 0) {
	    this.setText(Integer.toString(s.getElement(row,col)));
	}
	if (s.isSolved()) {
	    JOptionPane.showMessageDialog(null, "Congrats! You solved the puzzle!");
	}
    }

    // updates GUI of the button when the solve button is used
    public void set() {
	if (s.getElement(row,col) == -1) {
	    this.setText("");
	} else if (s.getElement(row,col) > 0) {
	    this.setText(Integer.toString(s.getElement(row,col)));
	}
    }
}