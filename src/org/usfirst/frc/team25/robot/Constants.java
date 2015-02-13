package org.usfirst.frc.team25.robot;

public class Constants {

	// Left Drive PWM Constants
		final static int LEFT_DRIVE_PWM1 = 2;
		final static int LEFT_DRIVE_PWM2 = 3;

		// Right Drive PWM Constants
		final static int RIGHT_DRIVE_PWM1 = 0;
		final static int RIGHT_DRIVE_PWM2 = 1;

		// Elevator PWM Contstants
		final static int LEFT_ELEVATOR_PWM = 4;
		final static int RIGHT_ELEVATOR_PWM = 5;
		final static int STRING_POT_PWM = 0;

		// Arm PWM Constants
		final static int ARM_CLAW_PWM = -1;  // TODO fix these
		final static int ARM_ROTATION_PWM = -1;
		final static int ARM_Y_AXIS_PWM = -1;
		
		// Elevator Pot Constants
		final static double ELEVATOR_UPPER_LIMIT = 0.345;
		final static double ELEVATOR_LOWER_LIMIT = 0.9519;  //.953- original
		final static double ELEVATOR_HOVER_VALUE = 0.855;
		final static double TOTE_CATCHING_POSITION = 0.8163;
		final static double ALLOWED_DEVIATION = 0.0005;
		
		//elevator constant speeds
		final static private double ELEVATOR_SPEED = 1.0;
		final static double ELEVATOR_UP = -ELEVATOR_SPEED;
		final static double ELEVATOR_DOWN = ELEVATOR_SPEED;
		
		// Joystick Ports
		final static int LEFT_JOYSTICK_PORT = 0;
		final static int RIGHT_JOYSTICK_PORT = 1;
		final static int OPERATOR_JOYSTICK_PORT = 2;
		final static double JOYSTICK_DEADBAND = 0.1;
		final static double TWIST_DEADBAND = 0.1;

		// ENCODER DIGITAL I/O PORT CONSTANTS
		final static int LEFT_ENCODER_A = 0;
		final static int LEFT_ENCODER_B = 1;
		final static int RIGHT_ENCODER_A = 2;
		final static int RIGHT_ENCODER_B = 3;

		final static double TIRE_CIRCUMFERENCE = 28.27431; // in inches
		final static double COUNTS_PER_REVOLUTION = 85.5; // encoder ticks per
															// revolution - gear
															// reduction
		final static double INCHES_PER_COUNT = TIRE_CIRCUMFERENCE
				/ COUNTS_PER_REVOLUTION;
	
}