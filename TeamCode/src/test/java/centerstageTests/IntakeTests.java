package centerstageTests;
import org.firstinspires.ftc.teamcode.CenterstageRobot.Intake.Intake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.Servo;

// TODO List:
//- Motor continuously spins intake
//        - Servo moves the intake up and down
//        1) Mode 1: dropdown to stack (30-150)
//        2) Mode 2: dropdown to ground (30-180)
//        3) Mode 3: holding (at 30)
// Need to account for various modes in Intake
// Add servo feedback when out of bounds?
@ExtendWith(MockitoExtension.class)
public class IntakeTests {
    @Mock
    Servo axonServo;
    @Test
    public void testThatServoOnlyMoves30To150ToStack() {
        Intake intake = new Intake(axonServo);
        double servoPos = 40;
        boolean actual = intake.setServoWithValidPosition(servoPos);
        assertTrue(actual);
    }

    @Test
    public void testThatServoWontMoveIfPosOutOfRange() {
        Intake intake = new Intake(axonServo);
        double servoPos = 180;
        boolean actual = intake.setServoWithValidPosition(servoPos);
        assertFalse(actual);
    }
}
