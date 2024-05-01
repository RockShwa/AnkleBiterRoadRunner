package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.util.Encoder;

// TODO LIST
// Put the bucket functions in the intake into here (the reset servo ones)

public class BucketSubsystem extends SubsystemBase {
    private final Servo wristAxon;
    private final Servo bucketServoTop;
    private final Servo bucketServoBottom;
    private final Servo flipAxonLeft;
    private final Servo flipAxonRight;

    public BucketSubsystem(final Servo wristAxon, final Servo bucketServoTop, final Servo bucketServoBottom,
                           final Servo flipAxonLeft, final Servo flipAxonRight) {
        this.wristAxon = wristAxon;
        this.bucketServoTop = bucketServoTop;
        this.bucketServoBottom = bucketServoBottom;
        this.flipAxonLeft = flipAxonLeft;
        this.flipAxonRight = flipAxonRight;
    }
// TODO: Implement this when you do teleop for outtake
//    public BucketSubsystem(OuttakeHardware outtakeHardware) {
//
//    }
    public void wristToFull() {
        // this would change for an actual robot, 1 is an obscure value
        wristAxon.setPosition(1);
    }

    public void flipWrist() {
        // this would change for an actual robot, 1 is an obscure value
        flipAxonRight.setPosition(1);
        flipAxonLeft.setPosition(1);
    }

}
