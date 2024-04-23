package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

// TODO LIST
// Put the bucket functions in the intake into here

public class BucketSubsystem {
    private final Servo bucketAxon;
    private final Servo bucketServoTop;
    private final Servo bucketServoBottom;
    private final Servo flipAxonLeft;
    private final Servo flipAxonRight;

    public BucketSubsystem(final Servo bucketAxon, final Servo bucketServoTop, final Servo bucketServoBottom,
                           final Servo flipAxonLeft, final Servo flipAxonRight) {
        this.bucketAxon = bucketAxon;
        this.bucketServoTop = bucketServoTop;
        this.bucketServoBottom = bucketServoBottom;
        this.flipAxonLeft = flipAxonLeft;
        this.flipAxonRight = flipAxonRight;
    }
// TODO: Implement this when you do teleop for outtake
//    public BucketSubsystem(OuttakeHardware outtakeHardware) {
//
//    }


}
