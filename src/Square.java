import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Square extends JLabel {
    private String state;
    private Color color;
    private Point location;

    public Square(int x, int y) {
        this.state = "blank";
        this.color = Color.white;
        this.location = new Point(x, y);
        this.setBackground(this.color);
        this.setOpaque(true);
        Border border = BorderFactory.createLineBorder(Color.black,1);
        this.setBorder(border);


        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                myListenerCode();
            }
        });

    }

    private void myListenerCode() {
        System.out.println(this.location.getLocation());
        this.setBackground(Color.green);
    }
}
