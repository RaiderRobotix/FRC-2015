
package org.usfirst.frc.team25.robot;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	OI m_OI;
	
	/**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	m_OI= OI.getInstance();
    	
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
    	
    }
    
    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {

    }

    /**
     * Initialization code for teleop mode should go here.
     */
    public void teleopInit() {
    	
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
