package centerstageTests.IntakeTests.OldIntake;
import org.firstinspires.ftc.teamcode.CenterstageRobot.oldCode.oldIntake.Intake;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

// TODO List:
// -- Questions --

// -- Functionality --
// Motor continuously spins rollers (this is turned on by a trigger) -> DONE
// Button a -> When clicked, servo should increase pos by 10 degrees, click multiple times to get to full pos (180) -> DONE
    // When it reaches the lowest pos, cycle back up by 10 to starting, used for stacks (pos doesn't save btw intake cycles) -> KIND OF DONE
// Button b -> When toggled on, should go to full pos (180) --> DONE
    // when pressed again, should go back to top -> DONE
// Trigger -> Treat like a button, turns on intaking rollers if pressed, once trigger is released, should go back to
    // init pos -> DONE
    // reverse for 2 seconds -> AHAHA DONE
    // on & off values: >= 0.5 -> DONE
    // When turned on, should reset the direction of the roller motor to be forward -> DONE
    // if want to go back to intaking, should not have to wait for seconds to be over -> UNTESTED, NEED ROBOT, BUT DONE
    // When this is pressed to turn on intake, servos on bucket should reset to pos 0 -> DONE

// -- Refactoring --
// Get rid of the TO_STACK anf TO_GROUND states, only do Intaking, Holding, and Outtacking -> DONE
// BREAK STUFF UP INTO METHODS GOOBER :) -> DONE
// TODO Re-do all of this with Commands and Subsystems :)

@ExtendWith(MockitoExtension.class)
public class IntakeTests {
    @Mock
    Servo axonServo;
    @Mock
    DcMotorEx intakeRollerMotor;

    @Mock
    Servo topBucketServo;

    @Mock
    Servo bottomBucketServo;

    private Intake intake;
    private Gamepad gamepad1;

    //@BeforeEach
    public void setUp() {
        intake = new Intake(axonServo, intakeRollerMotor, topBucketServo, bottomBucketServo);
        gamepad1 = new Gamepad();
    }

    //@Test
    public void servoCanSwitchModes() {
        intake.servoState = Intake.ServoState.HOLDING;
        gamepad1.right_trigger = 0.6f;
        intake.update(gamepad1);
        assertEquals(Intake.ServoState.INTAKING, intake.servoState);
    }

    //@Test
    public void servoCanSwitchBackToHolding() {
        intake.servoState = Intake.ServoState.INTAKING;
        gamepad1.x = true;
        intake.update(gamepad1);
        assertEquals(Intake.ServoState.HOLDING, intake.servoState);
    }

    //@Test
    public void servoGoesToFullStackPosWhenButtonClicked() {
        intake.servoState = Intake.ServoState.INTAKING;

        gamepad1.b = true;
        intake.update(gamepad1);

        verify(axonServo).setPosition(eq(Intake.servoAngleToPos(150)));
    }

    //@Test
    public void servoGoesToStartingPosWhenButtonClickedAgain() {
        intake.servoState = Intake.ServoState.INTAKING;

        gamepad1.b = true;
        intake.update(gamepad1);
        intake.update(gamepad1);


        verify(axonServo).setPosition(eq(0.0));
    }

    //@Test
    public void servoCanMoveToFullStackPosWhenButtonClickedMultipleTimes() {
        gamepad1 = new Gamepad();
        intake.servoState = Intake.ServoState.INTAKING;
        when(axonServo.getPosition()).thenReturn(Intake.servoAngleToPos(130));

        gamepad1.a = true;
        intake.update(gamepad1);
        gamepad1.a = false;
        intake.update(gamepad1);

        double updatePos = axonServo.getPosition() + Intake.servoAngleToPos(10);
        when(axonServo.getPosition()).thenReturn(updatePos);

        gamepad1.a = true;
        intake.update(gamepad1);

        verify(axonServo).setPosition(eq(Intake.servoAngleToPos(150)));
    }

