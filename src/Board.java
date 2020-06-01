import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class Board extends JPanel {

    public State SquareSelectState;
    private final int SquareCount = 9;
    private final int colCount;
    private final Square[][] SquareMatrix;
    public boolean wallBuild = false;
    public boolean mousedPressed = false;
    private Square dest;
    private boolean startExists = false;
    private boolean destExists = false;
    private final Graph graph;

    enum State {
        BLANK,
        STARTDEST,
        WALL
    }

    public Board() {
        //set up the board
        this.SquareSelectState = State.BLANK;
        this.colCount = (int) Math.sqrt(this.SquareCount);
        this.SquareMatrix= new Square[this.colCount + 1][this.colCount + 1];
        //init a new graph
        this.graph = new Graph(this.colCount * this.colCount);
        this.setLayout(new GridLayout(this.colCount, this.colCount));
        this.setVisible(true);
        this.DrawBoard();


    }

    //draw the board/grid
    public void DrawBoard() {
        //add the squares onto the board
        int x = 0;
        int y = 0;
        for (int i = 0; i < this.SquareCount; i++) {
            //create a new square and add it the SquareMatrix
            Square square = new Square(x, y, this);
            this.SquareMatrix[x][y] = square;
            //put this square into a hash table to keep track of its position in the SquareMatrix
            this.graph.SquareHash.put(square, i);

            //attach edges to neighboring squares
            this.ConnectSquareHelper(square, x, y);

            //add the square into the SquareMatrix
            this.add(this.SquareMatrix[x][y]);

            x += 1;
            //update their next coordinates
            if (x == this.colCount) {
                x = 0;
                y += 1;
            }
        }

        System.out.println(this.graph.toString());
    }

    //Helper function to create edges to neighbor squares
    public void ConnectSquareHelper(Square square, int x, int y) {
        //add vertical and horizontal edges
        try {
            this.ConnectSquare(square,x - 1, y, 1);
        } catch (Exception e) {
            //nothing
        }

        try {
            this.ConnectSquare(square, x + 1, y, 1);
        } catch (Exception e) {
            //nothing
        }

        try {
            this.ConnectSquare(square, x, y - 1, 1);
        } catch (Exception e) {
            //nothing
        }

        try {
            this.ConnectSquare(square, x, y + 1, 1);
        } catch (Exception e) {
            //nothing
        }

        //add horizontal edges
        try {
            this.ConnectSquare(square,x - 1, y - 1, 1.4);
        } catch (Exception e) {
            //nothing
        }

        try {
            this.ConnectSquare(square, x - 1, y + 1, 1.4);
        } catch (Exception e) {
            //nothing
        }

        try {
            this.ConnectSquare(square, x + 1, y + 1, 1.4);
        } catch (Exception e) {
            //nothing
        }

        try {
            this.ConnectSquare(square, x + 1, y - 1, 1.4);
        } catch (Exception e) {
            //nothing
        }
    }

    //Create an edge between a square and one of its neighbors
    public void ConnectSquare(Square currSquare, int a, int b, double weight){
        int currSquarePos = this.graph.SquareHash.get(currSquare);
        Square targetSquare = this.SquareMatrix[a][b];
        int targetSquarePos = this.graph.SquareHash.get(targetSquare);
        this.graph.addEdge(currSquarePos, targetSquarePos, weight);
    }

    //change the state of a square
    public void SquareStateChange(Point location) {
        int x = location.x;
        int y = location.y;
        Square square = this.SquareMatrix[x][y];

        switch (this.SquareSelectState) {
            case BLANK:
                return;
            case STARTDEST:
                if (!this.startExists) {
                    this.startExists = true;
                    square.setBackground(Color.green);
                    square.ChangeState(Square.SquareState.START);
                } else {
                    if (!this.destExists) {
                        this.dest = square;
                        this.destExists = true;
                        square.setBackground(Color.red);
                        square.ChangeState(Square.SquareState.DEST);
                    } else {
                        this.dest.ChangeState(Square.SquareState.BlANK);
                        this.dest.setBackground(Color.white);

                        this.dest = square;
                        this.destExists = true;
                        square.setBackground(Color.red);
                        square.ChangeState(Square.SquareState.DEST);
                    }
                }
                break;
            case WALL:
                square.setBackground(Color.black);
                square.ChangeState(Square.SquareState.WALL);
                break;
            default:
                break;

        }

    }

}




