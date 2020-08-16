/*----------------------------------------------------------------------------*/
/* Copyright (c) RoboRoos 2014. All Rights Reserved.                          */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 * DriveBase is mostly to configure the base so that the robot can move. 
 * Moving is good. Not moving is bad. This should contain methods to move for
 * a set length of time in order to assist with autonomous mode.
 * 
 * @author adam.jenkins@unisa.edu.au
 * @version 1.0
 */
public class DriveBase 
{
    private RobotMain robot;            // Reference to the main robot class
    
    private RobotDrive driveBase;       // Drives the robot. This is important. :)
    
    /**
     * Constructor - sets up the RobotDrive using the details grabbed from
     * configuration.
     * @param robot The main robot class
     */
    public DriveBase(RobotMain robot)
    {
        // Store the main robot class
        this.robot = robot;
        
        // Set up the drive base
        
        // We're using four Talon motors, two on each side.
        this.driveBase = new RobotDrive(
                new Talon(this.robot.getConfiguration().getLeftDriveMotor1()), 
                new Talon(this.robot.getConfiguration().getLeftDriveMotor2()), 
                new Talon(this.robot.getConfiguration().getRightDriveMotor1()), 
                new Talon(this.robot.getConfiguration().getRightDriveMotor2()));
        
        // Inverts the direction of the motor. Not particularly meaningful with 
        // this robot - can be changed as required by the drivers.
        this.driveBase.setInvertedMotor(RobotDrive.MotorType.kFrontLeft, false);
        this.driveBase.setInvertedMotor(RobotDrive.MotorType.kRearLeft, false);
        this.driveBase.setInvertedMotor(RobotDrive.MotorType.kFrontRight, false);
        this.driveBase.setInvertedMotor(RobotDrive.MotorType.kRearRight, false);
        
        // Zeros the drive, making sure it has no power.
        this.driveBase.arcadeDrive(0.0, 0.0);
    }
    
    /**
     * Drive the robot in tank drive using two joysticks. The joysticks are 
     * grabbed from the Input class. This is called when under operator control.
     */
    public void Drive()
    {
        driveBase.tankDrive(this.robot.getInput().getLeftJoystick(), this.robot.getInput().getRightJoystick(), true);
    }
    
    /**
     * Drives the robot in a straight line at half power for a specified number
     * of seconds.
     * @param time  Number of seconds to drive for.
     */
    public void driveForward(double time)
    {
        // By default, drives forward at half speed.
        this.drive(0.5, 0.0, time);
    }
    
    /**
     * Allows the speed and duration to be set, drives at the specified
     * power (between -1 and 1) for the specified time. Providing a negative
     * value drives in reverse.
     * @param speed the power, between -1 and 1.
     * @param time the time to drive for in seconds.
     */
    public void driveForward(double speed, double time)
    {
        this.drive(speed, 0, time);
    }
    
    /**
     * Drives the robot at the specified speed, angle and for the specified
     * time. I don't expect it to be called directly, as in autonomous mode 
     * we only want to drive forward, but it is here in case it proves
     * useful. Coupled with other features, such as a vision system, it could
     * be very useful.
     * @param speed the power to drive at, between -1 and 1
     * @param angle value for the y axis, handles rotation, between -1 and 1.
     * @param time  the number of seconds to drive for.
     */
    public void drive(double speed, double angle, double time)
    {
        // Ugly solution, but functional dor now. First we grab the current time.
        long stoptime = System.currentTimeMillis() + 1;
        
        // Short pause - 1 millisecond.
        while (System.currentTimeMillis() < stoptime) {}
        
        // Set how long to drive for. As the time is in seconds, but we're using
        // millisecond, the time is multipled by 1000. 
        // (1 second = 1000 milliseconds).
        stoptime = System.currentTimeMillis() + (long)(time * 1000);
        
        // Active loop. Keep looping for as long as the current time is less
        // than the target time.
        while (System.currentTimeMillis() < stoptime) 
        {
            // Drive forward.
            this.driveBase.arcadeDrive(speed, angle);
        }
        
        // Now that the step time has passed, turn off the motors.
        this.driveBase.arcadeDrive(0,0);
    }
}
