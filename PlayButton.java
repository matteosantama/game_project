import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlayButton extends JButton implements ActionListener {

    private MyFrame frame;
    private Container largeContainer;
    private JLabel label;
    private TheGame g;
    private int gameCode;
    private JPanel centerContainer;
    private JPanel bottomContainer;

    public PlayButton(String text, MyFrame f, Container lc, JLabel l, int game, JPanel center, JPanel bc) {
	super(text);
	frame = f;
	largeContainer = lc;
	label = l;
	gameCode = game;
	centerContainer = center;
	bottomContainer = bc;
	addActionListener(this);
    }

    public void actionPerformed(ActionEvent e) {
	//removes all components to allow for components of new game
	bottomContainer.removeAll();
	largeContainer.remove(label);
	centerContainer.removeAll();

	// creates a new instance of TheGame depending on the gameCode
	if (gameCode == 0) {
	    TheGame g = new Kakurasu(frame);
	    g.buildGrid(largeContainer,centerContainer, bottomContainer);
	} else if (gameCode == 1) {
	    TheGame g = new Skyscraper(frame);
	    g.buildGrid(largeContainer,centerContainer,bottomContainer);
	} else if (gameCode == 2) {
	    TheGame g = new Hitori(frame);
	    g.buildGrid(largeContainer,centerContainer,bottomContainer);
	} else if (gameCode == 3) {
	    TheGame g = new Norinori(frame);
	    g.buildGrid(largeContainer,centerContainer,bottomContainer);
	}

	frame.validate();
	frame.repaint();
    }

}
    