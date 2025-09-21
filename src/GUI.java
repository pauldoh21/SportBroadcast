import javax.swing.*;
import com.formdev.flatlaf.*;

public class GUI {
    JFrame frame;

    GUI() {
        FlatDarkLaf.setup();
        frame = new JFrame("Sport Broadcast");
        show();
    }

    void show() {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    
}
