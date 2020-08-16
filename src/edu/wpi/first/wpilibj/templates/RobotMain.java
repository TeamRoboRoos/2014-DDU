/*----------------------------------------------------------------------------*/
/* Copyright (c) RoboRoos 2014. All Rights Reserved.                          */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package edu.wpi.first.wpilibj.templates;

//import edu.wpi.first.wpilibj.SimpleRobot;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.DriverStationEnhancedIO.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SimpleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotMain extends SimpleRobot
{
    // Main classes - these handled the major parts of the robot
    
    private Input input;                // Manages the controls.
    private Catapult catapult;          // Shoots the ball. Yay!
    private BallAcquisitionSystem bas;  // Picks up the ball.
    private DriveBase driveBase;       // Drives the robot. This is important. :)
    private Compressor compressor;      // The air compressor.
    
    private Configuration configuration;// Tracks the various parts of the robot.
    
    private boolean provideFeedback; // Switch to give console feedback.
    
    public void robotInit() 
    {
        // Normally left on. Sends messages to the console when debugging.
        this.provideFeedback = true;
        
        // Feedback to confirm that the robot has started initialising
        this.feedback("Robot initialising");
        
        // Stores all of the configuration details, such as what ports to use.
        this.configuration = new Configuration();
        
        // Creates a reference to the compressor.
        this.compressor = new Compressor(1,1);
        
        // Set up the BAS
        this.bas = new BallAcquisitionSystem(this);
        
        // Ready the catapult, squire.
        this.catapult = new Catapult(this);
        
        // Instantiate the input - this covers the joysticks and control board.
        this.input = new Input();
        
        // The motors and wheels and stuff.
        this.driveBase = new DriveBase(this);
        
        // Just in case there's something we need to generally do. Not currently
        // used.
        this.enable();
        
        // Feedback to confirm that the robot has finished initialising
        this.feedback("Initialising done");
    }
    
    /**
     * Not currently used. Gets called at the start of each mode.
     */
    public void enable() 
    {
    }
    
    /**
     * Autonomous mode - the robot is controlling itself.
     */
    public void autonomous() 
    {
        // Just in case we need this later.
        this.enable();
        
        // Start the compressor.
        this.compressor.start();
        
        // Feedback to the console, letting the user know where we are.
        this.feedback("Entering autonomous");
        
        // This drives forward at half speed for 1.5 second.
        // Changing the value will change the length of time it drives for.
        this.driveBase.driveForward(4);
        
        // Shoot the ball. If it hasn't had the BAS lowered, it won't shoot.
        // Accordingly, we keep trying until it does. (The BAS should start
        // lowering automagically).
        boolean shotBall = false;
        while (!shotBall)
        {
            shotBall = this.catapult.shoot();
        }
        
        // Things to do when ending teleop mode
        this.compressor.stop();     // Stop the compressor
        
        // Feedback to the console
        this.feedback("Exited autonomous");
    }
    
    /**
     * The operator is now in charge.
     */
    public void operatorControl() 
    {
        this.enable();
        
        // Inform the console that this is under operator control
        this.feedback("Entering teleop");
        
        // Start the compressor.
        this.compressor.start();
        
        // Keep looking while under operator control.
        while (this.isOperatorControl()) 
        {
            // Drive based on joystick control.
            this.driveBase.Drive();
            
            // Various possible user inputs
            try
            {
                // Note that the "else" matters - this is to make sure
                // that only one thing can happen at a time.
                
                if (this.input.lowerBAS() == true)
                {
                    this.feedback("Lowering the BAS");
                    this.bas.lower();
                }
                
                else if (this.input.raiseBAS() == true)
                {
                    this.feedback("Raising the BAS");
                    this.bas.raise();
                }
                
                else if (this.input.shoot() == true)
                {
                    this.feedback("Shooting the ball");
                    this.catapult.shoot();
                }
                
                else if (this.input.lowerCatapult() == true)
                {
                    this.feedback("Lowering the catapult");
                    this.catapult.lower();
                }
                
                else if (this.input.dropBall() == true)
                {
                    this.feedback("Dropping the ball");
                    this.bas.dropBall();
                }
                
                else if (this.input.retrieveBall() == true)
                {
                    this.feedback("Retrieving the ball");
                    this.bas.fetchBall();;
                }
                
                // If it isn't clear that we want the ball retrieval motor 
                // running, it should be stopped.
                else if (this.input.retrieveBall() == false && this.input.dropBall() == false)
                {
                    this.bas.stop();
                }
                
                // These should be left commented out They are for debugging,
                // and let the console know the status of reed switches on the
                // pistons.
                //if (this.bas.isLowered()) this.feedback("BAS down");
                //if (this.bas.isRaised()) this.feedback("BAS up");
                //if (this.catapult.isLowered()) this.feedback("Catapult down");
            }
            catch (EnhancedIOException e)
            {
                this.feedback("*** ERROR \n" + e.getMessage());
            }
            
        }
        
        // Things to do when ending teleop mode
     
        this.compressor.stop();     // Stop the compressor
        
        // Inform the console that this has exited operator control
        this.feedback("Exited teleop");
    }
    
    /**
     * Test mode.
     */
    public void test() 
    {
        this.enable();
        
        // Feedback to the console
        this.feedback("Entering test");
        
        this.compressor.start();
        
        this.bas.lower();
        
        try
        {
            while (this.input.raiseBAS() == false)
            {
                this.feedback("Compressor loading");
            }
            this.bas.raise();
        }
        catch (EnhancedIOException ie)
        {
            this.feedback("*** ERROR\n" + ie.getMessage());
        }
        
        this.compressor.stop();
        
        // Feedback to the console
        this.feedback("Exited test");
    }
    
    // Accessors
    
    /**
     * Returns a reference to the robot's input (manages joysticks, etc).
     * @return Input
     */
    public Input getInput()
    {
        return this.input;
    }
    
    /**
     * Returns a reference to the robot's drive base.
     * @return Input
     */
    public DriveBase getDriveBase()
    {
        return this.driveBase;
    }
    
    /**
     * Returns a reference to the robot's catapult.
     * @return Catapult
     */
    public Catapult getCatapult()
    {
        return this.catapult;
    }
    
    /**
     * Returns a reference to the robot's Ball Acquisition System (BAS).
     * @return Compressor
     */
    public BallAcquisitionSystem getBAS()
    {
        return this.bas;
    }
    
    /**
     * Returns a reference to the robot's air compressor.
     * @return Compressor
     */
    public Compressor getCompressor()
    {
        return this.compressor;
    }
    
    /**
     * Returns a reference to the configuration manager.
     * @return Compressor
     */
    public Configuration getConfiguration()
    {
        return this.configuration;
    }
    
    /**
     * Simple feedback class - sends a message to the Driver Station, and 
     * if provideFeedback is on, to the console.
     */
    public void feedback(String message)
    {
        // Clear the screen
        DriverStationLCD.getInstance().clear();
        
        // Output the message
        DriverStationLCD.getInstance().println(DriverStationLCD.Line.kUser1, 2, message);
        
        // Send a duplicate message to the console
        if (this.provideFeedback) System.out.println(message);
        
        // Update the screen
        DriverStationLCD.getInstance().updateLCD();
    }
 
}
