import java.awt.*;
import javax.swing.*;

public class MyFrame extends JFrame {

    public void init() {
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	// sets content pane as a border display
	Container largeContainer = getContentPane();
	largeContainer.setLayout(new BorderLayout(50,10));

	//creates smaller jpanels that get added to the content pane
	JPanel centerContainer = new JPanel(new GridLayout(7,7));
	JPanel topContainer = new JPanel(new GridLayout(1,4));
	JPanel leftContainer = new JPanel(new GridLayout(5,1));
	JPanel rightContainer = new JPanel(new GridLayout(5,1));
	JPanel bottomContainer = new JPanel(new FlowLayout());

	JLabel label = new JLabel();
	label.setText("Select a game from above to begin playing");
	largeContainer.add(label, BorderLayout.CENTER);

	PlayButton kakurasu = new PlayButton("Play Kakurasu",this,largeContainer,label,0,centerContainer,bottomContainer);
	topContainer.add(kakurasu);
	
	PlayButton skyscraper = new PlayButton("Play Skyscraper",this,largeContainer,label,1,centerContainer,bottomContainer);
	topContainer.add(skyscraper);

	PlayButton hitori = new PlayButton("Play Hitori",this,largeContainer,label,2,centerContainer,bottomContainer);
	topContainer.add(hitori);

	PlayButton norinori = new PlayButton("Play Norinori",this,largeContainer,label,3,centerContainer,bottomContainer);
	topContainer.add(norinori);

	largeContainer.add(topContainer, BorderLayout.NORTH);
	largeContainer.add(leftContainer, BorderLayout.WEST);
	largeContainer.add(rightContainer, BorderLayout.EAST);

    }
}