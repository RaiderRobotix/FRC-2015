package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {

	private static OI m_instance;

	private final Joystick m_leftStick;
	private final Joystick m_rightStick;
	private final Joystick m_operatorStick; // TODO add operator controls
	
	private final PowerDistributionPanel m_pdp;
	
	private final DriveBase m_drivebase;
	private final Arm m_arm;
	private final Elevator m_elevator;

	private double goalPotValue;
	private boolean m_autoSequenceRunning = false;
	private double m_elevatorSpeed = 0.0;
	private boolean m_autoArmSequence = false;
	private double m_autoYValue = 0.0;
	private double m_autoTValue = 0.0;

	public OI() {
		m_leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
		m_rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
		m_operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT);

		m_drivebase = DriveBase.getInstance();
		m_arm = Arm.getInstance();
		m_elevator = Elevator.getInstance();

		m_pdp = new PowerDistributionPanel();
	}

	public static OI getInstance() {
		if (m_instance == null) {
			m_instance = new OI();
		}
		return m_instance;
	}

	public void startTotePosition() {
		/*
		 * m_autoSequenceRunning = true; goalPotValue =
		 * Constants.TOTE_CATCHING_POSITION;
		 */
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

	public double getOperatorY() {
		double yval = m_operatorStick.getY();
		if (yval > -Constants.JOYSTICK_DEADBAND
				&& yval < Constants.JOYSTICK_DEADBAND) {
			return 0.0;
		} else {
			return yval;
		}
	}

	public double getOperatorTwist() {
		double tval = m_operatorStick.getTwist();
		if (tval > -0.25
				&& tval < 0.25) {
			return 0.0;
		} else {
			double rotationLimit = Constants.ARM_SLOW;
			if(getOperatorButton(11)) {
				rotationLimit = Constants.ARM_FAST;
			} else if (Math.abs(tval) > rotationLimit) {
				tval = (tval / (Math.abs(tval))) * rotationLimit;
			}
			return -tval;
		}
	}

	public double getOperatorThrottle() {
		double tval = m_operatorStick.getThrottle();
		tval += 2.0;
		return tval;
	}

	public void enableTeleopControls() {
		//System.out.println("Dart: " + m_arm.getDartPot());
		//System.out.println("Rotary: " + m_arm.getRotaryPot());
		// System.out.println("current" + m_arm.getClawCurrent());

		
		SmartDashboard.putNumber("Dart", m_arm.getDartPot());
		SmartDashboard.putNumber("Rotary", m_arm.getRotaryPot());
		SmartDashboard.putNumber("String", m_elevator.getPotValue());
		SmartDashboard.putNumber("Claw Current", m_arm.getClawCurrent());
		SmartDashboard.putNumber("Left Drive Current: ", m_pdp.getCurrent(Constants.LEFT_DRIVES));
		SmartDashboard.putNumber("Right Drive Current: ", m_pdp.getCurrent(Constants.RIGHT_DRIVES));
		SmartDashboard.putNumber("Operator Twist: ", getOperatorTwist());
		SmartDashboard.putNumber("Operator POV: ", m_operatorStick.getPOV());
		
		// Drivebase controls
		m_drivebase.setSpeed(getLeftY(), getRightY());

		/*
		 * if (getOperatorTrigger()) { m_drivebase.resetEncoders(); }
		 */

		if (getLeftButton(2)) {
			m_autoSequenceRunning = true;
			goalPotValue = Constants.ELEVATOR_LOWER_LIMIT;
		} else if (getLeftButton(3)) {
			m_autoSequenceRunning = true;
			goalPotValue = Constants.ELEVATOR_UPPER_LIMIT;
		} else if (getLeftButton(4)) {
			m_autoSequenceRunning = true;
			goalPotValue = Constants.TOTE_CATCHING_POSITION;
		} else if (getRightButton(4)) {
			m_autoSequenceRunning = true;
			goalPotValue = Constants.TOTE_SET_POSITION;
		}

		if (getOperatorTrigger() && !getOperatorButton(2)) {
			m_arm.setClawSpeed(-1.0); // close
		} else if (!getOperatorTrigger() && getOperatorButton(2)) {
			m_arm.setClawSpeed(1.0); // open
		} else {
			m_arm.setClawSpeed(0.0);
		} // */
		/*
		 * if(getOperatorTrigger()) { m_clawAuto = true; m_clawClosing = true; }
		 * else if(getOperatorButton(2)) { m_clawAuto = true; m_clawClosing =
		 * false; }
		 * 
		 * if(getOperatorButton(12)) { m_arm.setClawSpeed(0.0);
		 * m_arm.setRotationSpeed(0.0); m_arm.setYSpeed(0.0); m_clawAuto =
		 * false; m_autoArmSequence = false; }
		 * 
		 * if(m_clawAuto) { if(m_clawClosing) { m_clawAuto = m_arm.closeClaw();
		 * } else { m_clawAuto = m_arm.openClaw(); } } //
		 */

		// Terminate button
		if (m_autoSequenceRunning
				&& (getRightButton(5) || getRightButton(2) || getRightButton(3))) {
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
				m_elevatorSpeed /= Constants.ELEVATOR_SLOW_RATIO;
			} else if (m_elevator.potValueWithinCustomRange(
					Constants.ELEVATOR_UPPER_LIMIT, 0.05) && getRightButton(3)) {
				m_elevatorSpeed /= Constants.ELEVATOR_SLOW_RATIO;
			}

			m_elevator.setSpeed(m_elevatorSpeed); // end manual controls
		} else {
			m_autoSequenceRunning = m_elevator.goToPotValue(goalPotValue);
		}

		// ARM CONTROLS V V V
		if (getOperatorButton(3)) {
			m_autoArmSequence = true;
			//runningArmNormalSeq = true;
			//yComplete = false;
			//timerGoing = false;
			m_autoTValue = Constants.ARM_BACKWARDS;
			m_autoYValue = Constants.DART_EXTENDED;
		} else if (getOperatorButton(4)) {
			m_autoArmSequence = true;
			m_autoTValue = Constants.ARM_FORWARDS;
			m_autoYValue = m_arm.getDartPot();
		} else if(getOperatorButton(5)) {
			m_autoArmSequence = true;
			m_autoTValue = m_arm.getRotaryPot();
			m_autoYValue = Constants.NOODLE_HEIGHT;
		}

		if (Math.abs(getOperatorTwist()) >= 0.25
				|| Math.abs(getOperatorY()) >= 0.25) { // Manual Terminate
			m_autoArmSequence = false;
		}

		
		if (getOperatorButton(12)) {
			m_autoArmSequence = false;
		}
		
		
		if (!getOperatorButton(12)) {
			if (m_autoArmSequence) {
				m_autoArmSequence = m_arm.goTo(m_autoTValue, m_autoYValue,
						0.66, 1.0);
			} else {
				// Arm Y Controls
				if (m_arm.getRotaryPot() < Constants.LEFT_LIMIT) {
					double armYVal = getOperatorY() / getOperatorThrottle();

					if (m_arm.getDartPot() > Constants.DART_RETRACTED
							&& armYVal < 0) {
						m_arm.setYSpeed(0.0);
					} else if (m_arm.getDartPot() < Constants.DART_EXTENDED
							&& armYVal > 0) {
						m_arm.setYSpeed(0.0);
					} else {

						final double SLOW_RANGE = 0.03;

						if (m_arm.getDartPot() <= Constants.DART_EXTENDED
								+ SLOW_RANGE
								&& armYVal > 0) {
							armYVal /= 2.0;
						} else if (m_arm.getDartPot() >= Constants.DART_RETRACTED
								- SLOW_RANGE
								&& armYVal < 0) {
							armYVal /= 2.0;
						}

						m_arm.setYSpeed(armYVal);
					}
				} else {
					m_arm.setYSpeed(0.0);
				}

				double armTVal = getOperatorTwist();

				final double SLOW_RANGE = 0.03;

				if (m_arm.getRotaryPot() < Constants.RIGHT_LIMIT + SLOW_RANGE) {
					armTVal /= 2.0;
				}

				if (armTVal > 0
						&& m_arm.getRotaryPot() <= Constants.RIGHT_LIMIT) {
					m_arm.setRotationSpeed(0.0);
				} else if (m_arm.getDartPot() < Constants.DART_EXTENDED + 0.01) {

					if (m_arm.getRotaryPot() > Constants.ARM_BACKWARDS
							- SLOW_RANGE) {
						armTVal /= 2.0;
					}

					// Arm Extended (AKA up)
					if (armTVal < 0
							&& m_arm.getRotaryPot() >= Constants.ARM_BACKWARDS) {
						m_arm.setRotationSpeed(0.0);
					} else {
						m_arm.setRotationSpeed(armTVal);
					}
				} else {

					if (m_arm.getRotaryPot() > Constants.LEFT_LIMIT
							- SLOW_RANGE) {
						armTVal /= 2.0;
					}

					// Arm Retracted
					if (armTVal < 0
							&& m_arm.getRotaryPot() >= Constants.LEFT_LIMIT) {
						m_arm.setRotationSpeed(0.0);
					} else {
						m_arm.setRotationSpeed(armTVal);
					}
				}
			}
		} else {
			m_arm.setRotationSpeed(getOperatorTwist());
			double yspeed = getOperatorY() / getOperatorThrottle();
			if(yspeed > 0.0 && m_arm.getDartPot() < Constants.DART_EXTENDED + 0.01) {
				yspeed = 0.0;
			}
			m_arm.setYSpeed(yspeed);
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
