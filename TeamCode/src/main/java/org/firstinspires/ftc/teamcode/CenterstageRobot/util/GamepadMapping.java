package org.firstinspires.ftc.teamcode.CenterstageRobot.util;

import com.qualcomm.robotcore.hardware.Gamepad;

public class GamepadMapping {
    private Gamepad gamepad1;
    private Gamepad gamepad2;

    // --Drive Functions--
    public static double forward = 0.0;
    public static  double strafe = 0.0;
    public static double rotate = 0.0;

    private boolean single;

    // --Intake Toggles--
    public static Toggle fullExtensionToggle;

    // Intake Functions
    public GamepadMapping(Gamepad gamepad1, Gamepad gamepad2) {
        this.gamepad1 = gamepad1;
        this.gamepad2 = gamepad2;

        fullExtensionToggle = new Toggle(false);
    }

    public void update() {
        if (single) {
            singleControllerUpdate();
        } else {
            doubleControllerUpdate();
        }
    }

    public void doubleControllerUpdate() {
        // implement if you want later
    }

    public void singleControllerUpdate() {
        // Drive
        forward = -gamepad1.left_stick_y; // y stick is reversed
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;

        fullExtensionToggle.update(gamepad1.b);
    }



}
