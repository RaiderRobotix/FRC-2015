package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Talon;

public class Arm {

private static Arm m_instance;
	
	private final Talon m_claw;
	private final Talon m_armRotation;
	private final Talon m_armYAxis;
	
	public Arm() {
		m_claw = new Talon(Constants.ARM_CLAW_PWM);
		m_armRotation = new Talon(Constants.ARM_ROTATION_PWM);
		m_armYAxis = new Talon(Constants.ARM_Y_AXIS_PWM);
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
	
	public void setRotationSpeed(double speed) {
		m_armRotation.set(speed);
	}
	
	public void setArmYSpeed(double speed) {
		m_armYAxis.set(speed);
	}
	
}
