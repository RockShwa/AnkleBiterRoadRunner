package org.firstinspires.ftc.teamcode.CenterstageRobot.util;

import com.arcrobotics.ftclib.command.SubsystemBase;

import org.firstinspires.ftc.teamcode.CenterstageRobot.Intake.IntakeSubsystem;

public class StatesSubsystem extends SubsystemBase {
    // Need to test for intermediate values
    private static final double[] axonPositions = {IntakeSubsystem.servoAngleToPos(30), IntakeSubsystem.servoAngleToPos(180)};
    public static enum IntakeState {
        INTAKE_START(axonPositions[0]),
        INTAKE_EXTENDED(axonPositions[1]),
        REVERSE(axonPositions[0]);

        private final double axonServoPos;

        IntakeState(double axonServoPos) {
            this.axonServoPos = axonServoPos;
        }

        public double getAxonPos() {
            return axonServoPos;
        }
    }

    public static IntakeState intakeState = IntakeState.INTAKE_START;

    public void changeIntakeState(IntakeState newState) {
        intakeState = newState;
    }
}
