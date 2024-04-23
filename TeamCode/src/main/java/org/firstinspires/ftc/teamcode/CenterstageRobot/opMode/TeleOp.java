package org.firstinspires.ftc.teamcode.CenterstageRobot.opMode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.CenterstageRobot.hardware.IntakeHardware;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.StatesSubsystem;

public class TeleOp extends CommandOpMode {
    // -- Subsystems --
    private StatesSubsystem states;
    private IntakeSubsystem intake;

    // -- Hardware --

    private IntakeHardware intakeHardware;

    // -- Gamepad --
    private GamepadEx driverPad;
    private Trigger intakeButton;
    private Button incrementIntake;
    private Button fullExtendIntake;

    // might need a switch states trigger
    // private Trigger switchIntakeState;


    @Override
    public void initialize() {
        // Initialize
        CommandScheduler.getInstance().reset(); // reset scheduler
        driverPad = new GamepadEx(gamepad1);

        intakeHardware = new IntakeHardware(hardwareMap);

        states = new StatesSubsystem();
        intake = new IntakeSubsystem(intakeHardware);

        register(states, intake);

        intakeButton = new Trigger(() -> gamepad1.right_trigger > 0.3);
        incrementIntake = driverPad.getGamepadButton(GamepadKeys.Button.A);
        fullExtendIntake = driverPad.getGamepadButton(GamepadKeys.Button.B);


        // When pressed, turn on extend, when pressed again, do resetAxon
        fullExtendIntake.toggleWhenPressed(
                    new InstantCommand(() -> intake.resetAxonPosition()),
                    new InstantCommand(() -> intake.extend())
        );

        incrementIntake.whenPressed(
                new InstantCommand(() -> intake.incrementPos())
        );

        // This is apparently set to be interruptable, test that later?
        intakeButton.whenActive(
                new InstantCommand(() -> intake.intakeOn())
        )
                .whenInactive(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> states.changeIntakeState(StatesSubsystem.IntakeState.REVERSE)),
                                new InstantCommand(() -> intake.runMotorInReverseFor2Seconds()),
                                new InstantCommand(() -> intake.resetAxonPosition())
                        )
                );
    }
}
