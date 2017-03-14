import java.awt.*;
import javax.swing.*;


public abstract class TheGame {

    public TheGame() {}

    public abstract void buildGrid(Container lc, JPanel cc, JPanel bc);
    
    public abstract void resetGrid();

    public abstract boolean check();

    public abstract boolean isSolved();

    public abstract boolean fullCheck();

    public abstract void setElement(int row, int col);

    public abstract void fillGrid();

}