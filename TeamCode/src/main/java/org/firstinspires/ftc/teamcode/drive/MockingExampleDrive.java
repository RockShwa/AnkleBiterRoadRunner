package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MockingExampleDrive {
    DcMotorEx motor;
    public MockingExampleDrive(DcMotorEx motor) {
        this.motor = motor;
    }

    public int convertPositionToDegrees(int ticks) {
        if (ticks == 0 || ticks >= 538) {
            return 0;
        }
        int angle = 360/(538/ticks);
        return angle;
    }

}
