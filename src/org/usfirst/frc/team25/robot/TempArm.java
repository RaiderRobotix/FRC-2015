package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Victor;

public class TempArm {
	private static TempArm m_instance;
	
	private final Victor m_victor1;
	private final Victor m_victor2;
	private final Victor m_victor3;
	
	public TempArm() {
		m_victor1 = new Victor(1);
		m_victor2 = new Victor(2);
		m_victor3 = new Victor(3);
	}
	
	public static TempArm getInstance() {
		if(m_instance == null) {
			m_instance = new TempArm();
		}
		return m_instance;
	}
	
	public void setSpeed(int victorNum, double speed) {
		switch(victorNum) {
		case 1:
			m_victor1.set(speed);
			break;
		case 2:
			m_victor2.set(speed);
			break;
		case 3:
			m_victor3.set(speed);
			break;
		default:
			System.out.println("Out Of Range!!!");
			break;
		}
	}
	
}
