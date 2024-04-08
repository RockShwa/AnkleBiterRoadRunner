package org.firstinspires.ftc.teamcode.CenterstageRobot.Intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public ServoState servoState = ServoState.HOLDING;
    private DcMotorEx intakeMotor;
    private Servo axonIntake;

    // constructor, deal with hardware map
    public Intake(Servo axonIntake) {
        this.axonIntake = axonIntake;
    }

    public void setServoIfValidPosition(double servoPos) {
        if (servoPos > 30 && servoPos < 150) {
            axonIntake.setPosition(servoPos);
        }
    }

    public void update(Gamepad gamepad1) {
        switch (servoState) {
            case HOLDING:
                axonIntake.setPosition(30);
                if (gamepad1.a) {
                    servoState = ServoState.TO_STACK;
                }
                break;
            case TO_STACK:
                axonIntake.setPosition(150);
                break;
        }
    }

    public enum ServoState {
        HOLDING,
        TO_STACK,
        TO_GROUND

    }

}
