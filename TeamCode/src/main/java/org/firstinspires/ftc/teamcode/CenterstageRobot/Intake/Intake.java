package org.firstinspires.ftc.teamcode.CenterstageRobot.Intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Intake {
    public ServoState servoState = ServoState.HOLDING;
    private Servo axonServoIntake;

    private DcMotorEx intakeRollerMotor;

    private Servo topBucketServo;

    private Servo bottomBucketServo;
    private boolean triggerWasPressed = false;
    private boolean fullExtensionWasPressed = false;

    private double startTime = 0;

    // constructor, deal with hardware map
    public Intake(Servo axonIntake, DcMotorEx motor, Servo topBucketServo, Servo bottomBucketServo) {
        this.axonServoIntake = axonIntake;
        this.intakeRollerMotor = motor;
        this.topBucketServo = topBucketServo;
        this.bottomBucketServo = bottomBucketServo;
    }

    public void setServoIfValidPosition(double servoPos) {
        if (servoState.equals(ServoState.TO_STACK) && servoPos >= servoAngleToPos(30) && servoPos <= servoAngleToPos(150)) {
            axonServoIntake.setPosition(servoPos);
        } else if (servoState.equals(ServoState.TO_GROUND) && servoPos >= servoAngleToPos(30) && servoPos <= servoAngleToPos(180)) {
            axonServoIntake.setPosition(servoPos);
        }
    }

    public void update(Gamepad gamepad1) {
        switch (servoState) {
            case HOLDING:
                axonServoIntake.setPosition(servoAngleToPos(30));
                if (gamepad1.a) {
                    servoState = ServoState.TO_STACK;
                }
                break;
            case TO_STACK:
                if (fullExtensionWasPressed == false && gamepad1.b) {
                    axonServoIntake.setPosition(servoAngleToPos(150));
                    fullExtensionWasPressed = true;
                } else if (fullExtensionWasPressed && gamepad1.b) {
                    axonServoIntake.setPosition(0.0);
                    fullExtensionWasPressed = false;
                }
                if (gamepad1.a) {
                    if (axonServoIntake.getPosition() == servoAngleToPos(180)) {
                        // does this cause the servo to move to 0 degrees? or just sets the direction?
                        axonServoIntake.setDirection(Servo.Direction.REVERSE);
                        axonServoIntake.setPosition(0);
                        double pos = 0.0;
                        pos = pos + servoAngleToPos(10);
                        axonServoIntake.setPosition(pos);
                    } else {
                        double pos = axonServoIntake.getPosition();
                        System.out.println(pos);
                        pos = pos + servoAngleToPos(10);
                        axonServoIntake.setPosition(pos);
                    }
                }
                break;
            case TO_GROUND:
        }

        if (gamepad1.right_trigger >= 0.5f) {
            topBucketServo.setPosition(0);
            bottomBucketServo.setPosition(0);
            intakeRollerMotor.setDirection(DcMotorSimple.Direction.FORWARD);
            intakeRollerMotor.setPower(1);
            triggerWasPressed = true;
        } else if (triggerWasPressed && gamepad1.right_trigger < 0.5f) {
            axonServoIntake.setPosition(servoAngleToPos(30));
            triggerWasPressed = false;
            intakeRollerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
            runMotorFor2Seconds();
        }
    }

    public void runMotorFor2Seconds() {
        startTime = System.currentTimeMillis();
        System.out.println(startTime);
        while ((System.currentTimeMillis() - startTime >= 1900) && (System.currentTimeMillis() - startTime <= 2200)) {
            intakeRollerMotor.setPower(1);
        }
        intakeRollerMotor.setPower(0);
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
        pos = 1/pos;
        return pos;
    }

}
