package org.firstinspires.ftc.teamcode.randomTests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.util.Encoder;

@TeleOp
public class MotorEncoderTest extends OpMode {
    DcMotorEx motor;
    Encoder encoder;

    @Override
    public void init() {
        motor = hardwareMap.get(DcMotorEx.class, "leftFront");
        encoder = new Encoder(motor);
    }

    @Override
    public void loop() {
        telemetry.addData("Motor pos (w/o encoder)", motor.getCurrentPosition());
        telemetry.addData("Motor pos (w/ encoder)", encoder.getCurrentPosition());
        telemetry.addData("Motor velocity (w/o encoder)", motor.getVelocity(AngleUnit.DEGREES));
        telemetry.addData("Motor raw velocity (w/ encoder)", encoder.getRawVelocity());
        telemetry.addData("Motor corrected velocity (w/ encoder)", encoder.getCorrectedVelocity());
    }
}
