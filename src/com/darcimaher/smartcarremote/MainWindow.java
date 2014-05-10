package com.darcimaher.smartcarremote;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class MainWindow {

    public MainWindow() {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (ClassNotFoundException  ex) {
                }
                catch ( InstantiationException ex) {
                }
                catch (IllegalAccessException  ex) {
                }
                catch ( UnsupportedLookAndFeelException ex) {
                }

                JFrame frame = new JFrame("Smart Car Remote");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
                frame.setLayout(new BorderLayout());
                frame.add(new KeyPad());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }

    public interface SingleKeyDelegate {
    	public void keyTouched();
    }
 
}
