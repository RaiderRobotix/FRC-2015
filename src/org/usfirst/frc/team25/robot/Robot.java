
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
	static String dashStr = "<html>Autonoumous Chooser<br><br>0- Default<br>1- Pick up can from step<br>2- Two cans from field</html>";
	
    public void robotInit() {
    	m_autonController = AutonController.getInstance();
    	m_OI = OI.getInstance();
    	m_autonChooser = new SendableChooser();
    	m_autonChooser.addDefault("Do Nothing (default)", 0);
    	m_autonChooser.addObject("Move Forward", 1);
    	m_autonChooser.addObject("Push Tote to Auto Zone", 2);
    	m_autonChooser.addObject("Grab Container From Step", 3);
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
    		m_autonController.grabContainerFromStep();
    	}
    }

    /**
     * Initialization code for teleop mode should go here.
     */
    public void teleopInit() {
//    	m_OI.startTotePosition();
 //   	m_autonController.reset();
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
