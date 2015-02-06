package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {

	private static OI m_instance;

	private final Joystick m_leftStick;
	private final Joystick m_rightStick;
	private final Joystick m_operatorStick; // TODO add operator controls

	// private final Arm m_arm;
	private final DriveBase m_drivebase;
	private final Elevator m_elevator;
	
	private boolean m_canGoTo = true;
	private double m_potValue = Constants.ELEVATOR_BOTTOM_VALUE;
	
	public OI() {
		m_leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
		m_rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
		m_operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT);

		m_drivebase = DriveBase.getInstance();
		m_elevator = Elevator.getInstance();
		// m_arm = Arm.getInstance();

	}

	public static OI getInstance() {
		if (m_instance == null) {
			m_instance = new OI();
		}
		return m_instance;
	}

	public double getLeftY() {
		double yval = m_leftStick.getY();
		if (yval > -Constants.JOYSTICK_DEADBAND
				&& yval < Constants.JOYSTICK_DEADBAND) {
			return 0.0;
		} else {
			return yval;
		}
	}

	public double getRightY() {
		double yval = m_rightStick.getY();
		if (yval > -Constants.JOYSTICK_DEADBAND
				&& yval < Constants.JOYSTICK_DEADBAND) {
			return 0.0;
		} else {
			return yval;
		}
	}

	/*
	 * UNUSED! public double getOperatorY() { double yval =
	 * m_operatorStick.getY(); if (yval > -Constants.JOYSTICK_DEADBAND && yval <
	 * Constants.JOYSTICK_DEADBAND) { return 0.0; } else { return yval; } }
	 */

	public void enableDriveControls() {
		m_drivebase.setSpeed(getLeftY(), getRightY());
	}
	
	public void enableTeleopControls() {
		
		enableDriveControls();
		
		if (m_operatorStick.getTrigger()) {
			m_drivebase.resetEncoders();
		}
		
		System.out.println("String Pot Value: " + m_elevator.getPotValue() + "\n");
		
		m_canGoTo = true;
		
		if(getRightButton(2) || getRightButton(3) || getRightButton(5)) {
			m_canGoTo = false;
		} else if(getLeftTrigger() && getLeftButton(2)) {
			m_canGoTo = false;
		}
		
		//"goto" methods
		if(m_canGoTo) {
			if(getLeftTrigger()) {
				gotoPotValue(Constants.ELEVATOR_LOWER_LIMIT);
				return;
			} else if(getRightButton(2)) {
				gotoPotValue(Constants.ELEVATOR_UPPER_LIMIT);
				return;
			}
		}
		
		//manually raise and lower
		if(getRightButton(3)) {
			m_potValue += Constants.ELEVATOR_INCREMENT;
		}
		if(getRightButton(2)) {
			m_potValue -= Constants.ELEVATOR_INCREMENT;
		}
		
		//limits
		if(m_potValue < Constants.ELEVATOR_LOWER_LIMIT) {
			m_potValue = Constants.ELEVATOR_LOWER_LIMIT;
		}
		if(m_potValue > Constants.ELEVATOR_UPPER_LIMIT) {
			m_potValue = Constants.ELEVATOR_UPPER_LIMIT;
		}
		
		m_elevator.setSpeedByPotValue(m_potValue); 
		
	}

	public void gotoPotValue(double value) {
		while(!m_elevator.potValueWithinRange(value)) {
			if(getRightButton(5)) {
				return;
			}
			enableDriveControls();
			m_elevator.setSpeedByPotValue(value);
		}
	}
	
	public boolean getOperatorButton(int b) {
		return m_operatorStick.getRawButton(b);
	}
	
	public boolean getRightButton(int b) {
		return m_rightStick.getRawButton(b);
	}
	
	public boolean getLeftButton(int b) {
		return m_leftStick.getRawButton(b);
	}
	
	public boolean getRightTrigger() {
		return m_rightStick.getTrigger();
	}

	public boolean getLeftTrigger() {
		return m_leftStick.getTrigger();
	}

	public boolean getOperatorTrigger() {
		return m_operatorStick.getTrigger();
	}
}
