package com.darcimaher.smartcarremote;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

import com.darcimaher.smartcarremote.MainWindow.SingleKeyDelegate;

public class KeyPad extends JPanel implements SingleKeyDelegate {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private SingleKey leftArrowButton;
	private SingleKey rightArrowButton;
	private SingleKey upArrowButton;
	private SingleKey downArrowButton;
	private CarCommand lastCommand = CarCommand.STOP;

	public KeyPad() {

		setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.gridx = 1;
		gbc.gridy = 0;
		this.upArrowButton = new SingleKey(KeyEvent.VK_UP, 0, this);
		add(upArrowButton, gbc);

		gbc.gridy = 2;
		this.downArrowButton = new SingleKey(KeyEvent.VK_DOWN, 0, this);
		add(downArrowButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		this.leftArrowButton = new SingleKey(KeyEvent.VK_LEFT, 0, this);
		add(leftArrowButton, gbc);

		gbc.gridx = 2;
		this.rightArrowButton = new SingleKey(KeyEvent.VK_RIGHT, 0, this);
		add(rightArrowButton, gbc);

	}

	@Override
	public void keyTouched() {

		CarCommand newCommand = this.getCurrentCommand();
		
		if (newCommand != lastCommand){
			this.sendCommand (newCommand);
		}
		
	}
	
	private void sendCommand(CarCommand newCommand) {
		this.lastCommand = newCommand;
		System.out.println(newCommand.name());
	}

	private CarCommand getCurrentCommand() {

		CarCommand newCommand;

		if (this.leftArrowButton.isPressed() && this.upArrowButton.isPressed()) {
			newCommand = CarCommand.TURN_LEFT;
		} else if (this.rightArrowButton.isPressed() && this.upArrowButton.isPressed()) {
			newCommand = CarCommand.TURN_RIGHT;
		} else if (this.leftArrowButton.isPressed() && this.downArrowButton.isPressed()) {
			newCommand = CarCommand.REV_TURN_LEFT;
		} else if (this.rightArrowButton.isPressed() && this.downArrowButton.isPressed()) {
			newCommand = CarCommand.REV_TURN_RIGHT;
		} else if (this.leftArrowButton.isPressed()) {
			newCommand = CarCommand.ROTATE_LEFT;
		} else if (this.rightArrowButton.isPressed()) {
			newCommand = CarCommand.ROTATE_RIGHT;
		} else if (this.upArrowButton.isPressed()) {
			newCommand = CarCommand.FORWARD;
		} else if (this.downArrowButton.isPressed()) {
			newCommand = CarCommand.REVERSE;
		} else {
			newCommand = CarCommand.STOP;
		}
		return newCommand;
	}
}