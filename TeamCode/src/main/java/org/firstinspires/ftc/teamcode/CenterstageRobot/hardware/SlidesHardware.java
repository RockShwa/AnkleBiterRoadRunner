package org.firstinspires.ftc.teamcode.CenterstageRobot.hardware;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class SlidesHardware {
    public final DcMotorEx leftSlideMotor;
    public final DcMotorEx rightSlideMotor;

    public SlidesHardware(final HardwareMap hwMap) {
        leftSlideMotor = hwMap.get(DcMotorEx.class, "left_slide_motor");
        rightSlideMotor = hwMap.get(DcMotorEx.class, "right_slide_motor");
    }
}
