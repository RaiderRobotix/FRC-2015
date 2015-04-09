package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.Timer;

public class AutonController {

	private static AutonController m_instance;

	private final DriveBase m_drivebase;
	private final Arm m_arm;
	private final Elevator m_elevator;

	private int m_step;
	private final Timer m_timer;

	private AutonController() {
		m_drivebase = DriveBase.getInstance();
		m_arm = Arm.getInstance();
		m_elevator = Elevator.getInstance();
		m_step = 0;
		m_timer = new Timer();
	}

	public static AutonController getInstance() {
		if (m_instance == null) {
			m_instance = new AutonController();
		}
		return m_instance;
	}

	public void reset() {
		m_step = 0;
	}

	/**
	 * Drives forward the length from one bin on the step to another.
	 */

	public void driveToNextContainer() {

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
			if (distance >= 65 && distance <= 70) {
				m_drivebase.setSpeed(0.5);
			} else {
				m_step++;
			}
		} else {
			m_drivebase.setSpeed(0.0);
		}
	}

	/**
	 * Drives into the auto zone. Robot starts right at the edge.
	 */
	public void driveToAutoZone() {
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(distance);

		if (m_step == 0) {
			m_drivebase.resetEncoders();
			m_step++;
		} else if (m_step == 1) {
			if (distance < 45) {
				m_drivebase.setSpeed(-0.25);
			} else {
				m_step++;
			}
		} else if (m_step == 2) {
			// STOP
			if (distance >= 45 && distance <= 50) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_step++;
			}
		} else {
			m_drivebase.setSpeed(0.0);
		}
	}

	public void pushContainerToAutoZone() {
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(distance);

		if (m_step == 0) {
			m_drivebase.resetEncoders();
			m_step++;
		} else if (m_step == 1) {
			if (distance > -135) {
				m_drivebase.setSpeed(0.3);
			} else {
				m_step++;
			}
		} else if (m_step == 2) {
			// STOP
			if (distance <= -135 && distance >= -140) {
				m_drivebase.setSpeed(-0.1);
			} else {
				m_step++;
			}
		} else {
			m_drivebase.setSpeed(0.0);
		}
	}

	public void dropArm() {
		if(m_step == 0) {
			m_step++;
		} else if(m_step == 1) {
			if(!m_arm.goTo(Constants.ARM_FORWARDS, m_arm.getDartPot(), 0.5, 0.0)) {
				m_arm.setYSpeed(0.0);
				m_arm.setRotationSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 2) {
			if(m_timer.get() < 1.1) {
				m_arm.setClawSpeed(Constants.CLAW_OPEN);
			} else {
				m_timer.stop();
				m_arm.setClawSpeed(0.0);
				m_step++;
			}
		} else if(m_step == 3) {
			if(!m_arm.goTo(m_arm.getRotaryPot(), 0.6475, 0.0, 0.8)) {
				m_arm.setYSpeed(0.0);
				m_arm.setRotationSpeed(0.0);
				m_step++;
			}
		} else {
			m_arm.setRotationSpeed(0.0);
			m_arm.setYSpeed(0.0);
			m_arm.setClawSpeed(0.0);
			m_drivebase.setSpeed(0.0);
		}
	}
	
	public void armGrabOneCan() {
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(m_step);

		if (m_step == 0) {  //start
			m_drivebase.resetEncoders();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {  //drive forward
			if (distance < 36) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_drivebase.setSpeed(0.1);
			}
			if (m_timer.get() < 1.0) {
				m_arm.setClawSpeed(Constants.CLAW_OPEN);  //and open claw
			} else {
				m_arm.setClawSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 2) {  //finish driving forward
			if (distance < 36) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 3) {  //drive back a little  by timer
			if(m_timer.get() < 0.25) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_timer.stop();
				m_drivebase.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 4) {  //bring arm forwards and down
			if (!m_arm.goTo(Constants.ARM_FORWARDS, Constants.DART_RETRACTED, 0.66, 0.8)) {
				m_arm.setRotationSpeed(0.0);
				m_arm.setYSpeed(0.0);
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if(m_step == 5) {  //move back (into the can)
			if (distance > -36) {
				m_drivebase.setSpeed(0.3);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 6) {
			if (m_timer.get() < 0.3) {  //move forward a little (to stop TIMER)
				m_drivebase.setSpeed(-0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 7) {  //close claw
			if(m_timer.get() < 1.1) {
				m_arm.setClawSpeed(Constants.CLAW_CLOSE);
			} else {
				m_arm.setClawSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if(m_step == 8) {  //bring arm up a little
			if(!m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_RETRACTED, 0.0, 1.0)) {
				m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_EXTENDED, 0.0, 0.3);
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if(m_step == 9) {  //drive forward into the auto zone
			if (distance < 75) {
				m_drivebase.setSpeed(-0.4);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 10) {  //move backward a little (to stop TIMER)
			if (m_timer.get() < 0.3) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if(m_step == 11) {
			if(!m_arm.goTo(Constants.LEFT_LIMIT, Constants.DART_EXTENDED, 0.3, 0.85)) {
				m_arm.setYSpeed(0.0);
				m_arm.setRotationSpeed(0.0);
				m_step++;
			}
		/*} else if(m_step == 11) {  //turn the damn arm
			/*if(!m_arm.goTo(Constants.LEFT_LIMIT, m_arm.getDartPot(), 0.4, 0.0)) {
				m_arm.setYSpeed(0.0);
				m_arm.setRotationSpeed(0.0);
				m_step++;
			} *
			if(m_arm.getRotaryPot() < Constants.LEFT_LIMIT) {
				m_arm.setRotationSpeed(-0.4);
			} else {
				m_arm.setRotationSpeed(0.0);
				m_step++;
			}*/
		} else {  //set everything to zero
			m_drivebase.setSpeed(0.0);
			m_arm.setYSpeed(0.0);
			m_arm.setClawSpeed(0.0);
			m_arm.setRotationSpeed(0.0);
		}
	}
	
	public void getOneCanReady() {  //to noodle height
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(m_step);

		if (m_step == 0) {  //start
			m_drivebase.resetEncoders();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {  //drive forward
			if (distance < 36) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_drivebase.setSpeed(0.1);
			}
			if (m_timer.get() < 1.0) {
				m_arm.setClawSpeed(Constants.CLAW_OPEN);  //and open claw
			} else {
				m_arm.setClawSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 2) {  //finish driving forward
			if (distance < 36) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 3) {  //drive back a little
			if(m_timer.get() < 0.25) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_timer.stop();
				m_drivebase.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 4) {  //bring arm forwards and down
			if (!m_arm.goTo(Constants.ARM_FORWARDS, Constants.DART_RETRACTED, 0.66, 0.75)) {
				m_arm.setRotationSpeed(0.0);
				m_arm.setYSpeed(0.0);
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if(m_step == 5) {  //move back (into the can)
			if (distance > -36) {
				m_drivebase.setSpeed(0.3);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 6) {
			if (m_timer.get() < 0.25) {  //move forward a little (to stop TIMER)
				m_drivebase.setSpeed(-0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else {  //set everything to zero
			m_drivebase.setSpeed(0.0);
			m_arm.setYSpeed(0.0);
			m_arm.setClawSpeed(0.0);
			m_arm.setRotationSpeed(0.0);
		}
	}
	
	public void pickUpCan() {  //to noodle height
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(m_step);

		if (m_step == 0) {  //start
			m_drivebase.resetEncoders();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {  //drive forward
			if (distance < 36) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_drivebase.setSpeed(0.1);
			}
			if (m_timer.get() < 1.0) {
				m_arm.setClawSpeed(Constants.CLAW_OPEN);  //and open claw
			} else {
				m_arm.setClawSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 2) {  //finish driving forward
			if (distance < 36) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 3) {  //drive back a little
			if(m_timer.get() < 0.25) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_timer.stop();
				m_drivebase.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 4) {  //bring arm forwards and down
			if (!m_arm.goTo(Constants.ARM_FORWARDS, Constants.DART_RETRACTED, 0.66, 0.75)) {
				m_arm.setRotationSpeed(0.0);
				m_arm.setYSpeed(0.0);
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if(m_step == 5) {  //move back (into the can)
			if (distance > -36) {
				m_drivebase.setSpeed(0.3);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 6) {
			if (m_timer.get() < 0.3) {  //move forward a little (to stop TIMER)
				m_drivebase.setSpeed(-0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 7) {  //close claw
			if(m_timer.get() < 1.1) {
				m_arm.setClawSpeed(Constants.CLAW_CLOSE);
			} else {
				m_arm.setClawSpeed(0.0);
				m_timer.stop();
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if(m_step == 8) {
			if(distance < 12) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 9) {
			if(m_timer.get() < 0.25) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_timer.stop();
				m_drivebase.setSpeed(0.0);
				m_step++;
			}
		} else if(m_step == 10) {  //bring dart up
			if(!m_arm.goTo(m_arm.getRotaryPot(), Constants.NOODLE_HEIGHT, 0.0, 0.75)) {
				m_arm.setYSpeed(0.0);
				m_arm.setRotationSpeed(0.0);
				m_step++;
			}
		} else {  //set everything to zero
			m_drivebase.setSpeed(0.0);
			m_arm.setYSpeed(0.0);
			m_arm.setClawSpeed(0.0);
			m_arm.setRotationSpeed(0.0);
		}
	}

	public void toteAndCanToAutoZone() {
		double distance = m_drivebase.getLeftEncoderDistance();

		if (m_step == 0) {
			m_drivebase.resetEncoders();
			m_step++;
		} else if (m_step == 1) {
			if (m_elevator.getPotValue() > Constants.TOTE_CATCHING_POSITION) {
				m_elevator.setSpeed(Constants.ELEVATOR_UP);
			} else {
				m_elevator.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 2) {
			if (distance < 30) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_step++;
			}
		} else if (m_step == 3) {
			// STOP
			if (distance >= 30 && distance <= 35) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_step++;
			}
		} else if (m_step == 4) {
			m_drivebase.setSpeed(0.0);
			m_step++;
		} else if (m_step == 5) {
			if (!m_arm.goTo(Constants.ARM_FORWARDS, m_arm.getDartPot(), 0.8,
					0.0)) {
				m_step++;
			}
		} else if (m_step == 6) {
			m_arm.setRotationSpeed(0.0);
			m_step++;
		} else if (m_step == 7) {
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 8) {
			if (m_timer.get() < 0.8) {
				m_arm.setClawSpeed(Constants.CLAW_OPEN);
			} else {
				m_timer.stop();
				m_arm.setClawSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 9) {
			if (!m_arm.goTo(m_arm.getRotaryPot(), 0.965, 0.0, 1.0)) {
				m_step++;
			}
		} else if (m_step == 10) {
			m_arm.setYSpeed(0.0);
			m_drivebase.resetEncoders();
			m_step++;
		} else if (m_step == 11) {
			if (distance > -15) {
				m_drivebase.setSpeed(0.3);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 12) {
			if (m_timer.get() < 1.1) {
				m_arm.setClawSpeed(Constants.CLAW_CLOSE);
			} else {
				m_arm.setClawSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 13) {
			if (!m_arm.goTo((Constants.RIGHT_LIMIT + 0.05), 0.95, 0.75, 1.0)) {
				m_arm.setYSpeed(0.0);
				m_arm.setRotationSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 14) {
			if (m_timer.get() > 0.5) {
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 15) {
			if (m_timer.get() < 0.7) {
				m_drivebase.setSpeed(-1.0, 1.0);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 16) {
			if (m_timer.get() < 1.15) {
				m_drivebase.setSpeed(-1.0, -0.8);
			} else if (m_timer.get() < 1.45) {
				m_drivebase.setSpeed(0.1, 0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 17) {
			if (!m_arm
					.goTo(Constants.LEFT_LIMIT, m_arm.getDartPot(), 0.66, 0.0)) {
				m_arm.setRotationSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 18) {
			if (!m_elevator.goToPotValue(Constants.ELEVATOR_LOWER_LIMIT)) {
				m_elevator.setSpeed(0.0);
				m_step++;
			}
		} else {
			m_drivebase.setSpeed(0.0);
			m_arm.setYSpeed(0.0);
			m_arm.setRotationSpeed(0.0);
			m_arm.setClawSpeed(0.0);
		}
	}

	public void pushToteToAutoZone() {
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(distance);

		if (m_step == 0) {
			m_drivebase.resetEncoders();
			m_step++;
		} else if (m_step == 1) {
			if (distance < 135) {
				m_drivebase.setSpeed(-0.3);
			} else {
				m_step++;
			}
		} else if (m_step == 2) {
			// STOP
			if (distance >= 135 && distance <= 140) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_step++;
			}
		} else {
			m_drivebase.setSpeed(0.0);
		}
	}

	/**
	 * Moves the containers from the home zone to the auto zone.
	 */
	public void moveContainersToAutoZone() {
		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(distance);

		if (m_step == 0) {
			if (!m_arm.closeClaw()) {
				m_step++;
			}
		} else if (m_step == 1) {
			// Arm straight up
			if (!m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_EXTENDED, 0.0,
					1.0)) {
				m_step++;
			}
		} else if (m_step == 2) {
			// Rotate to 90 deg
			if (!m_arm
					.goTo(Constants.RIGHT_LIMIT, m_arm.getDartPot(), 1.0, 0.0)) {
				m_step++;
			}
		} else if (m_step == 3) {
			// Arm down
			if (!m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_RETRACTED,
					0.0, 1.0)) {
				m_step++;
			}
		} else if (m_step == 4) {
			// Release container
			if (!m_arm.openClaw()) {
				m_step++;
				m_arm.setClawSpeed(0.0);
			}
		} else if (m_step == 5) {
			// Arm straight up
			if (!m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_EXTENDED, 0.0,
					1.0)) {
				m_step++;
				m_drivebase.resetEncoders();
			}
		} else if (m_step == 6) {
			// Start rotating arm
			m_arm.goTo(0.578, m_arm.getDartPot(), 1.0, 0.0);

			// Drive forward 85 inches
			if (distance < 80) {
				m_drivebase.setSpeed(-0.5);
			} else {
				m_step++;
			}
		} else if (m_step == 7) {
			// STOP
			boolean complete = false;

			if (distance >= 80 && distance <= 85) {
				m_drivebase.setSpeed(0.5);
			} else {
				m_drivebase.setSpeed(0.0);
				complete = true;
			}

			// Rotate to pick up next can deg
			if (!m_arm.goTo(0.578, m_arm.getDartPot(), 1.0, 0.0) && complete) {
				m_step++;
			}
		} else if (m_step == 8) {
			if (!m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_RETRACTED,
					0.0, 1.0)) {
				m_step++;
			}
		} else if (m_step == 9) {
			if (!m_arm.closeClaw()) {
				m_step++;
			}
		} else if (m_step == 10) {
			if (!m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_EXTENDED, 0.0,
					1.0)) {
				m_step++;
				m_timer.start();
				m_timer.reset();
			}
		} else if (m_step == 11) {
			if (m_timer.get() < 0.6) {
				m_drivebase.setSpeed(-1.0, 1.0);
			} else {
				m_drivebase.setSpeed(0.0);
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if (m_step == 12) {
			if (distance < 60) {
				m_drivebase.setSpeed(-0.35);
			} else {
				m_step++;
			}
		} else if (m_step == 13) {
			// STOP
			if (distance >= 60 && distance <= 65) {
				m_drivebase.setSpeed(0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_step++;
			}
		} else {
			m_drivebase.setSpeed(0.0);
			m_arm.setClawSpeed(0.0);
			m_arm.setRotationSpeed(0.0);
			m_arm.setYSpeed(0.0);
		}
	}

	/**
	 * Grab one container from the step and drive to the auto zone.
	 */
	/*public void grabContainerFromStep() {

		double distance = m_drivebase.getLeftEncoderDistance();
		System.out.println(distance);

		if (m_step == 0) {
			m_drivebase.resetEncoders();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if (m_step == 1) {
			if (m_timer.get() < 1.0) {
				m_arm.goTo(Constants.ARM_FORWARDS, m_arm.getDartPot(), 0.66,
						0.0);
				m_arm.setClawSpeed(Constants.CLAW_OPEN);
			} else {
				m_arm.setClawSpeed(0.0);
				if (!m_arm.goTo(Constants.ARM_FORWARDS, m_arm.getDartPot(),
						0.66, 0.0)) {
					m_timer.stop();
					m_arm.setRotationSpeed(0.0);
					m_step++;
				}
			}
		} else if (m_step == 2) {

			// Drive backwards towards step
			if (distance < 12) {
				m_drivebase.setSpeed(0.33);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_drivebase.resetEncoders();
			}
		} else if (m_step == 3) {
			if (m_timer.get() < 0.25) {
				m_drivebase.setSpeed(-0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if (m_step == 4) {
			if (!m_arm.goTo(m_arm.getRotaryPot(), 0.6515, 0.0, 1.0)) {
				m_arm.setYSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if (m_step == 5) {
			// Grab the can
			if (m_timer.get() < 1.0) {
				m_arm.setClawSpeed(Constants.CLAW_CLOSE);
			} else {
				m_timer.stop();
				m_arm.setClawSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 6) {

			m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_EXTENDED, 0.0, 1.0);

			if (distance < 20) {
				m_drivebase.setSpeed(-0.5);
			} else {
				m_drivebase.setSpeed(0.0);
				m_step++;
			}
		} else if (m_step == 7) {
			// Continue raising arm up, go to next step when complete.
			if (!m_arm.goTo(m_arm.getRotaryPot(), Constants.DART_EXTENDED, 0.0,
					1.0)) { // && stopped) {
				m_step++;
			}
		} else {
			m_drivebase.setSpeed(0.0);  //DART : 0.72 LEFT
			//ROTARY: 0.423
			m_arm.setClawSpeed(0.0);  //3 feet    .6475    17 inches
			m_arm.setRotationSpeed(0.0);
			m_arm.setYSpeed(0.0);  //rotary:  RIGHT: 0.4765
			//Dart: .72
		}
	} */
	
	public void getCanFromStep(boolean left) {
		double distance = m_drivebase.getLeftEncoderDistance();
		
		if(m_step == 0) {
			m_drivebase.resetEncoders();
			m_timer.start();
			m_timer.reset();
			m_step++;
		} else if(m_step == 1) {
			if(m_timer.get() < 1.1) {
				m_arm.setClawSpeed(Constants.CLAW_OPEN);
			} else {
				m_arm.setClawSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if(m_step == 2) {
			double rotaryValue = 0.4765;
			if(left) {
				rotaryValue = 0.423;
			}
			if(!m_arm.goTo(rotaryValue, m_arm.getDartPot(), 0.5, 0.0)) {
				m_arm.setYSpeed(0.0);
				m_arm.setRotationSpeed(0.0);
				m_step++;
			}
		} else if(m_step == 3) {
			if(!m_arm.goTo(m_arm.getRotaryPot(), 0.6475, 0.0, 0.5)) {
				m_arm.setRotationSpeed(0.0);
				m_arm.setYSpeed(0.0);
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if(m_step == 4) {
			if(distance > -17) {
				m_drivebase.setSpeed(0.2);
			} else {
				m_step++;
			}
		} else if(m_step == 5) {
			if(distance <= -17 && distance >= -23) {
				m_drivebase.setSpeed(-0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_step++;
			}
		} else if(m_step == 6) {
			if(!m_arm.goTo(m_arm.getRotaryPot(), 0.72, 0.0, 0.5)) {
				m_arm.setRotationSpeed(0.0);
				m_arm.setYSpeed(0.0);
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if(m_step == 7) {  //19
			if(distance > -19) {
				m_drivebase.setSpeed(0.2);
			} else {
				m_step++;
			}
		} else if(m_step == 8) {
			if(distance <= -19 && distance >= -25) {
				m_drivebase.setSpeed(-0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_timer.start();
				m_timer.reset();
				m_step++;
			}
		} else if(m_step == 9) {
			if(m_timer.get() < 1.1) {
				m_arm.setClawSpeed(Constants.CLAW_CLOSE);
			} else {
				m_arm.setClawSpeed(0.0);
				m_timer.stop();
				m_step++;
			}
		} else if(m_step == 10) {
			if(!m_arm.goTo(m_arm.getRotaryPot(), 0.444, 0.0, 0.5)) {
				m_arm.setYSpeed(0.0);
				m_arm.setRotationSpeed(0.0);
				m_drivebase.resetEncoders();
				m_step++;
			}
		} else if(m_step == 11) {
			if(distance < 70) {
				m_drivebase.setSpeed(0.25);
			} else {
				m_step++;
			}
		} else if(m_step == 12) {
			if(distance >= 70 && distance <= 75) {
				m_drivebase.setSpeed(-0.1);
			} else {
				m_drivebase.setSpeed(0.0);
				m_step++;
			} 
		} else {
			m_drivebase.setSpeed(0.0);
			m_arm.setClawSpeed(0.0);
			m_arm.setYSpeed(0.0);
			m_arm.setRotationSpeed(0.0);
			m_elevator.setSpeed(0.0);
		}
	}
}
