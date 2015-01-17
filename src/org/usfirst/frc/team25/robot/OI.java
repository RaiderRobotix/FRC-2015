package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {
	private static OI m_instance;
	
	private final Joystick m_leftStick;
	private final Joystick m_rightStick;
	
	private final DriveBase m_drivebase;

	public OI() {
		m_leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
		m_rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);

		m_drivebase = DriveBase.getInstance();
	}

	public static OI getInstance() {
		if (m_instance == null) {
			m_instance = new OI();
		}
		return m_instance;
	}

	public double getLeftY() {
		double yval = m_leftStick.getY();
		if (yval > -0.1 && yval < 0.1) {
			return 0.0;
		} else {
			return yval;
		}
	}

	public double getRightY() {
		double yval = m_rightStick.getY();
		if (yval > -0.1 && yval < 0.1) {
			return 0.0;
		} else {
			return yval;
		}
	}

	public void enableTeleopControls() {
		m_drivebase.setSpeed(getLeftY(), getRightY());
	}

}
