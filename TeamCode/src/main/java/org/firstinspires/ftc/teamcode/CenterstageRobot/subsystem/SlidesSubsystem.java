package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.util.Encoder;

public class SlidesSubsystem extends SubsystemBase {
    // Might be good to make an Encoder for the motor later?
    private DcMotorEx leftSlideMotor;
    private DcMotorEx rightSlideMotor;
    private Encoder liftEncoder;
    private int currentLiftTick;

    // way to make this private?
    public int pixelTickHeight = 300; // got this from Tomahawk

    public SlidesSubsystem(DcMotorEx leftSlideMotor, DcMotorEx rightSlideMotor) {
        this.leftSlideMotor = leftSlideMotor;
        this.rightSlideMotor = rightSlideMotor;

        liftEncoder = new Encoder(rightSlideMotor);
        currentLiftTick = liftEncoder.getCurrentPosition();
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

    // Would probably need to do a PID controller with this in real life
    public void setLiftPosition(int targetPos) {
        leftSlideMotor.setTargetPosition(targetPos);
        rightSlideMotor.setTargetPosition(targetPos);
    }

    public void adjustUp() {
        setLiftPosition(currentLiftTick + pixelTickHeight); // move up by a pixel? I really need a robot for this...
    }

    public void adjustDown() {
        setLiftPosition(currentLiftTick - pixelTickHeight); // move down by a pixel
    }
}
