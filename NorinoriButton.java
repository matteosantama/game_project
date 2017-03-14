import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

public class NorinoriButton extends JButton implements ActionListener {

    private Norinori n;
    private int row;
    private int col;
    private ImageIcon check = new ImageIcon("checkmark.png");

    public NorinoriButton(Norinori nori, int r, int c, Color color) {
    	n = nori;
    	row = r;
    	col = c;
    	this.setBackground(color);
        this.setOpaque(true);
        this.setBorderPainted(false);
    	addActionListener(this);
    }

    // sets element in theGrid, displays icon in GUI, and checks if
    // puzzle is solved
    public void actionPerformed(ActionEvent e) {
	n.setElement(row, col);
	if (n.isSelected(row,col))
	    this.setIcon(check);
	else
	    this.setIcon(null);
	if (n.isSolved()) {
	    JOptionPane.showMessageDialog(null, "Congrats! You solved the puzzle!");
	}
    }

    // called when solve button is used to display solution
    public void set() {
	if (n.isSelected(row,col)) {
	    this.setIcon(check);
	} else {
	    this.setIcon(null);
	}
    }
}
