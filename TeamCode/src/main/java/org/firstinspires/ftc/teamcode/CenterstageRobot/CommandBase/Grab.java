package org.firstinspires.ftc.teamcode.CenterstageRobot.CommandBase;

import com.arcrobotics.ftclib.command.CommandBase;

import kotlin.OptionalExpectation;

public class Grab extends CommandBase {
    Claw clawSubsystem;
    Arm armSubsystem;

    public Grab(Claw clawSubsystem, Arm armSubsystem) {
        // init
    }

    @Override
    public void initialize() {
        // only running once
    }

    @Override
    public boolean isFinished() {
        // timer, this is when it frees up the subsystems to be used by other things
        return false;
    }

}
