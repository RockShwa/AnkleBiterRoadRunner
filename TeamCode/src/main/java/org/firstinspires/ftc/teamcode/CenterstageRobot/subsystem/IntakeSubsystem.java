package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.CenterstageRobot.hardware.IntakeHardware;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystemConstants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {
    private final Servo intakeAxonServo;
    private final DcMotorEx intakeRollerMotor;

    public IntakeSubsystem(final Servo servo, final DcMotorEx rollerMotor) {
        intakeAxonServo = servo;
        intakeRollerMotor = rollerMotor;

    }

    public IntakeSubsystem(IntakeHardware intakeHardware) {
        intakeAxonServo = intakeHardware.intakeAxonServo;
        intakeRollerMotor = intakeHardware.intakeRollerMotor;
    }

    public void extend() {
        intakeAxonServo.setPosition(IntakeConstants.IntakeState.INTAKE_EXTENDED.getAxonPos());
    }

    // This may not work on the actual robot due to the getPosition() only returning the position you sent it
    // It actually might work because there are encoders on axon servos
    public void incrementPos() {
        if (intakeAxonServo.getPosition() == IntakeConstants.IntakeState.INTAKE_EXTENDED.getAxonPos()) {
            // does this cause the servo to move to 0 degrees? or just sets the direction?
            intakeAxonServo.setDirection(Servo.Direction.REVERSE);
            intakeAxonServo.setPosition(0);
            double currentPos = 0.0;
            intakeAxonServo.setPosition(currentPos += servoAngleToPos(10));
        } else {
            double currentPos = intakeAxonServo.getPosition();
            intakeAxonServo.setPosition(currentPos += servoAngleToPos(10));
        }
    }

    public void runMotorInReverseFor2Seconds() {
        IntakeConstants.changeIntakeState(IntakeConstants.IntakeState.REVERSE);
        intakeRollerMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        long startTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - startTime >= 0 && System.currentTimeMillis() - startTime <= 2000) {
            intakeRollerMotor.setPower(1);
        }
        intakeRollerMotor.setPower(0);
    }

    public void resetMotor() {
        intakeRollerMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void resetAxonPosition() {
        intakeAxonServo.setDirection(Servo.Direction.FORWARD);
        intakeAxonServo.setPosition(IntakeConstants.IntakeState.INTAKE_START.getAxonPos());
    }

    public void intakeOn() {
        intakeRollerMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        intakeRollerMotor.setPower(1);
    }

    public void resetAll() {
        resetAxonPosition();
        resetMotor();
    }

    // helper method for this class and users :)
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