package org.firstinspires.ftc.teamcode.CenterstageRobot.Intake;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.CenterstageRobot.util.GamepadMapping;

public class Intake {
    public ServoState servoState = ServoState.HOLDING;
    private Servo axonServoIntake;

    private DcMotorEx intakeRollerMotor;

    private Servo topBucketServo;

    private Servo bottomBucketServo;
    private boolean triggerWasPressed = false;
    private boolean fullExtensionWasPressed = false;

    private long startTime = 0;

    //private GamepadMapping controls;

    // constructor, deal with hardware map
    public Intake(Servo axonIntake, DcMotorEx motor, Servo topBucketServo, Servo bottomBucketServo, GamepadMapping controls) {
        this.axonServoIntake = axonIntake;
        this.intakeRollerMotor = motor;
        this.topBucketServo = topBucketServo;
        this.bottomBucketServo = bottomBucketServo;
        //this.controls = controls;
    }

    public void update(Gamepad gamepad1) {
        switch (servoState) {
            case HOLDING:
                axonServoIntake.setPosition(servoAngleToPos(30));
                if (gamepad1.right_trigger >= 0.5f) {
                    servoState = ServoState.INTAKING;
                }
                break;
            case INTAKING:
                checkIfButtonToggledForFullExtension(gamepad1);
                if (gamepad1.a) {
                    if (axonServoIntake.getPosition() == servoAngleToPos(180)) {
                        // does this cause the servo to move to 0 degrees? or just sets the direction?
                        axonServoIntake.setDirection(Servo.Direction.REVERSE);
                        axonServoIntake.setPosition(0);
                        double currentPos = 0.0;
                        incrementIntakeServoPos(10, currentPos);
                    } else {
                        double currentPos = axonServoIntake.getPosition();
                        incrementIntakeServoPos(10, currentPos);
                    }
                }
                if (gamepad1.right_trigger >= 0.5f) {
                    resetBucketServos();
                    intakeRollerMotor.setDirection(DcMotorSimple.Direction.FORWARD);
                    intakeRollerMotor.setPower(1);
                    triggerWasPressed = true;
                } else if (triggerWasPressed && gamepad1.right_trigger < 0.5f) {
                    axonServoIntake.setPosition(servoAngleToPos(30));
                    triggerWasPressed = false;
                    intakeRollerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
                    servoState = ServoState.OUTAKING;
                    runMotorFor2Seconds();
                }
                break;
            case OUTAKING:
        }
        if (gamepad1.x) {
            servoState = ServoState.HOLDING;
        }
    }

    private void resetBucketServos() {
        topBucketServo.setPosition(0);
        bottomBucketServo.setPosition(0);
    }

    public void runMotorFor2Seconds() {
        startTime = System.currentTimeMillis();
        while ((!servoState.equals(ServoState.INTAKING) && (System.currentTimeMillis() - startTime >= 0) && (System.currentTimeMillis() - startTime <= 2000))) {
            intakeRollerMotor.setPower(1);
        }
        intakeRollerMotor.setPower(0);
    }

    private void incrementIntakeServoPos(double angleIncrement, double pos) {
        pos = pos + servoAngleToPos(angleIncrement);
        axonServoIntake.setPosition(pos);
    }

    private void checkIfButtonToggledForFullExtension(Gamepad gamepad1) {
        if (fullExtensionWasPressed == false && gamepad1.b) {
            axonServoIntake.setPosition(servoAngleToPos(150));
            fullExtensionWasPressed = true;
        } else if (fullExtensionWasPressed && gamepad1.b) {
            axonServoIntake.setPosition(0.0);
            fullExtensionWasPressed = false;
        }
    }

    public enum ServoState {
        HOLDING,
        INTAKING,
        OUTAKING
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
