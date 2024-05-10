package centerstageTests.subsystemTests;

import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.configuration.annotations.ServoType;

import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.BucketSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.IntakeSubsystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BucketSubsystemTests {
    @Mock
    Servo wristAxon;
    @Mock
    Servo bucketServoTop;
    @Mock
    Servo bucketServoBottom;
    @Mock
    Servo flipAxonLeft;
    @Mock
    Servo flipAxonRight;

    private BucketSubsystem bucketSub;

    @BeforeEach
    public void setUp() {
        bucketSub = new BucketSubsystem(wristAxon, bucketServoTop, bucketServoBottom, flipAxonLeft, flipAxonRight);
    }

    @Test
    public void testWristServoMovesToFullPos() {
        bucketSub.wristToFull();
        verify(wristAxon).setPosition(anyDouble());
    }

    @Test
    public void testFlipServosTogetherMoveToFullPos() {
        bucketSub.flipWrist();
        verify(flipAxonLeft).setPosition(anyDouble());
        verify(flipAxonRight).setPosition(anyDouble());
    }

    @Test
    public void whenResetCalledBucketInternalResets() {
        bucketSub.resetBucketInternal();
        verify(bucketServoTop).setPosition(0);
        verify(bucketServoBottom).setPosition(0);
    }

    @Test
    public void testTotalReset() {
        bucketSub.resetAll();
        verify(bucketServoTop).setPosition(0);
        verify(bucketServoBottom).setPosition(0);
        verify(flipAxonLeft).setPosition(0);
        verify(flipAxonRight).setPosition(0);
        verify(wristAxon).setPosition(0);
    }
}
