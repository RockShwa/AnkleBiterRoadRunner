package org.firstinspires.ftc.teamcode.CommandBase;

import com.arcrobotics.ftclib.command.CommandBase;

public class DriveCommand extends CommandBase {
    Drivetrain driveSubsystem;

    // constructor

    @Override
    public void execute() {
        // tells the drive subsystem to continually update
        driveSubsystem.periodic();
    }
}
