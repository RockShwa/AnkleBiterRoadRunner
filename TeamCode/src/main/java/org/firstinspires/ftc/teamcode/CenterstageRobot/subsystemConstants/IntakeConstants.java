package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystemConstants;

import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.StatesSubsystem;

public class IntakeConstants {
    private static final double[] intakeAxonPositions = {IntakeSubsystem.servoAngleToPos(30), IntakeSubsystem.servoAngleToPos(180)};

    public enum IntakeState {
        INTAKE_START(intakeAxonPositions[0]),
        INTAKE_EXTENDED(intakeAxonPositions[1]),
        REVERSE(intakeAxonPositions[0]);

        private final double axonServoPos;

        IntakeState(double axonServoPos) {
            this.axonServoPos = axonServoPos;
        }

        public double getAxonPos() {
            return axonServoPos;
        }
    }

    public static IntakeConstants.IntakeState intakeState = IntakeConstants.IntakeState.INTAKE_START;

    public static void changeIntakeState(IntakeConstants.IntakeState newState) {
        intakeState = newState;
    }
}
