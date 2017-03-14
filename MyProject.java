import java.awt.*;

public class MyProject {
    
    public static void main(String[] args) {
	
	MyFrame frame = new MyFrame();
	frame.init();
	frame.setPreferredSize(new Dimension(840,600));
	
	frame.pack();
	frame.setVisible(true);
	frame.repaint();

    }
}