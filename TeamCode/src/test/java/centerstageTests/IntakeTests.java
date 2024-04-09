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
//- Motor continuously spins intake
//        - Servo moves the intake up and down
//        1) Mode 1: dropdown to stack (30-150)
//        2) Mode 2: dropdown to ground (30-180)
//        3) Mode 3: holding (at 30)
// Find a way to verify the enum without a public variable
// Add servo range to IntakeConstants?
// Goes in reverse for 2 secs
// Ensure that the right trigger goes to pos when clicked once, not when held down

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
        intake.setServoIfValidPosition(servoPos);
        verify(axonServo, times(1)).setPosition(servoPos);
    }

    @Test
    public void testThatServoWontMoveIfPosOutOfRange() {
        double servoPos = Intake.servoAngleToPos(180);
        intake.setServoIfValidPosition(servoPos);
        verify(axonServo, never()).setPosition(servoPos);
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
}
