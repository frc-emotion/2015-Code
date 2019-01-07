
package org.usfirst.frc.team2658.robot;



import edu.wpi.first.wpilibj.AnalogInput;
//import edu.wpi.first.wpilibj.;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//Driver Variables
	int leftDrivePort = 3; //The port of the Talon for the left wheels
	int rightDrivePort = 4; //The port of the Talon for the right wheels
	
	int leftJoystickPort = 0; //The port of the joystick for left controls
	int rightJoystickPort = 1; //The port of the joystick for right controls
	
	int driveDefaultExponent = 2; //The default setting for the drive exponent
	int driveExponent; //The actual drive exponent
	
	Joystick leftJoystick; //The joystick for the left controls
	Joystick rightJoystick; //The joystick for the right controls
	
	Talon rDrive, lDrive; //The motors themselves
	VictorSP victor;
	RobotDrive drive; //The entire drive (both joysticks)
	Encoder encoderA; // encoder a at port 3-4
	Encoder encoderB;// encoder b at port 1-2
	
	//Pneumatics Variables
	//Note: we may need to put more variables for multiple pneumatics
	int shiftButton = 7; //The button on the controller to use to shift gears
	int shiftPort1 = 0; //The port of the pneumatics
	int shiftPort2 = 1;
	Solenoid driveShift1; //The pneumatic itself
	Solenoid driveShift2;
	boolean shiftButtonCheck; //Used to check the button check of the pneumatics button
	
	double encoderBVal;
	double encoderAVal;
	
	//Gyro gyro;
	//CANTalon canTalon;
	ADXRS450_Gyro gyro;
	
	//Ultrasonic ultraSensor;
	AnalogInput analog;
	//DigitalOutput analogDO;
	
	//Method used to set the pneumatics on, High Gear
	public void ShiftHigh() {
    	driveShift1.set(true);
    	driveShift2.set(false);
    	SmartDashboard.putString("Gear", "High");
    }
	
	//Method used to set the pneumatics off, Low Gear
    public void ShiftLow() {
    	driveShift1.set(false);
    	driveShift2.set(true);
    	SmartDashboard.putString("Gear", "Low");
    }
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
    	rDrive = new Talon(rightDrivePort); //Initializes right Talon
    	lDrive = new Talon (leftDrivePort); //Initializes left Talon
    	victor = new VictorSP(5); //Init CAN Bus talon 
    	leftJoystick = new Joystick(leftJoystickPort); //Initializes left joystick
    	rightJoystick = new Joystick(rightJoystickPort); //Initializes right joystick
    	drive = new RobotDrive(lDrive, rDrive); //Creates the drive (two talons)
    	driveExponent = driveDefaultExponent; //Sets the drive exponent to the default one
    	SmartDashboard.putNumber("Driver Exponent", driveExponent); //Smartdashboard for drive exponent
    	SmartDashboard.putNumber("EncoderUnits", 1585);
    	
   
    	driveShift1 = new Solenoid(0, shiftPort1); //Initializes pneumatics
    	driveShift2 = new Solenoid(0, shiftPort2);
    	encoderA = new Encoder(5,4);
    	encoderB = new Encoder(0,1);
    	
    	//canTalon = new CANTalon(26);
    	
     	//gyro = new AnalogGyro();
    	gyro = new ADXRS450_Gyro();
    	gyro.calibrate();
     	SmartDashboard.putNumber("Gyro Angle", 0);
     	
    	//CameraServer server = CameraServer.getInstance();
    	//server.startAutomaticCapture("cam0");
    	//server.setQuality(1);
    	//server.setSize(10000);

    	//ultraSensor = new Ultrasonic(0,4);
    	//ultraSensor.setAutomaticMode(true);
    	
    	analog = new AnalogInput(0);
    	//analogDO = new DigitalOutput(4);
    	SmartDashboard.putNumber("Voltage", 0);
    	
    	
    	//SmartDashboard.putNumber("Ultra Sonic Inches", ultraSensor.getRangeInches());
    	SmartDashboard.putNumber("Encoder A", encoderA.getDistance());
    	SmartDashboard.putNumber("Encoder B", encoderB.getDistance());
    	
    	
    	ShiftLow(); //Start off in low gear
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	
    	
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	
    	
