package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.Talon;

public class Arm {

	private static Arm m_instance;
	
	private final AnalogPotentiometer m_rotaryPot;
	private final AnalogPotentiometer m_dartPot;
	
	private final Talon m_claw;
	private final Talon m_rotation;
	private final Talon m_armYAxis;
	
	public Arm() {
		m_claw = new Talon(Constants.ARM_CLAW_PWM);
		m_rotation = new Talon(Constants.ARM_ROTATION_PWM);
		m_armYAxis = new Talon(Constants.ARM_Y_AXIS_PWM);
		
		m_rotaryPot = new AnalogPotentiometer(Constants.ROTARY_POT);
		m_dartPot = new AnalogPotentiometer(Constants.DART_POT);
	}
	
	public static Arm getInstance() {
		if(m_instance == null) {
			m_instance = new Arm();
		}
		return m_instance;
	}
	
	public void openClaw() {
		// TODO add
	}
	
	public void closeClaw() {
		// TODO add
	}
	
	public void setClawSpeed(double speed) {
		m_claw.set(speed);
	}
	
	public void setRotationSpeed(double speed, boolean largeLimits) {
		if(largeLimits) {
			if(speed > 0 && getRotaryPot() <= Constants.UPPER_COUNTER_LIMIT)
				m_rotation.set(0.0);
			else if(speed < 0 && getRotaryPot() >= Constants.UPPER_LIMIT)
				m_rotation.set(0.0);
			else
				m_rotation.set(speed);
		} else {
			if(speed > 0 && getRotaryPot() <= Constants.LOWER_COUNTER_LIMIT)
				m_rotation.set(0.0);
			else if(speed < 0 && getRotaryPot() >= Constants.LOWER_LIMIT)
				m_rotation.set(0.0);
			else
				m_rotation.set(speed);
		}
	}

	public void setYSpeed(double speed) {
		m_armYAxis.set(speed);
	}
	
	public double getRotaryPot() {
		return m_rotaryPot.get();
	}
	
	public double getDartPot() {
		return m_dartPot.get();
	}
	
}
