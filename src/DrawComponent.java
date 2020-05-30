import javax.swing.*;
import java.awt.*;

public class DrawComponent extends JComponent {
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        Rectangle rect1 = new Rectangle(5,5, 100, 100);
        g2.draw(rect1);
    }
}
