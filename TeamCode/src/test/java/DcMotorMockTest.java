import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

@ExtendWith(MockitoExtension.class)
public class DcMotorMockTest {
    @Mock
    DcMotorEx motor;

    @Test
    public void doesMotorPositionAngleGetConvertedToDegrees() {
        // Get DcMotor currentPosition and convert that to degrees
        // 360 degrees = 538 ticks per revolution
        when(motor.getCurrentPosition()).thenReturn(269);
        int ticks = motor.getCurrentPosition();
        // This would be in a different class so both ControlHub and tests can access it
        // This is the actual "method" I'm testing (line 29)
        int angle = 360/(538/ticks);
        assertEquals(180, angle);
    }

}
