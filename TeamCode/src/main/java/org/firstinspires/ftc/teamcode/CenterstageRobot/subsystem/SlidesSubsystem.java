package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.CenterstageRobot.hardware.SlidesHardware;
import org.firstinspires.ftc.teamcode.util.Encoder;

public class SlidesSubsystem extends SubsystemBase {
    // Might be good to make an Encoder for the motor later?
    private final DcMotorEx leftSlideMotor;
    private final DcMotorEx rightSlideMotor;
    private int currentLiftTick;
    private int pixelTickHeight = 300; // got this from Tomahawk

    public SlidesSubsystem(final DcMotorEx leftSlideMotor, final DcMotorEx rightSlideMotor) {
        this.leftSlideMotor = leftSlideMotor;
        this.rightSlideMotor = rightSlideMotor;

        currentLiftTick = 0;
    }

    public SlidesSubsystem(SlidesHardware slidesHardware) {
        this.leftSlideMotor = slidesHardware.leftSlideMotor;
        this.rightSlideMotor = slidesHardware.rightSlideMotor;
    }

    public void reset() {
        rightSlideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        leftSlideMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        rightSlideMotor.setPower(0);
        leftSlideMotor.setPower(0);
    }

    // Would probably need to do a PID controller with this in real life
    public void setLiftPosition(int targetPos) {
        leftSlideMotor.setTargetPosition(targetPos);
        rightSlideMotor.setTargetPosition(targetPos);
    }

    public void adjustUp() {
        currentLiftTick = rightSlideMotor.getCurrentPosition();
        setLiftPosition(currentLiftTick + pixelTickHeight); // move up by a pixel? I really need a robot for this...
    }

    public void adjustDown() {
        currentLiftTick = rightSlideMotor.getCurrentPosition();
        setLiftPosition(currentLiftTick - pixelTickHeight); // move down by a pixel
    }
}
