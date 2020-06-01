import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JPanel rootPanel;
    private JButton resetButton;
    private JButton dikjstraButton;
    private JPanel boardPanel;
    private JButton startDestinationButton;
    private JButton wallButton;
    private Board board;

    public GUI() {
        add(rootPanel);
        this.setTitle("Path Finding Demo");
        this.setSize(900, 1000);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.board = new Board();

        boardPanel.setLayout(new GridLayout(1, 1));
        boardPanel.add(this.board);

        //Action listener for reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResetBoard();
            }
        });

        //Action listener for Start/Destination button
        startDestinationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StartDestButton();
            }
        });

        //Action listener for Wall button
        wallButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WallButton();
            }
        });

        //Action listener for Dijkstra Button
        dikjstraButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PerformDijkstra();
            }
        });
    }

    private void PerformDijkstra() {
        this.board.graph.dijkstra_GetMinDistances(0);
    }

    //reset the board
    private void ResetBoard() {
        new GUI();
        dispose();
    }

    //place start and dest cells
    private void StartDestButton() {
        this.board.SquareSelectState = Board.State.STARTDEST;
        this.board.wallBuild = false;
    }

    //place wall cells
    private void WallButton() {
        this.board.SquareSelectState = Board.State.WALL;
        this.board.wallBuild = true;
    }

}
