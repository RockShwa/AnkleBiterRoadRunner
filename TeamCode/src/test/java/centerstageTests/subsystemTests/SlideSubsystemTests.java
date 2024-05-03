package centerstageTests.subsystemTests;

import static org.mockito.Mockito.*;

import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.util.Encoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@ExtendWith(MockitoExtension.class)
public class SlideSubsystemTests {
    // Figure out corner cases for adjust up; like going above limit for slide height
    // I think I need a transport constants of some sort with Ticks per revolution, max heights, etc.
    // need to figure out how to test the Encoder class, injecting mocks somehow?
    @Mock
    DcMotorEx leftSlideMotor;
    @Mock
    DcMotorEx rightSlideMotor;

    SlidesSubsystem slideSub;

    @BeforeEach
    public void setUp() {
        slideSub = new SlidesSubsystem(leftSlideMotor, rightSlideMotor);
    }

    @Test
    public void testResetSlideMotors() {
        slideSub.reset();
        verify(rightSlideMotor).setDirection(DcMotorSimple.Direction.REVERSE);
        verify(leftSlideMotor).setDirection(DcMotorSimple.Direction.FORWARD);
        verify(rightSlideMotor).setPower(0);
        verify(leftSlideMotor).setPower(0);
        verify(rightSlideMotor).setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    // We need to iterate through slide heights, best way to do this? Could iterate through with one button, a bumper?
    @Test
    public void testIdealAdjustUp() {
        when(rightSlideMotor.getCurrentPosition()).thenReturn(100);
        slideSub.adjustUp();
        verify(rightSlideMotor).setTargetPosition(400);
        verify(leftSlideMotor).setTargetPosition(400);
    }
    @Test
    public void testIdealAdjustDown() {
        when(rightSlideMotor.getCurrentPosition()).thenReturn(300);
        slideSub.adjustDown();
        verify(rightSlideMotor).setTargetPosition(0);
        verify(leftSlideMotor).setTargetPosition(0);
    }
    @Test
    public void testSetLiftPos() {
        slideSub.setLiftPosition(100);
        verify(leftSlideMotor).setTargetPosition(anyInt());
        verify(rightSlideMotor).setTargetPosition(anyInt());
    }

}
