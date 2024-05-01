package centerstageTests.subsystemTests;

import static org.mockito.Mockito.*;

import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.SlidesSubsystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@ExtendWith(MockitoExtension.class)
public class SlideSubsystemTests {
    // Slides, two motors, opp of each other, set one to opp position and then treat them as the same (set to equal power)
    //Spin on same axel, same speed.
    // Encoder in the motor, have a set input value, like 1000, and then we divide it however many positions we want for the
    // encoder pos (I think?)
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
}