//    	while(encoderB.getDistance() < SmartDashboard.getNumber("EncoderUnits")){
//    		rDrive.set(0.487 * ((SmartDashboard.getNumber("EncoderUnits") - encoderB.getDistance())/SmartDashboard.getNumber("EncoderUnits")));
//    		lDrive.set(-0.388 * ((SmartDashboard.getNumber("EncoderUnits") - encoderB.getDistance())/SmartDashboard.getNumber("EncoderUnits")));
//    		
//
//    		encoderAVal = encoderA.getDistance();
//        	encoderBVal = encoderB.getDistance();
//        	SmartDashboard.putNumber("Encoder A", encoderAVal);
//        	SmartDashboard.putNumber("Encoder B", encoderBVal);
//    	}
    	
    	
    	
    	
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
    	//Simple Tank Drive
    	driveExponent = (int) SmartDashboard.getNumber("Driver Exponent"); //Get the set drive exponent
    	double left = Math.pow(leftJoystick.getRawAxis(1), driveExponent); //Get left joystick w/ drive exp
    	double right = Math.pow(rightJoystick.getRawAxis(1), driveExponent); //Get right joystick w/ drive exp
    	if (driveExponent % 2 != 0){ //Check to see if drive exp is even (or else all positive)
    		drive.tankDrive(left, right);
    	} else {
    		if (leftJoystick.getRawAxis(1) < 0) left *= -1;
    		if (rightJoystick.getRawAxis(1) < 0) right *= -1;
    		drive.tankDrive(left, right);
    	}
    	encoderAVal = encoderA.getDistance();
    	encoderBVal = encoderB.getDistance();
    	SmartDashboard.putNumber("Encoder A", encoderAVal);
    	SmartDashboard.putNumber("Encoder B", encoderBVal);
    	//SmartDashboard.putNumber("CanTemp", canTalon.getTemperature());
        
    	//Pneumatics Gear Shifts
    	if (leftJoystick.getRawButton(shiftButton)){ //Check to see if pneumatics button is pressed
    		if (!shiftButtonCheck){ //Check to see if pneumatics button was not being held down (doesn't spam)
    			if (driveShift1.get()){ //Shift High if Low and Low if High
    				ShiftLow();
    			}
    			else{
    				ShiftHigh();
    			}
    		}
    	}
    	if(leftJoystick.getRawButton(11)){
    		encoderA = new Encoder(4,5);
    		encoderB = new Encoder(0,1);
    	}
    	if (leftJoystick.getRawButton(9)){
    		victor.set(1.0);
    	}
    	else{
    		victor.set(0);
    	}
    	if(rightJoystick.getRawButton(9)){
    		
    		//canTalon.set(0.2);
    		//analogDO.
    		SmartDashboard.putNumber("Voltage", analog.getVoltage());
    	}
    	else{
    		//canTalon.set(0);
    	}
    	if(leftJoystick.getRawButton(1)){
        	/*CameraServer server1 = CameraServer.getInstance();
        	server1.setQuality(50);
        	server1.startAutomaticCapture("cam1");*/
    	}
    	if(leftJoystick.getRawButton(12)){
    		//gyro.reset();
    		//gyro.calibrate();
    		
    	}
    	shiftButtonCheck = leftJoystick.getRawButton(shiftButton); //For before, sets to check holding down button
    	SmartDashboard.putNumber("Gyro Angle", gyro.getAngle() );
    	//SmartDashboard.putNumber("Ultra Sonic Inches", ultraSensor.getRangeInches());
    	
    }
   
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
     
    }
    
}
