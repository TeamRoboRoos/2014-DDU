/*----------------------------------------------------------------------------*/
/* Copyright (c) RoboRoos 2014. All Rights Reserved.                          */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;

/**
 * Configuration contains the references to the various electronic parts and
 * their ports. Abstracted out so that it can be handled from the one location
 * if changes are required.
 * 
 * This is a very longwinded way of handling things. But the intent is to make 
 * the code as clear as possible, so "wordy" is good. 
 * 
 * @author adam.jenkins@unisa.edu.au
 * @version 1.0
 */
public class Configuration 
{
    // Compressor settings
    private int compressorFull = 1;
    
    // BAS settings
    private int basRaised = 2;              // Reed switch for extended piston   
    private int basLowered = 4;             // Reed switch for retracted piston
    private int basMotor = 6;               // Victor motor driver
    private int basUp = 2;                  // BAS solonoid up
    private int basDown = 3;                // BAS solonoid down        
    
    // I expect that both of these may need adjustments.
    private double basRetrieveBallSpeed = 1.0;  // Turn the motor to pick up the ball
    private double basDropBallSpeed = -1.0;     // Dropping the ball requires the motor to be reversed.
    
    // Drive settings
    private int leftDriveMotor1 = 1;        // Talon
    private int leftDriveMotor2 = 2;        // Talon
    private int rightDriveMotor1 = 3;       // Talon
    private int rightDriveMotor2 = 4;       // Talon
    
    // Catapult settings
    private int catapultReloadedSwitch = 3; // Reed switch for retracted piston
    private int catapultPneumatics = 1;     // Solonoid for extending piston
    
    // These shouldn't need to be touched unless new components are added. This 
    // is a basic set of accessors - avoids making the instance variables public,
    // as that opens them up to problems.
    
    public int getCompressorSwitch() { return this.compressorFull; }
    
    public int getBASLowered() { return this.basLowered; }
    public int getBASRaised() { return this.basRaised; }
    public int getBASMotor() { return this.basMotor; }
    public int getBASUp() { return this.basUp; }
    public int getBASDown() { return this.basDown; }
    public double getBASDropBallSpeed() { return this.basDropBallSpeed; }
    public double getBASRetrieveBallSpeed() { return this.basRetrieveBallSpeed; }
    
    public int getLeftDriveMotor1() { return this.leftDriveMotor1; }
    public int getLeftDriveMotor2() { return this.leftDriveMotor2; }
    public int getRightDriveMotor1() { return this.rightDriveMotor1; }
    public int getRightDriveMotor2() { return rightDriveMotor2; }
    
    public int getCatapultReloaded() { return this.catapultReloadedSwitch; }
    public int getCatapultPneumatics() { return this.catapultPneumatics; }
}
