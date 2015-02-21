package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;

public class DriveBase {

	private static DriveBase m_instance;

	private final Talon m_leftDrive1;
	private final Talon m_leftDrive2;

	private final Talon m_rightDrive1;
	private final Talon m_rightDrive2;

	private final Encoder m_leftEncoder;
	private final Encoder m_rightEncoder;

	public DriveBase() {
		m_leftDrive1 = new Talon(Constants.LEFT_DRIVE_PWM1);
		m_leftDrive2 = new Talon(Constants.LEFT_DRIVE_PWM2);

		m_rightDrive1 = new Talon(Constants.RIGHT_DRIVE_PWM1);
		m_rightDrive2 = new Talon(Constants.RIGHT_DRIVE_PWM2);

		m_leftEncoder = new Encoder(Constants.LEFT_ENCODER_A,
				Constants.LEFT_ENCODER_B);
		m_rightEncoder = new Encoder(Constants.RIGHT_ENCODER_A,
				Constants.RIGHT_ENCODER_B, true);
		

		m_leftEncoder.setDistancePerPulse(Constants.INCHES_PER_COUNT);
		m_rightEncoder.setDistancePerPulse(Constants.INCHES_PER_COUNT);

	}

	public static DriveBase getInstance() {
		if (m_instance == null) {
			m_instance = new DriveBase();
		}
		return m_instance;
	}

	public void setLeftSpeed(double speed) {
		m_leftDrive1.set(speed);
		m_leftDrive2.set(speed);
	}

	public void setRightSpeed(double speed) {
		m_rightDrive1.set(speed);
		m_rightDrive2.set(speed);
	}

	public void setSpeed(double speed) {
		setLeftSpeed(speed);
		setRightSpeed(speed);
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		setLeftSpeed(leftSpeed);
		setRightSpeed(rightSpeed);
	}

	public double getLeftEncoderDistance() {
		return m_leftEncoder.getDistance();
	}

	public double getRightEncoderDistance() {
		return m_rightEncoder.getDistance();
	}

	public void resetEncoders() {
		m_leftEncoder.reset();
		m_rightEncoder.reset();
	}
}
