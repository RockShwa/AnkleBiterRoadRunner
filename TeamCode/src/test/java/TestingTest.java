import org.firstinspires.ftc.teamcode.drive.Testing;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TestingTest {
//    HardwareMap hardwareMap;
//    @Test
//    public void testIfMotorNameIsSetCorrectly() {
//        Testing test = new Testing(hardwareMap);
//        DcMotorEx expected = hardwareMap.get(DcMotorEx.class, "motor");
//        assertEquals(expected, test);
//    }
    @Rule
    public TestFeedbackWatcher testWatcher = new TestFeedbackWatcher();
    @Test
    public void addition_isCorrect() {
        Assert.assertEquals(4, 2 + 2);
    }
}
