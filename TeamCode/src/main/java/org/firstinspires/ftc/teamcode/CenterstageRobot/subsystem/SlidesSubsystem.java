package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class SlidesSubsystem extends SubsystemBase {
    DcMotorEx leftSlideMotor;
    DcMotorEx rightSlideMotor;

    public SlidesSubsystem(DcMotorEx leftSlideMotor, DcMotorEx rightSlideMotor) {
        this.leftSlideMotor = leftSlideMotor;
        this.rightSlideMotor = rightSlideMotor;
    }

    // TODO: Implement with TeleOp with HardwareMap
    // public SlidesSubsystem(Hardware hwMap) {
    // }

    public void reset() {
        rightSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        leftSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);

        rightSlideMotor.setPower(0);
        leftSlideMotor.setPower(0);

        rightSlideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }
}
