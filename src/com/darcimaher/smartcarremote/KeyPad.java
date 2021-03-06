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
	private SingleKey replayButton;
	private SingleKey clearButton;

	private CarCommand lastCommand = CarCommand.STOP;
	private USBCom usbCommunicator = USBCom.getInstance();

	public KeyPad() {

		setLayout(new GridBagLayout());
		GridBagConstraints gbc;

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 0;
		this.upArrowButton = new SingleKey(KeyEvent.VK_UP, 0, this);
		add(upArrowButton, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 1;
		gbc.gridy = 2;
		this.downArrowButton = new SingleKey(KeyEvent.VK_DOWN, 0, this);
		add(downArrowButton, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 1;
		this.leftArrowButton = new SingleKey(KeyEvent.VK_LEFT, 0, this);
		add(leftArrowButton, gbc);

		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 1;
		this.rightArrowButton = new SingleKey(KeyEvent.VK_RIGHT, 0, this);
		add(rightArrowButton, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 2;
		this.replayButton = new SingleKey(KeyEvent.VK_SPACE, 0, this);
		add(replayButton, gbc);
		
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.gridy = 2;
		this.clearButton = new SingleKey(KeyEvent.VK_BACK_SPACE, 0, this);
		add(clearButton, gbc);

		// connect
//		this.connectToCar();
		this.connectToRelay();

	}
	
	private void connectToRelay(){
		usbCommunicator.connect();
	}

//	private void connectToCar() {
//
//		NXTConnector conn = new NXTConnector();// create a new NXT connector
//		conn.setDebug(true);
//		boolean connected = conn.connectTo(); // try to connect to any
//														// NXT over bluetooth
//
//		if (!connected) {// failure
//			System.out.println("Failed to connect to any NXT");
//		}
//
//		else// success!
//		{
//			System.out.println("Connected to " + conn.getNXTInfo());
//			outputStream = conn.getOutputStream();
//			inputStream = conn.getInputStream();
//		}
//
//	}

	@Override
	public void keyTouched() {

		CarCommand newCommand = this.getCurrentCommand();

		if ((newCommand != null) && (newCommand != lastCommand)) {
			this.sendCommand(newCommand);
		}

	}

	private void sendCommand(CarCommand newCommand) {
		this.lastCommand = newCommand;
		System.out.println(newCommand.name());
		usbCommunicator.sendInt(newCommand.ordinal());
	}

	private CarCommand getCurrentCommand() {

		CarCommand newCommand;

		if (this.leftArrowButton.isPressed() && this.upArrowButton.isPressed()) {
			newCommand = CarCommand.TURN_LEFT;
		} else if (this.rightArrowButton.isPressed()
				&& this.upArrowButton.isPressed()) {
			newCommand = CarCommand.TURN_RIGHT;
		} else if (this.leftArrowButton.isPressed()
				&& this.downArrowButton.isPressed()) {
			newCommand = CarCommand.REV_TURN_LEFT;
		} else if (this.rightArrowButton.isPressed()
				&& this.downArrowButton.isPressed()) {
			newCommand = CarCommand.REV_TURN_RIGHT;
		} else if (this.leftArrowButton.isPressed()) {
			newCommand = CarCommand.ROTATE_LEFT;
		} else if (this.rightArrowButton.isPressed()) {
			newCommand = CarCommand.ROTATE_RIGHT;
		} else if (this.upArrowButton.isPressed()) {
			newCommand = CarCommand.FORWARD;
		} else if (this.downArrowButton.isPressed()) {
			newCommand = CarCommand.REVERSE;
		} else if (this.clearButton.isPressed()) {
			newCommand = CarCommand.CLEAR;
		} else if (this.replayButton.isPressed()) {
			newCommand = CarCommand.REPLAY;
		} else if (this.lastCommand == CarCommand.REPLAY || this.lastCommand == CarCommand.CLEAR){
			newCommand = null;
		} else {
			newCommand = CarCommand.STOP;
		}
		return newCommand;
	}
}