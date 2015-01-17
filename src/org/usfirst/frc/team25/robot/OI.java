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

	public void enableTeleopControls() {

		m_drivebase.setSpeed(getLeftY(), getRightY());
		
		if (m_leftStick.getTrigger())
		{
			m_drivebase.resetEncoders();
		}
		
		System.out.println("LEFT ENC:"+m_drivebase.getLeftEncoderDistance());
		System.out.println("RIGHT ENC:"+m_drivebase.getRightEncoderDistance());
	}

}
