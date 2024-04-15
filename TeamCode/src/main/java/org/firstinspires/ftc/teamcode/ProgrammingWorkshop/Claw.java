package org.firstinspires.ftc.teamcode.ProgrammingWorkshop;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw extends SubsystemBase {
    Servo leftGrip, rightGrip;
    public Claw(HardwareMap hardwareMap) {
        leftGrip = hardwareMap.get(Servo.class, "leftGrip");
        rightGrip = hardwareMap.get(Servo.class, "rightGrip");
    }

    public void grab() {
        // set the grab pos for servos
    }
}
