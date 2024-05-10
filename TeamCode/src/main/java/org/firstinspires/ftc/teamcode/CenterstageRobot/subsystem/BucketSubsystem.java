package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.CenterstageRobot.hardware.BucketHardware;
import org.firstinspires.ftc.teamcode.util.Encoder;

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

    public BucketSubsystem(BucketHardware bucketHardware) {
        this.wristAxon = bucketHardware.wristAxon;
        this.bucketServoTop = bucketHardware.bucketServoTop;
        this.bucketServoBottom = bucketHardware.bucketServoBottom;
        this.flipAxonLeft = bucketHardware.flipAxonLeft;
        this.flipAxonRight = bucketHardware.flipAxonRight;
    }
    public void wristToFull() {
        // this would change for an actual robot, 1 is an obscure value
        wristAxon.setPosition(1);
    }

    public void flipWrist() {
        // this would change for an actual robot, 1 is an obscure value
        flipAxonRight.setPosition(1);
        flipAxonLeft.setPosition(1);
    }

    // resets the two servos within the bucket, done before intake
    public void resetBucketInternal() {
        bucketServoTop.setPosition(0);
        bucketServoBottom.setPosition(0);
    }

    public void resetAll() {
        resetBucketInternal();
        flipAxonRight.setPosition(0);
        flipAxonLeft.setPosition(0);
        wristAxon.setPosition(0);
    }
}
