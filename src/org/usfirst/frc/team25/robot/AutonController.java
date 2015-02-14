package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Timer;

public class AutonController {
	
	private static AutonController m_instance;
	
	private final DriveBase m_drivebase;
	
	private int m_step;
	private final Timer m_timer;
	
	private AutonController() {
		m_drivebase = DriveBase.getInstance();
		m_step = 0;
		m_timer = new Timer();
	}
	
	public static AutonController getInstance() {
        if (m_instance == null) {
            m_instance = new AutonController();
        }
        return m_instance;
    }

	/**
	 * Drives forward the length from one bin on the step to another.
	 */
	public void grabBinFromStep() {
		
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(distance);
		
		if (m_step == 0) {
			m_drivebase.resetEncoders();
			m_step++;
		} else if (m_step == 1) {
			if (distance < 65) {
				m_drivebase.setSpeed(-0.5);
			} else {
				m_step++;
			}
		} else if (m_step == 2) {
			// STOP
			if (distance >=65 && distance <= 70) {
				m_drivebase.setSpeed(0.5);
			} else {
				m_step++;
			}
		}
		else {
			m_drivebase.setSpeed(0.0);
		}
	}
	
	public void grabBinsFromStep() {
		
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(distance);
		
		if (m_step == 0) {
			m_drivebase.resetEncoders();
			m_step++;
		} else if (m_step == 1) {
			if (distance < 65) {
				m_drivebase.setSpeed(-0.5);
			} else {
				m_step++;
			}
		} else if (m_step == 2) {
			// STOP
			if (distance >=65 && distance <= 70) {
				m_drivebase.setSpeed(0.5);
			} else {
				m_step++;
			}
		}
		else {
			m_drivebase.setSpeed(0.0);
		}
	}
}
