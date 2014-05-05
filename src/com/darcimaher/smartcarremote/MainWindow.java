package com.darcimaher.smartcarremote;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MainWindow {

	private JFrame frame;
	private JPanel textPanel;
	private JTextArea console;
	private JTextArea keyArea;
	private JScrollPane pScroll;

	public MainWindow() {
		this.createWindow();
		this.connectToCar();
	}

	private void createWindow() {

		System.out.println("Creating window...");

		// make the window itself
		frame = new JFrame("Car Remote");
		frame.setSize(624, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

		// make a panel to hold console and key area
		textPanel = new JPanel(new GridLayout(1, 2));

		// make console
		console = new JTextArea();
		console.setBackground(Color.CYAN);
		pScroll = new JScrollPane(console,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		pScroll.setPreferredSize(new Dimension(500, 250));
		textPanel.add(pScroll);

		// make area for listening to keys
		keyArea = new JTextArea();
		keyArea.setBackground(Color.YELLOW);
		keyArea.setPreferredSize(new Dimension(100, 100));
		keyArea.addKeyListener(new MainWindowKeyListener());// add key listener
															// to listen
		// to key events such as
		// press, etc
		textPanel.add(keyArea);

		// add textPanel and buttonPanel to the window
		frame.getContentPane().add(textPanel, BorderLayout.CENTER);
		textPanel.revalidate();
	}

	private void connectToCar() {
		System.out.println("Connecting to car...");
	}

	class MainWindowKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			try {
				if (e.getKeyCode() == 37)// left arrow keycode, tell NXT to
											// turn right
				{
					console.append("Left command sent.\n");
//					dos.writeInt(e.getKeyCode());
//					dos.flush();
				} else if (e.getKeyCode() == 38)// up arrow keycode, tell NXT to
												// go forward
				{
					console.append("Forward command sent.\n");
//					dos.writeInt(e.getKeyCode());
//					dos.flush();
				} else if (e.getKeyCode() == 39)// left arrow keycode, tell NXT
												// to turn left
				{
					console.append("Right command sent.\n");
//					dos.writeInt(e.getKeyCode());
//					dos.flush();
				} else if (e.getKeyCode() == 40)// down arrow keycode, tell NXT
												// to go backwards
				{
					console.append("Backwards command sent.\n");
//					dos.writeInt(e.getKeyCode());
//					dos.flush();
				} else {
					console.append("Unrecognized command.\n");
				}// some other key pressed, not any of the four arrow keys, warn
					// that the command is unrecognized
			}

			catch (Exception E) {
				console.append("Could not send command\n");
			}// catch exception, save face

		}

		@Override
		public void keyReleased(KeyEvent e) {
			try {
//				dos.writeInt(-2);// NXT recognises -2 as command to stop
//									// whatever it is doing whether it is
//									// turning left, right or going forwards or
//									// backwards.
//				dos.flush();
			} catch (Exception E) {
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
		}

	}

}
