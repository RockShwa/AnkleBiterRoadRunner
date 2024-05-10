package centerstageTests.subsystemTests;

import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.StatesSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystemConstants.IntakeConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntakeConstantsTests {
    private IntakeConstants states;
    @BeforeEach
    public void setUp() {
        states = new IntakeConstants();
    }
    @Test
    public void testIntakeCanSwitchStates() {
        states.changeIntakeState(IntakeConstants.IntakeState.INTAKE_EXTENDED);
        assertEquals("INTAKE_EXTENDED", states.intakeState.name());
    }

    @Test
    public void testIntakeCanReturnPos() {
        states.intakeState = IntakeConstants.IntakeState.INTAKE_START;
        double actual = states.intakeState.getAxonPos();
        double expected = IntakeSubsystem.servoAngleToPos(30);
        assertEquals(expected, actual);
    }
}
