import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame {
    private JPanel rootPanel;
    private JButton resetButton;
    private JButton dikjstraButton;
    private JPanel boardPanel;
    private JPanel BoardPanel;

    public GUI() {
        add(rootPanel);
        this.setTitle("Path Finding Demo");
        this.setSize(800, 900);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);

        Board board = new Board();

        boardPanel.setLayout(new GridLayout(1, 1));
        boardPanel.add(board);

        //Action listener for reset button
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new GUI();
            }
        });


    }
}
