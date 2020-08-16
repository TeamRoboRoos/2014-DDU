/*----------------------------------------------------------------------------*/
/* Copyright (c) RoboRoos 2014. All Rights Reserved.                          */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 * BallPickerUppper handles the BAS. It lowers the bar down, starts the motor
 * and lifts it up once the ball is grabbed. The BAS will need to be lowered
 * to shoot.
 * 
 * @author adam.jenkins@unisa.edu.au
 * @version 1.0
 */
public class BallAcquisitionSystem 
{
    private RobotMain robot;        // Reference to the main robot class
    
    private DigitalInput lowered;   // Reed switch that is true if the BAS is lowered
    private DigitalInput raised;    // Reed switch that is true if the BAS is raised
    
    // Normally we would use a double solenoid to do this, but that appeared
    // broken during testing. So this uses two single solenoids instead. The 
    // code should be careful to never turn both on.
    private Solenoid pneumaticsLower; // Solenoid to raise or lower the BAS
    private Solenoid pneumaticsRaise; // Solenoid to raise or lower the BAS
    
    // The motor controller that drives the ball pickup system
    private Victor roller;
    
    /**
     * Basic constructor. Records a reference to the main robot class, and
     * initialises the various components.
     * @param robot the main robot class
     */
    public BallAcquisitionSystem(RobotMain robot)
    {
        this.robot = robot;
        
        // Set up the reed switches
        this.lowered = new DigitalInput(this.robot.getConfiguration().getBASLowered());
        this.raised = new DigitalInput(this.robot.getConfiguration().getBASRaised());
        
        // Set up the roller motor
        this.roller = new Victor(this.robot.getConfiguration().getBASMotor());
        
        // Set up the pneumatics.
        this.pneumaticsLower = new Solenoid(this.robot.getConfiguration().getBASDown());
        this.pneumaticsRaise = new Solenoid(this.robot.getConfiguration().getBASUp());
    }
    
    /**
     * Lowers the BAS and turns on a motor in order to collect the ball.
     */
    public void fetchBall()
    {
        // We need to lower the BAS to fetch a ball. The first step is to 
        // check if it is lowered, and lower it if not.
        if (this.isLowered() == false)
        {
            this.lower();
        }
        
        // It is lowered, so start the motor. The speed is grabbed from the
        // configuration class.
        else
        {
            this.roller.set(this.robot.getConfiguration().getBASRetrieveBallSpeed());
        }
    }
    
    /**
     * Lowers the BAS.
     */
    public void lower()
    {
        // If it hasn't already been lowered...
        if (this.isLowered() == false)
        {
            // ... set the pnematics. As we are using two solenoids, rather than
            // the doublesolenoid (something which could be fixed) I make sure 
            // that the raise one is turned off before turning on the "lower" 
            // relay.
            this.pneumaticsRaise.set(false);
            this.pneumaticsLower.set(true);
        }
        // If it is fully lowered, turn both off. Only one shoudl be necessary,
        // but there is no harm in making sure.
        else
        {
            this.pneumaticsRaise.set(false);
            this.pneumaticsLower.set(false);
        }
    }
    
    /**
     * Raises the BAS.
     */
    public void raise()
    {
        // As before, check to see if it is already upright. If not ...
        if (this.isRaised() == false)
        {
            // ... turn oof the lowering solenoid and ...
            this.pneumaticsLower.set(false);
            // ... turn on the lifting one.
            this.pneumaticsRaise.set(true);
        }
        
        // If it is fully raised, turn off both solenoids.
        else
        {
            this.pneumaticsLower.set(false);
            this.pneumaticsRaise.set(false);
        }
    }
    
    /**
     * Turns off the motor. Called by default if you aren't using it.
     */
    public void stop()
    {
        // A quick check to see if it is turned on.
        if (this.roller.get() != 0)
        {
            // If it is, stop it.
            this.roller.set(0);
        }
    }
    
    /**
     * Dropping the ball is easy - reverse the rollers while the BAS is upright.
     */
    public void dropBall()
    {
        // If it isn't upright already, make it so.
        if (this.isRaised() == false)
        {
            this.raise();
        }
        
        // Start the BAS motor, using the speed set in the configuration class.
        else
        {
            this.roller.set(this.robot.getConfiguration().getBASDropBallSpeed());
        }
    }
    
    /**
     * Returns whether or not the BAS is lowered. The BAS is lowered if  and 
     * only if, the reed switch for "lowered" is true, and the reed switch for
     * "raised" is false. 
     * @return is lowered
     */
    public boolean isLowered()
    { 
        boolean isLowered = false;
        
        // Check to see if it is properly lowered
        if (this.lowered.get() == true && this.raised.get() == false)
        {
            isLowered = true;
        }
        
        return isLowered;
    }
    
    /**
     * Returns whether or not the BAS is raised. The BAS is raised if, and 
     * only if, the reed switch for "raised" is true, and the reed switch for
     * "lowered" is false. 
     * @return is raised
     */
    public boolean isRaised()
    { 
        boolean isRaised = false;
        
        // Check to see if it is properly raised
        if (this.lowered.get() == false && this.raised.get() == true)
        {
            isRaised = true;
        }
        
        return isRaised;
    }
}
