package org.firstinspires.ftc.teamcode.CenterstageRobot.CommandBase;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Arm extends SubsystemBase {
    // the two axonServos, right and left
    private Servo axonLeft, axonRight, clawServo;

    public Arm(HardwareMap hardwareMap) {
        axonLeft = hardwareMap.get(Servo.class, "axonLeft");
        axonRight = hardwareMap.get(Servo.class, "axonRight");
        clawServo = hardwareMap.get(Servo.class, "clawServo");
    }

    public void grab() {
        axonLeft.setPosition(0.313);
        axonRight.setPosition(0.313);
    }

    public void deposit() {
        axonLeft.setPosition(0.7052);
        axonRight.setPosition(0.7052);
    }
}