    // TODO: Test Servo Behavior in MakerSpace
    //@Test
    public void servoMovesDownToLowestPosAndThenBackUpInIncrementsOfButtonClick() {
        intake.servoState = Intake.ServoState.INTAKING;
        when(axonServo.getPosition()).thenReturn(Intake.servoAngleToPos(170));

        gamepad1.a = true;
        intake.update(gamepad1); // moves it to 180
        gamepad1.a = false;
        intake.update(gamepad1);

        double updatePos = axonServo.getPosition() + Intake.servoAngleToPos(10);
        when(axonServo.getPosition()).thenReturn(updatePos);

        gamepad1.a = true;
        intake.update(gamepad1); // this should make servo go back up

        verify(axonServo).setPosition(eq(Intake.servoAngleToPos(10)));
    }

    //@Test
    public void testServoAngleToPos() {
        double pos = Intake.servoAngleToPos(150);
        double expected = 0.8333333333333334;
        assertEquals(expected, pos);
    }

    //@Test
    public void testServoAnglePosIfZero() {
        double pos = Intake.servoAngleToPos(0);
        double expected = 0.0;
        assertEquals(expected, pos);
    }

    //@Test
    public void whenTriggerPressedRollerMotorTurnsOn() {
        intake.servoState = Intake.ServoState.INTAKING;
        gamepad1.right_trigger = .6f;
        intake.update(gamepad1);
        verify(intakeRollerMotor).setPower(anyDouble());
    }

    //@Test
    public void whenTriggerNotPressedMotorIsOff() {
        gamepad1.right_trigger = .2f;
        intake.update(gamepad1);
        verify(intakeRollerMotor, never()).setPower(anyDouble());
    }

    //@Test
    public void whenTriggerPressedMotorModeIsForward() {
        intake.servoState = Intake.ServoState.INTAKING;
        gamepad1.right_trigger = .5f;
        intake.update(gamepad1);
        verify(intakeRollerMotor).setDirection(DcMotorSimple.Direction.FORWARD);
    }

    //@Test
    public void whenTriggerPressedBucketServosResetTo0() {
        intake.servoState = Intake.ServoState.INTAKING;
        gamepad1.right_trigger = 1f;
        intake.update(gamepad1);
        verify(topBucketServo).setPosition(0);
        verify(bottomBucketServo).setPosition(0);
    }

    //@Test
    public void whenTriggerReleasedAxonServoMovesToStartingPos() {
        intake.servoState = Intake.ServoState.INTAKING;
        gamepad1.right_trigger = .5f;
        intake.update(gamepad1);
        gamepad1.right_trigger = 0f;
        intake.update(gamepad1);
        verify(axonServo, times(1)).setPosition(Intake.servoAngleToPos(30));
    }

    //@Test
    public void whenTriggerIsNotPressedAndWasNotJustPressedServoDoesNotMove() {
        intake.servoState = Intake.ServoState.INTAKING;
        gamepad1.right_trigger = 0.6f;
        intake.update(gamepad1);

        // pos should be set to starting only here
        gamepad1.right_trigger = 0f;
        intake.update(gamepad1);

        gamepad1.right_trigger = 0f;
        intake.update(gamepad1);
        verify(axonServo, times(1)).setPosition(anyDouble());
    }

    //@Test
    public void whenTriggerReleasedRollerMotorGoesInReverse() {
        intake.servoState = Intake.ServoState.INTAKING;
        // trigger pushed
        gamepad1.right_trigger = 0.6f;
        intake.update(gamepad1);
        // trigger released
        gamepad1.right_trigger = 0f;
        intake.update(gamepad1);

        verify(intakeRollerMotor).setDirection(DcMotorSimple.Direction.REVERSE);
    }

    //@Test
    public void whenTriggerReleasedRollerMotorReversesFor2Secs() {
        double startTime = System.currentTimeMillis();
        intake.runMotorFor2Seconds();
        double endTime = System.currentTimeMillis();
        double elapsedTime = endTime - startTime;
        assertTrue(elapsedTime <= 2100 && elapsedTime >= 1900);
    }
}
