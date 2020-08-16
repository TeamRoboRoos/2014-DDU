/*----------------------------------------------------------------------------*/
/* Copyright (c) RoboRoos 2014. All Rights Reserved.                          */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.*;

/**
 * The Input class is intended to handled joysticks and the control panel.
 * By abstracting out the code, we end up in a better place if we need to make 
 * modifications, as it is more likely that we only need to make changes in
 * the one place.
 *      
 * @author adam.jenkins@unisa.edu.au
 * @version 1.0
 */
public class Input 
{
    // Inputs
    private Joystick leftJoystick;
    private Joystick rightJoystick;
    private Joystick controlJoystick;
    private DriverStationEnhancedIO controlBoard;
    
    // Values for the different buttons on the button board (if working)
    //private int lowerCatapultID = 1;
    //private int shootID = 2;
    //private int retrieveBallID = 3;
    //private int lowerBasID = 4;
    //private int dropBallID = 5;
    //private int raiseBasID = 6;
    
    // Values for the different buttons on a third joystick
    private int shootID = 1;
    private int lowerCatapultID = 4;
    private int retrieveBallID = 2;
    private int dropBallID = 3;
    // Note that the BAS is raised and lowered with the joystick forward and back
    
    /**
     * Constructor - sets the two joysticks and the IO board.
     */
    public Input()
    {
        this.leftJoystick = new Joystick(1);
        this.rightJoystick = new Joystick(2);
        this.controlJoystick = new Joystick(3);
        
        this.controlBoard = DriverStation.getInstance().getEnhancedIO();
    }
    
    // Note: for various reasons, these return true if the button is up,
    // and false if it is down. Accordingly, in each case, I've reversed
    // the input. This should be revisited in the future, but is perfectly
    // fundtional for now.
    
    public boolean lowerCatapult() throws EnhancedIOException
    {
        boolean buttonState = false;
        
        // Grab the status of the shoot button.
        //buttonState = !this.controlBoard.getDigital(this.lowerCatapultID);
        buttonState = this.controlJoystick.getRawButton(this.lowerCatapultID);
     
        // Return the shoot button.
        return buttonState;
    }
    
    public boolean shoot() throws EnhancedIOException
    {
        boolean buttonState = false;
        
        // Grab the status of the shoot button.
        //buttonState = !this.controlBoard.getDigital(this.shootID);
        buttonState = this.controlJoystick.getRawButton(this.shootID);
     
        // Return the shoot button.
        return buttonState;
    }
    
    public boolean retrieveBall() throws EnhancedIOException
    {
        boolean buttonState = false;
        
        // Grab the status of the shoot button.
        //buttonState = !this.controlBoard.getDigital(this.retrieveBallID);
        buttonState = this.controlJoystick.getRawButton(this.retrieveBallID);
     
        // Return the shoot button.
        return buttonState;
    }
    
    public boolean lowerBAS() throws EnhancedIOException
    {
        boolean buttonState = false;
        
        // Grab the status of the shoot button.
        //buttonState = !this.controlBoard.getDigital(this.lowerBasID);
        buttonState = (this.controlJoystick.getY() < -0.5);
     
        // Return the shoot button.
        return buttonState;
    }
    
    public boolean dropBall() throws EnhancedIOException
    {
        boolean buttonState = false;
        
        // Grab the status of the shoot button.
        //buttonState = (!this.controlBoard.getDigital(this.dropBallID) ||
        buttonState =  this.controlJoystick.getRawButton(this.dropBallID); 
     
        // Return the shoot button.
        return buttonState;
    }
    
    public boolean raiseBAS() throws EnhancedIOException
    {
        boolean buttonState = false;
        
        // Grab the status of the shoot button.
        //buttonState = !this.controlBoard.getDigital(this.raiseBasID);
        buttonState = (this.controlJoystick.getY() > 0.5); 
     
        // Return the shoot button.
        return buttonState;
    }
    
    // Accessors
    public Joystick getLeftJoystick() { return this.leftJoystick; }
    public Joystick getRightJoystick() { return this.rightJoystick; }
}
