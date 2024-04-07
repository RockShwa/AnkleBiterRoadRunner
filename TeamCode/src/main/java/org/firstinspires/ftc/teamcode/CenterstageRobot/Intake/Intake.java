package org.firstinspires.ftc.teamcode.CenterstageRobot.Intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    private DcMotorEx intakeMotor;
    private Servo axonIntake;

    // constructor, deal with hardware map
    public Intake(Servo axonIntake) {
        this.axonIntake = axonIntake;
    }

    public boolean setServoWithValidPosition(double servoPos) {
        if (servoPos > 30 && servoPos < 150) {
            axonIntake.setPosition(servoPos);
            return true;
        }
        return false;
    }


}
