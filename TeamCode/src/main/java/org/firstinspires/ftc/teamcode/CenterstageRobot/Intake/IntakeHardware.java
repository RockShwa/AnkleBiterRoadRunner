package org.firstinspires.ftc.teamcode.CenterstageRobot.Intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

// This is for the purpose of encapsulating some of the hardware initializing in teleop
public class IntakeHardware {
    public Servo intakeAxonServo;
    public DcMotorEx intakeRollerMotor;
    public Servo topBucketServo;
    public Servo bottomBucketServo;

    public IntakeHardware(HardwareMap hwMap) {
        intakeAxonServo = hwMap.get(Servo.class, "intake_servo");
        intakeRollerMotor = hwMap.get(DcMotorEx.class, "intake_motor");
        topBucketServo = hwMap.get(Servo.class, "top_bucket_servo");
        bottomBucketServo = hwMap.get(Servo.class, "bottom_bucket_servo");
    }
}
