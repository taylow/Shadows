package edu.lancs.game;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

public class SplashScreen {
    private JWindow window;

    public SplashScreen() {
        window = new JWindow();
        try {
            //TODO: Could definitely be improved. Change of image maybe? I don't know. This one looks indie though
            window.getContentPane().add(new JLabel("", new ImageIcon(new URL("https://im.ezgif.com/tmp/ezgif-1-a7e3c408d8.gif")), SwingConstants.CENTER));
            window.setBounds(0, 0, 800, 600);
            window.setLocationRelativeTo(null);
            window.setVisible(true);
            window.setAlwaysOnTop(true);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void dispose() {
        window.dispose();
    }
}