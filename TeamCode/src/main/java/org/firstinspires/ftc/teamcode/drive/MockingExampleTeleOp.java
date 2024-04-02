package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@TeleOp
public class MockingExampleTeleOp extends OpMode {
    private MockingExampleDrive drive;
    private DcMotorEx motor;
    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "leftFront");
        drive = new MockingExampleDrive(motor);
        motor.setTargetPosition(0);
    }

    @Override
    public void loop() {
        int ticks = motor.getCurrentPosition();
        telemetry.addData("Motor Position in Degrees", drive.convertPositionToDegrees(ticks));
        telemetry.update();
        // motor.setPower(0.2);
    }
}
