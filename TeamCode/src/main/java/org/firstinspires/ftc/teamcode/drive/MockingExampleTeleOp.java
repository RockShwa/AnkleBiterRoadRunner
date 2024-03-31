package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class MockingExampleTeleOp extends OpMode {
    MockingExampleDrive drive;
    DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "motor");
    @Override
    public void init() {
        drive = new MockingExampleDrive(motor);
    }

    @Override
    public void loop() {
        int ticks = motor.getCurrentPosition();
        telemetry.addData("Motor Position in Degrees", drive.convertPositionToDegrees(ticks));
        telemetry.update();
    }
}
