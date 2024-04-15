package org.firstinspires.ftc.teamcode.ProgrammingWorkshop;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Drivetrain extends SubsystemBase {
    private DcMotorEx leftFront;
    public Drivetrain(HardwareMap hardwareMap, Gamepad gamepad) {
        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
    }

    @Override
    public void periodic() {
        // Input mecanumdrive code here
    }
}
