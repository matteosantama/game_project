import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.Color;

public class HitoriButton extends JButton implements ActionListener {

    private Hitori h;
    private Color gray = Color.lightGray;
    private int row;
    private int col;

    public HitoriButton(String text, Hitori hit, int r, int c) {
	super(text);
	h = hit;
	row = r;
	col = c;
	addActionListener(this);
    }

    //sets value in theGrid, changes GUI of button, and checks to see if
    //puzzle is solved
    public void actionPerformed(ActionEvent e) {
	h.setElement(row, col);
	if (h.isSelected(row,col)) 
	    this.setBackground(gray);
	else 
	    this.setBackground(new JButton().getBackground());
	if (h.isSolved()) {
	    JOptionPane.showMessageDialog(null, "Congrats! You solved the puzzle!");
	}
    }

    //called when "solve" button is used to change the GUI for each button
    public void set() {
	if (h.isSelected(row,col)) {
	    this.setBackground(gray);
	} else {
	    this.setBackground(new JButton().getBackground());
	}
    }

}