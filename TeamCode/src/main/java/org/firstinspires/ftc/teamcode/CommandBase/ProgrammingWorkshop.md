# Programming Workshop Notes
- Commit early and commit often

## Questions
- How to fork multiple robot controllers/road runner -> Import repository and copy FTC Road Runner url. You can also download it, but...

## OpMode
- OpMode and LinearOpMode have access to HardwareMap, Telemetry, and Gamepad
- getPosition() on a servo returns the position that you sent it
- servo programmer tells a servo its limits

## Servo Tester Program
- Loop() runs hundreds of times per second
- Make before comps
- Axons do have encoders you can get the position of
~~~ java
// In loop()
// Makes the servo go up and down until it's the pos you want it
gripServoPosition += -gamepad1.left_stick_y * .001;
gripServoLeft.setPosition(gripServoPosition);
telemetry.addData("Grip Servo Position", gripServoPosition);
~~~
- Then, in code, create servoPos variables that you got from the tester class
~~~ java
// These servos are mounted in opposite directions
// Left servo closed: 0.8
// Left servo open: 0.308

// Right servo closed: 0.18
// Right servo open: 0.66

// Use a toggle for buttons, due to the fact that button values change hundreds of times in the loop
if (gamepad1.a && !clawButtonIsPressed) {
    gripClosed = !gripClosed;
    clawButtonIsPressed = true;
}
if (!gamepad1.a) {
    clawButtonIsPressed = false;
}

if (gripClosed) {
    gripServoLeft.setPosition(0.8)
    gripServoRight.setPosition(0.18)
else {
    gripServoLeft.setPosition(0.308)
    gripServoRight.setPosition(0.66)
}
~~~
- Programming the actual arm
~~~ java
private ElapsedTime intermediateDelay = new ElapsedTime();
public void update() {
    switch(transportPos) {
        case INTAKING:
        // Arm down, axons up
            axonLeft.setPosition(.313);
            axonRight.setPosition(0.313);
            clawServo.setPosition(0.947);
            if (gamepad1.right_bumper) {
                transportPos = TransportPos.PLACING;
            }
            break;
        case PLACING:
            axonLeft.setPosition(.7052);
            axonRight.setPosition(0.7052);
            clawServo.setPosition(0.4);
            if (gamepad.left_bumper) {
                transportPos = TransportPos.INTERMEDIATE;
                intermediateDelay.reset(); // timer counts up until you reset again
            }
            break;
        case INTERMEDIATE: 
            if (intermediate,milliseconds() > 700) {
                transportPos = TransportPos.PLACING;
            }
            axonLeft.setPosition(0.4);
            axonRight.setPosition(0.4);
            clawServo.setPosition(0.7);
            // add a timer to transition, plug in random numbers for servo pos
            
            break;
            
private enum TransportPos{
// Intermediate to prevent slamming on the table
    INTAKING, INTERMEDIATE, PLACING
}
 ~~~

## FTCLib
- Subsystem
  - A collection of mechanisms
  - Stores information about the mechanism and acts as an interface between hardware and software
  - Grip, claw, arm, etc.
  - Low level functions
- Command
  - Instruction that controls one or more mechanisms
  - Infinite different ways to organize command-subsystem balance
  - More high-level, complicated use of subsystem
~~~ java
aButton.whenPressed(intake::run); // only when no params
adjustDownButton.whenPressed(new InstantCommand(() -> transport.adjustDown(), transport());
~~~
- Command Structure
  - initialize(): once at scheduling
  - execute(): every loop cycle until end
  - end(): once descheduling
  - isFinished(): returns whether the command is finished
- The Command Scheduler
  - Runs all the loops and subsystem commands
  - kind of like the loop() in teleop, you can schedule a check to happen
~~~ java
exampleButton.whenPressed(new InstantCommand(() -> {
    // your implementation of Runnable.run() here
}));
~~~
- Gamepad Wrapper
  - extends the functionality of a gamepad, connects to the scheduler
- Default commands always run and there's no start condition