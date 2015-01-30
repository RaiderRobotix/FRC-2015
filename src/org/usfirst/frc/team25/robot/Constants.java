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

	// Joystick Ports
	final static int LEFT_JOYSTICK_PORT = 0;
	final static int RIGHT_JOYSTICK_PORT = 1;
	final static int OPERATOR_JOYSTICK_PORT = 2;
	final static double JOYSTICK_DEADBAND = 0.1;

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
/*
 * TODO: -Two Half CIMS {Lift, Button Up and Down} -
 * 
 * 
 * -
 */