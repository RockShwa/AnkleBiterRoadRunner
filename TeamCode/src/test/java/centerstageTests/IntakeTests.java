package centerstageTests;
import org.firstinspires.ftc.teamcode.CenterstageRobot.Intake.Intake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

// TODO List:
// Motor continuously spins rollers
// Find a way to verify the enum without a public variable
// Add servo range to IntakeConstants?
// Goes in reverse for 2 secs (TO_STACK & GROUND?)
    // intake should reverse for 2 seconds after completing intaking, but if need to intake again, shouldn't have to wait
// Ensure that the right trigger goes to pos when clicked once, not when held down
// Maybe create two states, INTAKING, OUTTAKING, and HOLDING, and then within INTAKING have TO_STACK and TO_GROUND

@ExtendWith(MockitoExtension.class)
public class IntakeTests {
    @Mock
    Servo axonServo;

    private Intake intake;

    @BeforeEach
    public void setUp() {
        intake = new Intake(axonServo);
    }
    @Test
    public void testThatServoOnlyMoves30To150ToStack() {
        double servoPos = Intake.servoAngleToPos(40);
        intake.servoState = Intake.ServoState.TO_STACK;
        intake.setServoIfValidPosition(servoPos);
        verify(axonServo, times(1)).setPosition(servoPos);
    }

    @Test
    public void testThatServoWontMoveIfPosOutOfRange() {
        double servoPos = Intake.servoAngleToPos(180);
        intake.servoState = Intake.ServoState.TO_STACK;
        intake.setServoIfValidPosition(servoPos);
        verify(axonServo, never()).setPosition(servoPos);
    }

    @Test
    public void testThatServoMovesInRangeToGround() {
        double servoPos = Intake.servoAngleToPos(180);
        intake.servoState = Intake.ServoState.TO_GROUND;
        intake.setServoIfValidPosition(servoPos);
        verify(axonServo, times(1)).setPosition(servoPos);
    }

    @Test
    public void servoCanSwitchModes() {
        intake.servoState = Intake.ServoState.HOLDING;
        Gamepad gamepad1 = new Gamepad();
        gamepad1.a = true;
        intake.update(gamepad1);
        assertEquals(Intake.ServoState.TO_STACK, intake.servoState);
    }

    @Test
    public void servoCanSwitchBackToHolding() {
        intake.servoState = Intake.ServoState.TO_STACK;
        Gamepad gamepad1 = new Gamepad();
        gamepad1.b = true;
        intake.update(gamepad1);
        assertEquals(Intake.ServoState.HOLDING, intake.servoState);
    }
    @Test
    public void servoGoesToFullPosWhenTriggerClicked() {
        Gamepad gamepad1 = new Gamepad();
        intake.servoState = Intake.ServoState.TO_STACK;

        gamepad1.right_trigger = 1;
        intake.update(gamepad1);

        verify(axonServo).setPosition(eq(Intake.servoAngleToPos(175)));
    }

    @Test
    public void servoMovesInSmallIncsTo175() {
        Gamepad gamepad1 = new Gamepad();
        intake.servoState = Intake.ServoState.TO_STACK;
        when(axonServo.getPosition()).thenReturn(Intake.servoAngleToPos(150));
        double updatePos = axonServo.getPosition();

        for (float i = .1f; i <= .5f; i += .1f) {
            when(axonServo.getPosition()).thenReturn(updatePos);
            gamepad1.right_trigger = i;
            intake.update(gamepad1);
            updatePos = updatePos + Intake.servoAngleToPos(5);
        }
        verify(axonServo).setPosition(Intake.servoAngleToPos(150) + Intake.servoAngleToPos(5)*5);
    }

    @Test
    public void servoMovesInSmallIncsNotPast175() {
        Gamepad gamepad1 = new Gamepad();
        intake.servoState = Intake.ServoState.TO_STACK;
        when(axonServo.getPosition()).thenReturn(Intake.servoAngleToPos(150));
        double updatePos = axonServo.getPosition();

        for (float i = .1f; i <= .6f; i += .1f) {
            when(axonServo.getPosition()).thenReturn(updatePos);
            gamepad1.right_trigger = i;
            intake.update(gamepad1);
            updatePos = updatePos + Intake.servoAngleToPos(5);
        }
        verify(axonServo, times(5)).setPosition(anyDouble());
    }

    @Test
    public void testServoAngleToPos() {
        double pos = Intake.servoAngleToPos(150);
        double expected = 0.8333;
        assertEquals(expected, pos);
    }

    @Test
    public void testServoAnglePosIfZero() {
        double pos = Intake.servoAngleToPos(0);
        double expected = 0.0;
        assertEquals(expected, pos);
    }
}
