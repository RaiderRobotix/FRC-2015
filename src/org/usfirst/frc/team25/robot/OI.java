package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;

public class OI {

	private static OI m_instance;

	private final Joystick m_leftStick;
	private final Joystick m_rightStick;
	private final Joystick m_operatorStick; // TODO add operator controls

	private final DriveBase m_drivebase;
	private final Arm m_arm;
	private final Elevator m_elevator;

	private double goalPotValue;
	private boolean m_autoSequenceRunning = false;
	private double m_elevatorSpeed = 0.0;

	public OI() {
		m_leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
		m_rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
		m_operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT);

		m_drivebase = DriveBase.getInstance();
		m_arm = Arm.getInstance();
		m_elevator = Elevator.getInstance();
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
	
	public double getOperatorX() {
		double xval = m_operatorStick.getX();
		if (xval > -Constants.JOYSTICK_DEADBAND
				&& xval < Constants.JOYSTICK_DEADBAND) {
			return 0.0;
		} else {
			return xval;
		}
	}

	public void enableDriveControls() {
		m_drivebase.setSpeed(getLeftY(), getRightY());
		
		//System.out.println("Left: " + m_drivebase.getLeftEncoderDistance());
		//System.out.println("Right: " + m_drivebase.getRightEncoderDistance());
		
		System.out.println("L Current: " + m_drivebase.getLeftCurrent());
		System.out.println("R Current: " + m_drivebase.getRightCurrent());
	}

	public void enableTeleopControls() {

		// Driverbase controls
		enableDriveControls();

		if (m_operatorStick.getTrigger()) {
			m_drivebase.resetEncoders();
		}
		
		// Arm controls
		m_arm.setRotationSpeed(getOperatorX());

		// Elevator controls
		// System.out.println("String Pot Value: " + m_elevator.getPotValue() + "\n");
		
		if (getLeftButton(2)) {
			m_autoSequenceRunning = true;
			goalPotValue = Constants.ELEVATOR_LOWER_LIMIT;
		} else if (getLeftButton(3)) {
			m_autoSequenceRunning = true;
			goalPotValue = Constants.ELEVATOR_UPPER_LIMIT;
		} else if (getLeftButton(4)) {
			m_autoSequenceRunning = true;
			goalPotValue = Constants.TOTE_CATCHING_POSITION;
		}

		// Terminate button
		if (m_autoSequenceRunning && (getRightButton(5) || getRightButton(2) || getRightButton(3))) {
			m_autoSequenceRunning = false;
		}
		
		// Start manual elevator controls to raise and lower
		if (!m_autoSequenceRunning) {
			if (getRightButton(3)
					&& !getRightButton(2)
					&& m_elevator.getPotValue() >= Constants.ELEVATOR_UPPER_LIMIT) {
				m_elevatorSpeed = Constants.ELEVATOR_UP;
			} else if (getRightButton(2)
					&& !getRightButton(3)
					&& m_elevator.getPotValue() <= Constants.ELEVATOR_LOWER_LIMIT) {
				m_elevatorSpeed = Constants.ELEVATOR_DOWN;
			} else {
				m_elevatorSpeed = 0.0;
			}

			if (m_elevator.potValueWithinCustomRange(
					Constants.ELEVATOR_LOWER_LIMIT, 0.05) && getRightButton(2)) {
				m_elevatorSpeed /= 2.0;
			} else if (m_elevator.potValueWithinCustomRange(
					Constants.ELEVATOR_UPPER_LIMIT, 0.05) && getRightButton(3)) {
				m_elevatorSpeed /= 2.0;
			}

			m_elevator.setSpeed(m_elevatorSpeed);	// end manual controls
		} else {
			gotoPotValue(goalPotValue);
		}
	}

	public void gotoPotValue(double value) {
		boolean m_goingUp = m_elevator.getPotValue() >= value;
		boolean m_goingDown = !m_goingUp;
		double m_elevatorUp = Constants.ELEVATOR_UP;
		double m_elevatorDown = Constants.ELEVATOR_DOWN;

		// Checking if past limits
		if (m_goingDown
				&& m_elevator.getPotValue() >= Constants.ELEVATOR_LOWER_LIMIT) {
			m_autoSequenceRunning = false;
			m_elevator.setSpeed(0.0);
		} else if (m_goingUp
				&& m_elevator.getPotValue() <= Constants.ELEVATOR_UPPER_LIMIT) {
			m_autoSequenceRunning = false;
			m_elevator.setSpeed(0.0);
		} else if (m_elevator.potValueWithinRange(value)) {
			m_autoSequenceRunning = false;
			m_elevator.setSpeed(0.0);
		} else if (!m_elevator.potValueWithinRange(value)) {
			if (m_elevator.potValueWithinCustomRange(value, 0.075)) {
				m_elevatorUp = Constants.ELEVATOR_UP / 2.0;
				m_elevatorDown = Constants.ELEVATOR_DOWN / 2.0;
			}

			if (m_elevator.getPotValue() < value) {
				m_elevator.setSpeed(m_elevatorDown);
			} else if (m_elevator.getPotValue() > value) {
				m_elevator.setSpeed(m_elevatorUp);
			} else {
				m_autoSequenceRunning = false;
				m_elevator.setSpeed(0.0);
			}
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
