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

        /*
        Notes from the test:
        - TODO: Research on velocity, how is it calculated, what do the angles represent?
        - The values I think increase indefinitely (in reasonable range) for motors, to find out revolutions would
        need to divide current position by the ticks per revolution
        - The motor.getCurrentPosition() and encoder.getCurrentPosition() read the same value
        - Velocity only changes when the motor moves (duh), not sure what the values mean, is it a tick value? speed?
        - The motor.getVelocity(Degrees) returns a greater angle depending on how fast the motor spins
         */

        DcMotorEx motor = hardwareMap.get(DcMotorEx.class, "leftFront");
        encoder = new Encoder(motor);
        motor.setTargetPosition(0);

        waitForStart();

        while (opModeIsActive()) {
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
