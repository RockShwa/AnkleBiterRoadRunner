package centerstageTests.IntakeTests;

import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.CenterstageRobot.oldCode.oldIntake.Intake;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.StatesSubsystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class IntakeSubsystemTests {
    // Literally just trying to refactor, so no real to do list :D
    @Mock
    Servo axonServo;
    @Mock
    DcMotorEx intakeRollerMotor;

    @Mock
    Servo topBucketServo;

    @Mock
    Servo bottomBucketServo;

    private IntakeSubsystem intakeSub;

    @BeforeEach
    public void setUp() {
        intakeSub = new IntakeSubsystem(axonServo, intakeRollerMotor, topBucketServo, bottomBucketServo);
    }

    @Test
    public void testServoFullExtension() {
        intakeSub.extend();
        verify(axonServo).setPosition(StatesSubsystem.IntakeState.INTAKE_EXTENDED.getAxonPos());
    }

    @Test
    public void testServoCanIncrementToFullPos() {
        when(axonServo.getPosition()).thenReturn(Intake.servoAngleToPos(170));
        intakeSub.incrementPos();
        verify(axonServo).setPosition(StatesSubsystem.IntakeState.INTAKE_EXTENDED.getAxonPos());
    }

    @Test
    public void testServoCanIncrementToFullAndThenBackUp() {
        when(axonServo.getPosition()).thenReturn(Intake.servoAngleToPos(170.0));
        double currPos = axonServo.getPosition();
        intakeSub.incrementPos();

        when(axonServo.getPosition()).thenReturn(currPos + Intake.servoAngleToPos(10));
        intakeSub.incrementPos();
        verify(axonServo).setPosition(Intake.servoAngleToPos(10));
    }

    @Test
    public void rollerMotorRunsInReverseFor2Secs() {
        double startTime = System.currentTimeMillis();
        intakeSub.runMotorInReverseFor2Seconds();
        double endTime = System.currentTimeMillis();
        double elapsedTime = endTime - startTime;
        assertTrue(elapsedTime <= 2100 && elapsedTime >= 1900);
        verify(intakeRollerMotor).setDirection(DcMotorSimple.Direction.REVERSE);
    }

    @Test
    public void whenResetCalledBucketAndMotorResets() {
        intakeSub.resetMotorAndBucket();
        verify(topBucketServo).setPosition(0);
        verify(bottomBucketServo).setPosition(0);
        verify(intakeRollerMotor).setDirection(DcMotorSimple.Direction.FORWARD);
    }

    @Test
    public void testServoCanResetPosition() {
        intakeSub.resetAxonPosition();
        verify(axonServo).setPosition(StatesSubsystem.IntakeState.INTAKE_START.getAxonPos());
    }

    @Test
    public void testServoAngleToPos() {
        double pos = IntakeSubsystem.servoAngleToPos(150);
        double expected = 0.8333333333333334;
        assertEquals(expected, pos);
    }

    @Test
    public void testServoAnglePosIfZero() {
        double pos = IntakeSubsystem.servoAngleToPos(0);
        double expected = 0.0;
        assertEquals(expected, pos);
    }

    @Test
    public void testMotorNormalIntakeModeCanTurnOn() {
        intakeSub.intakeOn();
        verify(intakeRollerMotor).setDirection(DcMotorSimple.Direction.FORWARD);
        verify(intakeRollerMotor).setPower(anyDouble());
    }

    @Test
    public void testResetAll() {
        intakeSub.resetAll();
        verify(axonServo).setPosition(StatesSubsystem.IntakeState.INTAKE_START.getAxonPos());
        verify(axonServo).setDirection(Servo.Direction.FORWARD);
        verify(intakeRollerMotor).setDirection(DcMotorSimple.Direction.FORWARD);
        verify(topBucketServo).setPosition(0);
        verify(bottomBucketServo).setPosition(0);
    }
}
