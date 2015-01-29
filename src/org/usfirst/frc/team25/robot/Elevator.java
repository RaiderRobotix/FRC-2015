package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Talon;

public class Elevator {

	private static Elevator m_instance;
	
	private final Talon m_elevatorLeft;
	private final Talon m_elevatorRight;
	
	public Elevator() {
		m_elevatorLeft = new Talon(Constants.LEFT_ELEVATOR_PWM);
		m_elevatorRight = new Talon(Constants.RIGHT_ELEVATOR_PWM);
	}
	
	public static Elevator getInstance() {
		if(m_instance == null) {
			m_instance = new Elevator();
		}
		return m_instance;
	}
	
	public void setSpeed(double speed) {
		m_elevatorLeft.set(speed);
		m_elevatorRight.set(speed);
	}
		
}

