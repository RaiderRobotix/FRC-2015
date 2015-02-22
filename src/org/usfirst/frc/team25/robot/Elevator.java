package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;

public class Elevator {

	private static Elevator m_instance;

	private final AnalogPotentiometer m_stringPot;

	private final Talon m_elevatorLeft;
	private final Talon m_elevatorRight;

	public Elevator() {
		m_elevatorLeft = new Talon(Constants.LEFT_ELEVATOR_PWM);
		m_elevatorRight = new Talon(Constants.RIGHT_ELEVATOR_PWM);

		m_stringPot = new AnalogPotentiometer(Constants.STRING_POT_PWM);
	}

	public static Elevator getInstance() {
		if (m_instance == null) {
			m_instance = new Elevator();
		}
		return m_instance;
	}

	public void setSpeedByPotValue(double desiredValue) {
		if (potValueWithinRange(desiredValue)) {
			setSpeed(0.0);
		} else if (desiredValue <= Constants.ELEVATOR_LOWER_LIMIT
				|| desiredValue >= Constants.ELEVATOR_UPPER_LIMIT) {
			setSpeed(0.0);
		} else if(getPotValue() < desiredValue) {
			setSpeed(1.0);
		} else if(getPotValue() > desiredValue) {
			setSpeed(-1.0);
		} else {
			setSpeed(0.0);
		}
	}

	public boolean potValueWithinRange(double desiredValue) {
		double m_potLowerLimit = desiredValue + Constants.ALLOWED_DEVIATION;
		double m_potUpperLimit = desiredValue - Constants.ALLOWED_DEVIATION;

		if (getPotValue() <= m_potLowerLimit
				&& getPotValue() >= m_potUpperLimit) {
			return true;
		}

		return false;
	}

	public boolean potValueWithinCustomRange(double desiredValue, double allowedDeviation) {
		double m_potLowerLimit = desiredValue + allowedDeviation;
		double m_potUpperLimit = desiredValue - allowedDeviation;

		if (getPotValue() <= m_potLowerLimit
				&& getPotValue() >= m_potUpperLimit) {
			return true;
		}

		return false;
	} 
	
	public void setSpeed(double speed) {
		m_elevatorLeft.set(speed);
		m_elevatorRight.set(speed);
	}

	public double getPotValue() {
		return m_stringPot.get();
	}

	public boolean goToPotValue(double value) {
		boolean m_goingUp = getPotValue() >= value;
		boolean m_goingDown = !m_goingUp;
		double m_elevatorUp = Constants.ELEVATOR_UP;
		double m_elevatorDown = Constants.ELEVATOR_DOWN;

		// Checking if past limits
		if (m_goingDown
				&& getPotValue() >= Constants.ELEVATOR_LOWER_LIMIT) {
			setSpeed(0.0);
			return false;
		} else if (m_goingUp
				&& getPotValue() <= Constants.ELEVATOR_UPPER_LIMIT) {
			setSpeed(0.0);
			return false;
		} else if (potValueWithinRange(value)) {
			setSpeed(0.0);
			return false;
		} else if (!potValueWithinRange(value)) {
			if (potValueWithinCustomRange(value, 0.075)) {
				m_elevatorUp = Constants.ELEVATOR_UP / 4.0;
				m_elevatorDown = Constants.ELEVATOR_DOWN / 4.0;
			}

			if (getPotValue() < value) {
				setSpeed(m_elevatorDown);
			} else if (getPotValue() > value) {
				setSpeed(m_elevatorUp);
			} else {
				setSpeed(0.0);
				return false;
			}
		}
		
		return true;
	}
}
