package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Joystick;

public class TempArmOI {
	private static TempArmOI m_instance;
	
	private final TempArm m_tArm;
	
	private final Joystick rightStick;
	private final Joystick leftStick;
	private final Joystick operatorStick;
	
	public TempArmOI() {
		rightStick = new Joystick(Constants.RIGHT_JOYSTICK_PORT);
		leftStick = new Joystick(Constants.LEFT_JOYSTICK_PORT);
		operatorStick = new Joystick(Constants.OPERATOR_JOYSTICK_PORT);
		
		m_tArm = TempArm.getInstance();
	}
	
	public static TempArmOI getInstance() {
		if(m_instance == null) {
			m_instance = new TempArmOI();
		}
		return m_instance;
	}
	
	public void enableTeleopControls() {
		if(getLeftTrigger()) {
			m_tArm.setSpeed(1, 1.0);
		}
		else if(leftStick.getRawButton(2)) {
			m_tArm.setSpeed(1, -1.0);
		}
		else {
			m_tArm.setSpeed(1, 0.0);
		}
		
		if(getRightTrigger()) {
			m_tArm.setSpeed(2, 1.0);
		}
		else if(rightStick.getRawButton(2)) {
			m_tArm.setSpeed(2, -1.0);
		}
		else {
			m_tArm.setSpeed(2, 0.0);
		}
		
		if(getOperatorTigger()) {
			m_tArm.setSpeed(3, 1.0);
		}
		else if(operatorStick.getRawButton(2)) {
			m_tArm.setSpeed(3, -1.0);
		}
		else {
			m_tArm.setSpeed(3, 0.0);
		}
	}
	
	public boolean getLeftTrigger(){
		return leftStick.getTrigger();
	}
	
	public boolean getRightTrigger(){
		return rightStick.getTrigger();
	}
	
	public boolean getOperatorTigger(){
		return operatorStick.getTrigger();
	}
}
