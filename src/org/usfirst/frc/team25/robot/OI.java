package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

public class OI {

	private static OI m_instance;

	private final Joystick m_leftStick;
	private final Joystick m_rightStick;
	private final Joystick m_operatorStick; // TODO add operator controls

	private final DriveBase m_drivebase; 
	private final Arm m_arm;
	private final Elevator m_elevator;

	private final PowerDistributionPanel m_pdPanel;

	private double goalPotValue;
	private boolean m_autoSequenceRunning = false;
	private double m_elevatorSpeed = 0.0;

	private double m_averageClawCurrent = 0.0;
	
	private boolean m_autoArmSequence = false;
	private double m_armAutoValue = Constants.ARM_FORWARD;
	
	public OI() {
		m_leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
		m_rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
		m_operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT);

		m_pdPanel = new PowerDistributionPanel();

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

	public void startTotePosition() { /*
		m_autoSequenceRunning = true;
		goalPotValue = Constants.TOTE_CATCHING_POSITION; */
	}
	
	public void setAverageClawCurrent() {
		for(int i=0; i<15; i++) {
			m_averageClawCurrent += getCurrent(Constants.ARM_40);
		}
		m_averageClawCurrent /= 15;
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
		if (tval > -Constants.JOYSTICK_DEADBAND
				&& tval < Constants.JOYSTICK_DEADBAND) {
			return 0.0;
		} else {
			return tval;
		}
	}
	
	public double getOperatorThrottle() {
		double tval = m_operatorStick.getThrottle();
		tval += 2.0;
		return tval;
	}

	public void enableDriveControls() {
		m_drivebase.setSpeed(getLeftY(), getRightY());
	}

	public void enableTeleopControls() {
		System.out.println("Dart: " + m_arm.getDartPot());
		System.out.println("Rotary: " + m_arm.getRotaryPot());
		
		// Driverbase controls
		enableDriveControls();

	
		if (getOperatorTrigger()) {
			m_drivebase.resetEncoders();
		}


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
				m_elevatorSpeed /= 4.0;
			} else if (m_elevator.potValueWithinCustomRange(
					Constants.ELEVATOR_UPPER_LIMIT, 0.05) && getRightButton(3)) {
				m_elevatorSpeed /= 4.0;
			}

			m_elevator.setSpeed(m_elevatorSpeed); // end manual controls
		} else {
			gotoPotValue(goalPotValue);
		}
		
		if(getOperatorButton(3)) {
            m_autoArmSequence = true;
            m_armAutoValue = Constants.ARM_FORWARD;
        }
        if(Math.abs(m_operatorStick.getTwist()) >= 0.25) {
            m_autoArmSequence = false;
        }
        if(m_arm.getRotaryPot() <= m_armAutoValue + 0.005 &&
                m_arm.getRotaryPot() >= m_armAutoValue - 0.005 ) {
                m_autoArmSequence = false;
        }
        
        //Arm Y Controls
        double armYVal = getOperatorY() / getOperatorThrottle();
        
        if(m_arm.getDartPot() > Constants.DART_RETRACTED && armYVal < 0) {
        	m_arm.setYSpeed(0.0);
        } else if(m_arm.getDartPot() < Constants.DART_EXTENDED && armYVal > 0) {
        	m_arm.setYSpeed(0.0);
        } else {
        	m_arm.setYSpeed(armYVal);
        }
        	
        
        //Arm Rotation Controls
        if(m_autoArmSequence) {
            m_arm.setClawSpeed(0.0);
            double rval = 0.0;
            if(m_arm.getRotaryPot() > m_armAutoValue) {
                rval = 1.0;
            } else if(m_arm.getRotaryPot() < m_armAutoValue) {
                rval = -1.0;
            } else {
                rval = 0.0;
                m_autoArmSequence = false;
            }
            if(m_arm.getRotaryPot() <= m_armAutoValue + 0.2 &&
                m_arm.getRotaryPot() >= m_armAutoValue - 0.2 ) {
                rval /= 2.0;
            }
            m_arm.setRotationSpeed(rval, false);
        } else {
            m_arm.setRotationSpeed(getOperatorTwist() / getOperatorThrottle(), getOperatorButton(11));
            if(getOperatorTrigger() && !getOperatorButton(2)) {
                m_arm.setClawSpeed(-0.1);
            }
            else if(getOperatorButton(2) && !getOperatorTrigger()) {
                m_arm.setClawSpeed(0.1);
            } else {
                m_arm.setClawSpeed(0.0);
            }
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
				m_elevatorUp = Constants.ELEVATOR_UP / 4.0;
				m_elevatorDown = Constants.ELEVATOR_DOWN / 4.0;
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

	public double getCurrent(int slot) {
		return m_pdPanel.getCurrent(slot);
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
