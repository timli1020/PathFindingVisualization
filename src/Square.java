import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Square extends JLabel {
    //states: blank, wall, start, end
    private String state;
    private Color color;
    private Point location;
    private Board parentBoard;

    public Square(int x, int y, Board _parentBaord) {
        this.state = "blank";
        this.color = Color.white;
        this.location = new Point(x, y);
        this.parentBoard = _parentBaord;

        this.setBackground(this.color);
        this.setOpaque(true);
        Border border = BorderFactory.createLineBorder(Color.black,1);
        this.setBorder(border);


        //listener for when a square is clicked on
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ActivateSquare();
            }
        });

        //listener for when a square is pressed on and held
        //mainly for building walls
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                StartWallBuild();
            }
        });

        //listener for when a square is entered by mouse
        //mainly for building walls
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (parentBoard.mousedPressed) {
                    MouseEnteredSquare();
                }
            }
        });

        //listener for when a mouse is released on a square
        //mainly for building walls
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                EndWallBuild();
            }
        });

    }

    private void StartWallBuild() {
        //start wall building
        if (!this.parentBoard.SquareSelectState.equals("wall")){
            return;
        }
        this.ActivateSquare();
        this.parentBoard.mousedPressed = true;
    }

    private void EndWallBuild() {
        this.parentBoard.wallBuild = false;
        this.parentBoard.mousedPressed = false;
    }

    private void MouseEnteredSquare() {
        this.ActivateSquare();
    }

    private void ActivateSquare() {
        System.out.println(this.location.getLocation());
        this.parentBoard.SquareStateChange(this.location);
    }

    public void ChangeState(String _state) {
        this.state = _state;
    }
}
