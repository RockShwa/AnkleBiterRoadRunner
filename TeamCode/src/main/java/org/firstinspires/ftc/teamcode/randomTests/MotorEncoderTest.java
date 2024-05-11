package org.firstinspires.ftc.teamcode.randomTests;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.util.Encoder;

@TeleOp
public class MotorEncoderTest extends LinearOpMode {
    Encoder encoder;
    @Override
    public void runOpMode() throws InterruptedException {

        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "leftFront");
        encoder = new Encoder(motor);

        waitForStart();

        if (opModeIsActive()) {
            telemetry.addData("Hello", 2);
            telemetry.addData("Motor pos (w/o encoder)", motor.getCurrentPosition());
            telemetry.addData("Motor pos (w/ encoder)", encoder.getCurrentPosition());
            telemetry.addData("Motor velocity (w/o encoder)", motor.getVelocity(AngleUnit.DEGREES));
            telemetry.addData("Motor raw velocity (w/ encoder)", encoder.getRawVelocity());
            telemetry.addData("Motor corrected velocity (w/ encoder)", encoder.getCorrectedVelocity());
            telemetry.update();
        }
    }
}

//        @Override
//        public void init() {
//            motor = hardwareMap.get(DcMotorEx.class, "leftFront");
//            encoder = new Encoder(motor);
//        }
//
//        @Override
//        public void loop() {
//            telemetry.addData("Motor pos (w/o encoder)", motor.getCurrentPosition());
//            telemetry.addData("Motor pos (w/ encoder)", encoder.getCurrentPosition());
//            telemetry.addData("Motor velocity (w/o encoder)", motor.getVelocity(AngleUnit.DEGREES));
//            telemetry.addData("Motor raw velocity (w/ encoder)", encoder.getRawVelocity());
//            telemetry.addData("Motor corrected velocity (w/ encoder)", encoder.getCorrectedVelocity());
//            telemetry.update();
//        }
//    }
