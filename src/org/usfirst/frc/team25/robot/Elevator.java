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
		if (potValueWithinRange(getPotValue())) {
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

	public void gotoPotValue(double value) {
		while(!(getPotValue() == value)) {
			setSpeedByPotValue(value);
		}
	}

	private boolean potValueWithinRange(double desiredValue) {
		double m_allowedDeviation = Constants.ALLOWED_ELEVATOR_VALUE_RANGE / 2.0;
		double m_potLowerLimit = desiredValue - m_allowedDeviation;
		double m_potUpperLimit = desiredValue + m_allowedDeviation;

		if (getPotValue() >= m_potLowerLimit
				&& getPotValue() <= m_potUpperLimit) {
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

}
