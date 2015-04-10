
package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	
	SendableChooser m_autonChooser;
	AutonController m_autonController;
	OI m_OI;
	int m_autonPicked = 1;
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */

    public void robotInit() {
    	m_autonController = AutonController.getInstance();
    	m_OI = OI.getInstance();
    	m_autonChooser = new SendableChooser();
    	m_autonChooser.addDefault("Do Nothing (default)", 0);
    	m_autonChooser.addObject("Move Forward", 1);
    	m_autonChooser.addObject("Push Tote to Auto Zone", 2);
    	m_autonChooser.addObject("Push container to Auto Zone (robot is backwards)", 3);
    	//m_autonChooser.addObject("Grab Container From Step", 4);
    	m_autonChooser.addObject("Bring one Tote and Can to Auto Zone", 5);
    	m_autonChooser.addObject("Arm bring one can to auto zone", 6);
    	m_autonChooser.addObject("Pick up can (To noodle height)", 9);
    	m_autonChooser.addObject("Get ready with can for teleop", 10);
    	m_autonChooser.addObject("Step set up", 11);
    	m_autonChooser.addObject("test", 12);
    	m_autonChooser.addObject("Pick from step", 13);
    	SmartDashboard.putData("Choose Auton mode: ", m_autonChooser);
    }
    
    /**
     * Initialization code for disabled mode should go here.
     */
    public void disabledInit() {
    }
    
    /**
     * Periodic code for disabled mode should go here.
     */
    public void disabledPeriodic() {
    }

    /**
     * Initialization code for autonomous mode should go here.
     */
    public void autonomousInit() {
    	m_autonPicked = (int) m_autonChooser.getSelected();
    	System.out.println("Auton Picked: " + m_autonPicked);
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	if(m_autonPicked == 1) {
    		m_autonController.driveToAutoZone();
    	} else if(m_autonPicked == 2) {
    		m_autonController.pushToteToAutoZone();
    	} else if(m_autonPicked == 3) {
    		m_autonController.pushContainerToAutoZone();
    	} else if(m_autonPicked == 4) {
    		//m_autonController.grabContainerFromStep();
    	} else if(m_autonPicked == 5) {
    		m_autonController.toteAndCanToAutoZone();
    	} else if(m_autonPicked == 6) {
    		m_autonController.armGrabOneCan();
    	} else if(m_autonPicked == 7) {
    		m_autonController.getCanFromStep(true);  //left
    	} else if(m_autonPicked == 8) {
    		m_autonController.getCanFromStep(false);  //right
    	} else if(m_autonPicked == 9) {
    		m_autonController.pickUpCan();
    	} else if(m_autonPicked == 10) {
    		m_autonController.getOneCanReady();
    	} else if(m_autonPicked == 11) {
    		m_autonController.dropArm();
    	} else if(m_autonPicked == 12) {
    		m_autonController.test();
    	} else if(m_autonPicked == 13) {
    		m_autonController.newPickUpFromStep();
    	}
    }

    /**
     * Initialization code for teleop mode should go here.
     */
    public void teleopInit() {
    	m_autonController.reset();
    	SmartDashboard.putNumber("Rotary Pot Limit (Arm Over Cage)", Constants.ARM_BACKWARDS);
    	SmartDashboard.putNumber("Rotary Left Limit", Constants.LEFT_LIMIT);
    	SmartDashboard.putNumber("Rotary Pot Limit (Arm Behind Robot)", Constants.ARM_FORWARDS);
    	SmartDashboard.putNumber("Rotary Right Limit", Constants.RIGHT_LIMIT);
    }
    
    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	m_OI.enableTeleopControls(); 
    }
    
    /**
     * Initialization code for test mode should go here.
     */
    public void testInit() {
    	
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    	
    }
    
}
