import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Board extends JPanel {

    public String SquareSelectState = "blank";
    private Square SquareMatrix[][] = new Square[31][31];
    public Boolean wallBuild = false;
    public Boolean mousedPressed = false;
    private Square start;
    private Square dest;
    private Boolean startExists = false;
    private Boolean destExists = false;

    public Board() {
        this.setLayout(new GridLayout(30, 30));
        this.setVisible(true);
        this.DrawBoard();

    }

    //draw the board/grid
    public void DrawBoard() {
        //add the buttons onto the board
        int x = 1;
        int y = 1;
        for (int i = 0; i < 900; i++) {
            //add them to the square matrix
            this.SquareMatrix[x][y] = new Square(x, y, this);
            this.add(this.SquareMatrix[x][y]);

            x += 1;
            //update their next coordinates
            if (x == 31) {
                x = 1;
                y += 1;
            }
        }
    }

    //change the state of a square
    public void SquareStateChange(Point location) {
        int x = location.x;
        int y = location.y;
        Square square = this.SquareMatrix[x][y];

        switch (this.SquareSelectState) {
            case "blank":
                return;
            case "start/dest":
                if (this.startExists == false) {
                    this.start = square;
                    this.startExists = true;
                    square.setBackground(Color.green);
                    square.ChangeState("start");
                } else {
                    if (this.destExists == false) {
                        this.dest = square;
                        this.destExists = true;
                        square.setBackground(Color.red);
                        square.ChangeState("dest");
                    } else {
                        this.dest.ChangeState("blank");
                        this.dest.setBackground(Color.white);

                        this.dest = square;
                        this.destExists = true;
                        square.setBackground(Color.red);
                        square.ChangeState("dest");
                    }
                }
                break;
            case "wall":
                square.setBackground(Color.black);
                square.ChangeState("wall");
                break;
            default:
                break;

        }

    }

}




