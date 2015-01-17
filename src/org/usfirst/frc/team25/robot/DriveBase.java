package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Talon;

public class DriveBase {
	private static DriveBase m_instance;
	private final Talon m_leftDrive1;
	private final Talon m_leftDrive2;

	private final Talon m_rightDrive1;
	private final Talon m_rightDrive2;

	public DriveBase() {
		m_leftDrive1 = new Talon(Constants.LEFT_DRIVE_PWM1);
		m_leftDrive2 = new Talon(Constants.LEFT_DRIVE_PWM2);

		m_rightDrive1 = new Talon(Constants.RIGHT_DRIVE_PWM1);
		m_rightDrive2 = new Talon(Constants.RIGHT_DRIVE_PWM2);
	}

	public static DriveBase getInstance() {
		if (m_instance == null) {
			m_instance = new DriveBase();
		}
		return m_instance;
	}

	public void setSpeed(double speed) {
		m_leftDrive1.set(speed);
		m_leftDrive2.set(speed);

		m_rightDrive1.set(speed);
		m_rightDrive2.set(speed);
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		m_leftDrive1.set(leftSpeed);
		m_leftDrive2.set(leftSpeed);

		m_rightDrive1.set(rightSpeed);
		m_rightDrive2.set(rightSpeed);
	}

	public void setRightSpeed(double speed) {
		m_rightDrive1.set(speed);
		m_rightDrive2.set(speed);
	}

	public void setLeftSpeed(double speed) {
		m_leftDrive1.set(speed);
		m_leftDrive2.set(speed);
	}

}
