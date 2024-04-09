package org.firstinspires.ftc.teamcode.CenterstageRobot.Intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    public ServoState servoState = ServoState.HOLDING;
    private Servo axonIntake;

    // constructor, deal with hardware map
    public Intake(Servo axonIntake) {
        this.axonIntake = axonIntake;
    }

    public void setServoIfValidPosition(double servoPos) {
        if (servoState.equals(ServoState.TO_STACK) && servoPos >= servoAngleToPos(30) && servoPos <= servoAngleToPos(150)) {
            axonIntake.setPosition(servoPos);
        } else if (servoState.equals(ServoState.TO_GROUND) && servoPos >= servoAngleToPos(30) && servoPos <= servoAngleToPos(180)) {
            axonIntake.setPosition(servoPos);
        }
    }

    public void update(Gamepad gamepad1) {
        switch (servoState) {
            case HOLDING:
                axonIntake.setPosition(servoAngleToPos(30));
                if (gamepad1.a) {
                    servoState = ServoState.TO_STACK;
                    axonIntake.setPosition(servoAngleToPos(150));
                }
                break;
            case TO_STACK:
                if (gamepad1.b) {
                    servoState = servoState.HOLDING;
                }
                if (gamepad1.right_trigger == 1) {
                    axonIntake.setPosition(servoAngleToPos(175));
                }
                if (gamepad1.right_trigger < 1) {
                    double curPos = axonIntake.getPosition();
                    if (curPos < servoAngleToPos(175)) {
                        double updatePos = Math.round((curPos + servoAngleToPos(5)) * 10000d) / 10000d;
                        axonIntake.setPosition(updatePos);
                    }
                }
                break;
        }
    }

    public enum ServoState {
        HOLDING,
        TO_STACK,
        TO_GROUND
    }

    public static double servoAngleToPos(double angle) {
        if (angle == 0) {
            return 0.0;
        }
        // turns desired angle into a 0-1 value servo understands
        double pos = 180/angle;
        pos = Math.round((1/pos)*10000d)/10000d;
        return pos;
    }

}
