import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;


public class KakurasuButton extends JButton implements ActionListener {

    private Kakurasu k;
    private MyFrame frame;
    private int row;
    private int col;
    private ImageIcon check = new ImageIcon("xmark.png");

    public KakurasuButton(Kakurasu kak, int r, int c, MyFrame f) {
	k = kak;
	row = r;
	col = c;
	frame = frame;
	addActionListener(this);
    }

    // sets element in theGrid, changes GUI of the button, and checks is the 
    // puzzle is solved
    public void actionPerformed(ActionEvent e) {
	k.setElement(row, col);
	if (k.isSelected(row, col)) {
	  this.setIcon(check);
	  k.addToSum(row, col);
	} else {
	  this.setIcon(null);
	  k.subtractFromSum(row, col);
	}
	if (k.isSolved()) {
	    JOptionPane.showMessageDialog(null, "Congrats! You solved the puzzle!");   
	}
    }
    
    // called when solve button is used to change GUI of button
    public void set() {
	if (k.isSelected(row,col)) {
	    this.setIcon(check);
	    k.addToSum(row,col);
	} else {
	    this.setIcon(null);
	    k.subtractFromSum(row, col);
	}
    }
}