/*----------------------------------------------------------------------------*/
/* Copyright (c) RoboRoos 2014. All Rights Reserved.                          */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 * This is fairly self evident. It is the catapult, and it shoots the ball. Yay!
 * It will retract the catapult and fire it. Significantly, it will also have 
 * safety checks to confirm that everything is ready to go.
 * 
 * @author adam.jenkins@unisa.edu.au
 * @version 1.0
 */
public class Catapult 
{
    private RobotMain robot;                // Reference to the main robot class
      
    // A reed switch that will be true if the catapult is down and ready to fire.
    // The switch was broken when testing, so it is set but not used. If it works, 
    // uncomment the code that checks it's status below.
    private DigitalInput reloaded; 
    
    // Solinoid that controls the piston. When set to true, the catapult will
    // fire. Setting to false should reload the catapult.
    private Solenoid pneumatics;
    
    // ignoreChecks. This should not be turned on. If it is true, it is possible
    // for the cataplut to fire with the BAS raised. It might be necessary to do
    // this, so I'm allowing for the possibility, but that would require manual 
    // confirmation of it's status before firing.
    private boolean ignoreChecks = false;  
    
    // Time at which the catapult has fired need to wait 1 sec to lower.
    private long start_time = 0;
    
    // Time conter since catapult has fired need to wait 1 sec to lower.
    private long shoot_time = 0;
    
    // Flag to lower the catapult
    private boolean lower_catapult = true;
    
    /**
     * Constructor for the catapult. Records the base robot class and sets up 
     * the relay switch and piston relay.
     * @param robot the base robot class.
     */
    public Catapult(RobotMain robot)
    {
        // Store the instance of the robot class.
        this.robot = robot;
        
        // Set up the digitalinput for the relay switch and the solenoid for the
        // penumatics. In each case, grabe the ports from the Configuration
        // class.
        this.reloaded = new DigitalInput(this.robot.getConfiguration().getCatapultReloaded());
        this.pneumatics = new Solenoid(this.robot.getConfiguration().getCatapultPneumatics());
    }
    
    /**
     * Fire the catapult. Only fire if the BAS is lowered. If it is not
     * lowered, lower it before shooting.
     * @return if it did shoot
     */
    public boolean shoot()
    {
        /*if (this.ignoreChecks || )
        
        
        
        */
        // Default to didn't shoot
        boolean didShoot = false;
        
        if (this.ignoreChecks == false)
        {
            // Is the BAS raised? If it is, we can shoot.
            if(this.robot.getBAS().isLowered() == true)
            {
                // Is the catapult set? As the reed switch was broken,
                // this is commented out. If it works we should add it back in
                // again.
                //if (this.isLowered())
                //{
                    this.pneumatics.set(true);
                    didShoot = true;
                //}
                //else
                //{
                //  this.robot.feedback("Catapult is not retracted");
                //}
            }
        
            // If the BAS is not lowered, lower it ourselves.
            else
            {
                this.robot.getBAS().lower();
            }
        }
        
        // Alternative - just shoot. This is only to be used if desperate.
        else
        {
            this.pneumatics.set(true);
            didShoot = true;    
        }
        
        // Let the calling class know that the catapult successfully fired.
        return didShoot;
    }
    
    /**
     * This simply lowers the catapult. It could be done automatically,
     * but at the moment it needs to be manually triggered.
     */
    public void lower()
    {
        // "False" causes the piston to retract.
        this.pneumatics.set(false);
    }
    
    /**
     * Checks to see if the reed switch indicates that the piston has retracted,
     * showing that the catapult is in the "ready to fire" position.
     * @return true if the catapult has been lowered into position
     */
    public boolean isLowered()
    {
        return this.reloaded.get();
    }
    
    public boolean update_timer()
    {
        return false;
    }
}
