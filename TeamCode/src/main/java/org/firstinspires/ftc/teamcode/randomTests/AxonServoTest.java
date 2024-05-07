package org.firstinspires.ftc.teamcode.randomTests;

import com.arcrobotics.ftclib.hardware.ServoEx;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp
public class AxonServoTest extends LinearOpMode {
    ServoEx axonServo;
    @Override
    public void runOpMode() throws InterruptedException {
        axonServo = hardwareMap.get(ServoEx.class, "axon");

        while (opModeIsActive()) {
            telemetry.addData("Servo Pos", axonServo.getPosition());
            telemetry.addData("Servo Angle", axonServo.getAngle(AngleUnit.DEGREES));
        }
    }
}
