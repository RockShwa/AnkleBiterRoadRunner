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
// Add servo feedback when out of bounds?
// Find a way to verify the enum without a public variable
// Does setPosition use ticks or degrees?
// Add servo range to IntakeConstants
// Add mode that starts at 150 and goes down in small increments to 175 (ground level) and then goes in reverse for 2 secs
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
        double servoPos = 40;
        intake.setServoIfValidPosition(servoPos);
        verify(axonServo, times(1)).setPosition(servoPos);
    }

    @Test
    public void testThatServoWontMoveIfPosOutOfRange() {
        double servoPos = 180;
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

    // This test needs some help
//    @Test
//    public void servoCanMoveInSmallIncsTo160() {
//        Gamepad gamepad1 = new Gamepad();
//        when(axonServo.getPosition()).thenReturn(150.0);
//        double curPos = axonServo.getPosition();
//
//        gamepad1.x = true;
//        intake.update(gamepad1);
//        gamepad1.x = false;
//
//        when(axonServo.getPosition()).thenReturn(curPos + 5);
//        curPos = axonServo.getPosition();
//
//        gamepad1.x = true;
//        intake.update(gamepad1);
//        gamepad1.x = false;
//
//        assertEquals(160, axonServo.getPosition());
//    }

}
