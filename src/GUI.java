import javax.swing.*;
import com.formdev.flatlaf.*;

public class GUI {
    JFrame frame;
    JButton themeButton;
    JPanel themePanel;
    boolean isDark;

    GUI() {
        initialise();
        show();
    }

    private void initialise() {
        FlatLightLaf.setup();
        isDark = false;
        frame = new JFrame("Sport Broadcast");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        themeButton = new JButton("Toggle Theme");
        themeButton.setFocusable(false);
        themeButton.addActionListener(e -> toggleTheme(isDark));
        themePanel = new JPanel();
        themePanel.add(themeButton);
        frame.add(themePanel);
    }

    private void show() {
        frame.setVisible(true);
    } 

    private void toggleTheme(boolean isDark) {
        if (isDark) {
            FlatLightLaf.setup();
            this.isDark = false;
        }
        else {
            FlatDarkLaf.setup();
            this.isDark = true;
        }
        FlatLaf.updateUI();
    }
 
    
}
