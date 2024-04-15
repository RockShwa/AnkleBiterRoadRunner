package org.firstinspires.ftc.teamcode.CenterstageRobot.Intake.OldIntake;

import org.firstinspires.ftc.teamcode.CenterstageRobot.Intake.OldIntake.Intake;

public class IntakeConstants {
    // Need to test for the intermediate values
    private static double[] axonServoPositions = {Intake.servoAngleToPos(30), 1};

    public enum IntakePos {
        HOLDING("HOLDING", axonServoPositions[0]),
        INTAKING_EXTENDED("INTAKING_EXTENDED", axonServoPositions[1]);

        private String name;
        private double axonServoPos;
        IntakePos(String name, double axonServoPos) {
            this.name = name;
            this.axonServoPos = axonServoPos;
        }

        public String toString() {
            return name;
        }
        public double servoPos() {
            return axonServoPos;
        }
    }
}
