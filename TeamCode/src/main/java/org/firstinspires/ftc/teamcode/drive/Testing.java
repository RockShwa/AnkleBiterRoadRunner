package org.firstinspires.ftc.teamcode.drive;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class Testing {
    private DcMotorEx motor;

    public Testing (HardwareMap hardwareMap) {
        motor = hardwareMap.get(DcMotorEx.class, "motor");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        if (motor.getDeviceName() == ((Testing) obj).motor.getDeviceName()) {
            return true;
        }
        return false;
    }

}
