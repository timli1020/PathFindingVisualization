import java.awt.*;
import javax.swing.*;

public class Board extends JPanel {

    private Square SquareList[] = new Square[400];

    public Board() {
        this.setLayout(new GridLayout(20, 20));
        this.setVisible(true);
        this.DrawBoard();
    }

    public void DrawBoard() {
        //add the buttons onto the board
        int x = 1;
        int y = 1;
        for (int i = 0; i < 400; i++) {
            //add them to the square list
            this.SquareList[i] = new Square(x, y);
            x += 1;

            //update their next coordinates
            if (x == 21) {
                x = 1;
                y += 1;
            }

            this.add(SquareList[i]);
        }
    }
}




