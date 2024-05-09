package org.firstinspires.ftc.teamcode.CenterstageRobot.hardware;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BucketHardware {
    public final Servo wristAxon;
    public final Servo bucketServoTop;
    public final Servo bucketServoBottom;
    public final Servo flipAxonLeft;
    public final Servo flipAxonRight;

    public BucketHardware(HardwareMap hwMap) {
        wristAxon = hwMap.get(Servo.class, "wristAxon");
        bucketServoTop = hwMap.get(Servo.class, "bucketServoTop");
        bucketServoBottom = hwMap.get(Servo.class, "bucketServoBottom");
        flipAxonLeft = hwMap.get(Servo.class, "flipAxonLeft");
        flipAxonRight = hwMap.get(Servo.class, "flipAxonRight");
    }


}
