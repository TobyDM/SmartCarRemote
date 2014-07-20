package com.darcimaher.smartcarremote;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import com.darcimaher.smartcarremote.MainWindow.SingleKeyDelegate;

public class SingleKey extends JPanel {
	
	private boolean isPressed = false;

    public boolean isPressed() {
		return isPressed;
	}

	public void setPressed(boolean isPressed) {
		this.isPressed = isPressed;
	}

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
				SingleKey sourceObject;
				sourceObject = (SingleKey) e.getSource();
				sourceObject.setPressed(true);

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
				SingleKey sourceObject;
				sourceObject = (SingleKey) e.getSource();
				sourceObject.setPressed(false);
				
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
