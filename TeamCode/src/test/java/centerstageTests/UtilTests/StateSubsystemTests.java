package centerstageTests.UtilTests;

import org.firstinspires.ftc.teamcode.CenterstageRobot.Intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.util.StatesSubsystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StateSubsystemTests {
    private StatesSubsystem states;
    @BeforeEach
    public void setUp() {
        states = new StatesSubsystem();
    }
    @Test
    public void testIntakeCanSwitchStates() {
        states.changeIntakeState(StatesSubsystem.IntakeState.INTAKE_EXTENDED);
        assertEquals("INTAKE_EXTENDED", states.intakeState.name());
    }

    @Test
    public void testIntakeCanReturnPos() {
        states.intakeState = StatesSubsystem.IntakeState.INTAKE_START;
        double actual = states.intakeState.getAxonPos();
        double expected = IntakeSubsystem.servoAngleToPos(30);
        assertEquals(expected, actual);
    }
}
