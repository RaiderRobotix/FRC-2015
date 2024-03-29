package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Talon;

public class Arm {

	private static Arm m_instance;

	private final AnalogPotentiometer m_rotaryPot;
	private final AnalogPotentiometer m_dartPot;

	private final Talon m_claw;
	private final Talon m_rotation;
	private final Talon m_armYAxis;

	private final PowerDistributionPanel m_pdPanel;
	//private int m_clawTinyStep = 0;
	//private Timer m_timer;
	
	public Arm() {
		m_claw = new Talon(Constants.ARM_CLAW_PWM);
		m_rotation = new Talon(Constants.ARM_ROTATION_PWM);
		m_armYAxis = new Talon(Constants.ARM_Y_AXIS_PWM);

		m_rotaryPot = new AnalogPotentiometer(Constants.ROTARY_POT);
		m_dartPot = new AnalogPotentiometer(Constants.DART_POT);

		m_pdPanel = new PowerDistributionPanel();
		//m_timer = new Timer();
	}

	public static Arm getInstance() {
		if (m_instance == null) {
			m_instance = new Arm();
		}
		return m_instance;
	}

	public double getCurrent() {
		return m_pdPanel.getCurrent(Constants.PD_CLAW);
	}
/*
	public boolean tinyOpenClaw() {
		if(m_clawTinyStep == 0) {
			m_timer.start();
			m_timer.reset();
			setClawSpeed(Constants.CLAW_OPEN);
			m_clawTinyStep++;
		} else if(m_clawTinyStep == 1) {
			if(m_timer.get() > Constants.TINY_TIME) {
				setClawSpeed(0.0);
				m_timer.stop();
				m_clawTinyStep = 0;
				return false;
			}
		}
		return true;
	}
	
	public boolean tinyCloseClaw() {
		if(m_clawTinyStep == 0) {
			m_timer.start();
			m_timer.reset();
			setClawSpeed(Constants.CLAW_CLOSE);
			m_clawTinyStep++;
		} else if(m_clawTinyStep == 1) {
			if(m_timer.get() > Constants.TINY_TIME) {
				setClawSpeed(0.0);
				m_timer.stop();
				m_clawTinyStep = 0;
				return false;
			}
		}
		return true;
	} */
	
	public boolean openClaw(double time) {
		if(time < 1.1) {
			setClawSpeed(Constants.CLAW_OPEN);
			return true;
		} else {
			setClawSpeed(0.0);
			return false;
		}
	}

	/**
	 * @return false when claw is done closing based on current
	 */
	public boolean rotateToLeftLimit() {
		final double BUFFER = 0.05;
		if (getRotaryPot() > Constants.LEFT_LIMIT - BUFFER
				&& getRotaryPot() < Constants.LEFT_LIMIT + BUFFER) {
			setRotationSpeed(0.0);
			return false;
		}
		setRotationSpeed(-0.4);
		return true;
	}

	public boolean closeClaw(double time) {
		if(time < 1.1) {
			setClawSpeed(Constants.CLAW_CLOSE);
			return true;
		} else {
			setClawSpeed(0.0);
			return false;
		}
	}

	public void setClawSpeed(double speed) {
		m_claw.set(speed);
	}

	public double getClawCurrent() {
		return m_pdPanel.getCurrent(Constants.PD_CLAW);
	}

	public void setRotationSpeed(double speed) {
		m_rotation.set(speed);
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

	/**
	 * 
	 * @param tval
	 *            Twist value
	 * @param yval
	 *            Up/Down position
	 * @return false if at the right position
	 */
	public boolean goTo(double tval, double yval, double rotationSpeed,
			double armSpeed) { // check some stuff in this

		final double ROTATE_BUFFER = 0.005;
		final double TILT_BUFFER = 0.005;

		// if complete
		if (getRotaryPot() < tval + ROTATE_BUFFER
				&& getRotaryPot() > tval - ROTATE_BUFFER
				&& getDartPot() < yval + TILT_BUFFER
				&& getDartPot() > yval - TILT_BUFFER) {
			setYSpeed(0.0);
			setRotationSpeed(0.0);
			return false;
		}

		if (yval > Constants.DART_EXTENDED
				&& getRotaryPot() > Constants.LEFT_LIMIT) {
			// if needs to go down and rotated too far
			setRotationSpeed(rotationSpeed);
			setYSpeed(0.0);
		} else if (tval > Constants.LEFT_LIMIT
				&& getDartPot() > Constants.DART_EXTENDED + 0.01) {
			// if needs to rotate far and arm down
			setYSpeed(armSpeed); // up
			setRotationSpeed(0.0);
		} else {

			if (Math.abs(getDartPot() - yval) <= 0.075) {
				armSpeed = (armSpeed / Math.abs(armSpeed)) * 0.5;
			}

			if (getDartPot() < yval - TILT_BUFFER) {
				setYSpeed(-armSpeed); // arm down
			} else if (getDartPot() > yval + TILT_BUFFER) {
				setYSpeed(armSpeed); // arm up
			} else {
				setYSpeed(0.0);
			}

			if (getRotaryPot() > tval + ROTATE_BUFFER) {
				setRotationSpeed(rotationSpeed); // rotate left, CCW
			} else if (getRotaryPot() < tval - ROTATE_BUFFER) {
				setRotationSpeed(-rotationSpeed); // rotate right, CW
			} else {
				setRotationSpeed(0.0);
			}
		}

		return true;
	}

}
