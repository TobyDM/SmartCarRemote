package com.darcimaher.smartcarremote;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

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

    public class KeyPad extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public KeyPad() {

            setLayout(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.gridx = 1;
            gbc.gridy = 0;
            SingleKeyDelegate myDelegate = new SingleKeyDelegate() {
				
				@Override
				public void keyTouched() {
					System.out.println("SOMEBODY TOUCHED A BUTTON");
				}
			};

            add(new SingleKey(KeyEvent.VK_UP, 0, myDelegate), gbc);

            gbc.gridy = 2;
            add(new SingleKey(KeyEvent.VK_DOWN, 0, myDelegate), gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            add(new SingleKey(KeyEvent.VK_LEFT, 0, myDelegate), gbc);

            gbc.gridx = 2;
            add(new SingleKey(KeyEvent.VK_RIGHT, 0, myDelegate), gbc);

        }
    }

    public interface SingleKeyDelegate {
    	public void keyTouched();
    }
    
    public class SingleKey extends JPanel {

        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private final SingleKeyDelegate keyDelegate;

		public SingleKey(int keyCode, int modifier, SingleKeyDelegate thingie) {

			this.keyDelegate = thingie;
            setBorder(new LineBorder(Color.BLACK));

            InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
            ActionMap am = getActionMap();

            im.put(KeyStroke.getKeyStroke(keyCode, modifier, false), "keyPressed");
            im.put(KeyStroke.getKeyStroke(keyCode, modifier, true), "keyReleased");

            am.put("keyPressed", new AbstractAction() {
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
                public void actionPerformed(ActionEvent e) {
                    setBackground(Color.RED);
                    // tell our parent that this happened
                    keyDelegate.keyTouched();
                }
            });

            am.put("keyReleased", new AbstractAction() {
                /**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
                public void actionPerformed(ActionEvent e) {
                    setBackground(UIManager.getColor("Panel.background"));
                    // tell our parent that this happened
                    keyDelegate.keyTouched();
                }
            });

        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(100, 100);
        }
    }
    
}
