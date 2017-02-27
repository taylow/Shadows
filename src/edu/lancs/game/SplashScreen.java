package edu.lancs.game;

import javax.swing.*;
import java.awt.*;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreen {
    private JWindow window;

    public SplashScreen() {
        window = new JWindow();
        try {
            ImageIcon icon = new ImageIcon("resources/misc/misc/loading.gif");
            window.getContentPane().add(new JLabel("", icon, SwingConstants.CENTER));
            window.setBounds(0, 0, 600, 400);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        window.dispose();
    }
}