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
    }

    //reset the board
    private void ResetBoard() {
        new GUI();
        dispose();
    }

    //place start and dest cells
    private void StartDestButton() {
        this.board.SquareSelectState = "start/dest";
        this.board.wallBuild = false;
    }

    //place wall cells
    private void WallButton() {
        this.board.SquareSelectState = "wall";
        this.board.wallBuild = true;
    }

}
