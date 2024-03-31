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
        int angle = 360/(538/ticks);
        return angle;
    }

}
