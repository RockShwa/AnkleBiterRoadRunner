package org.firstinspires.ftc.teamcode.CenterstageRobot.opMode;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.WaitUntilCommand;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.CenterstageRobot.hardware.BucketHardware;
import org.firstinspires.ftc.teamcode.CenterstageRobot.hardware.IntakeHardware;
import org.firstinspires.ftc.teamcode.CenterstageRobot.hardware.SlidesHardware;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.BucketSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.SlidesSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem.StatesSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.subsystemConstants.IntakeConstants;

public class TeleOp extends CommandOpMode {
    // -- Subsystems --
    private IntakeSubsystem intake;

    // TODO couple these into a transport subsystem/command?
    private BucketSubsystem bucket;
    private SlidesSubsystem slides;

    private StatesSubsystem states;

    // -- States/Constants --
    private IntakeConstants intakeConstants;

    // -- Hardware --
    private IntakeHardware intakeHardware;
    private BucketHardware bucketHardware;
    private SlidesHardware slidesHardware;

    // -- Gamepad --
    private GamepadEx driverPad;
    private Trigger intakeButton;
    private Button incrementIntake;
    private Button fullExtendIntake;
    private Trigger flipWrist;
    private Button slidesUp;
    private Button slidesDown;

    @Override
    public void initialize() {
        // Initialize
        CommandScheduler.getInstance().reset(); // reset scheduler
        driverPad = new GamepadEx(gamepad1);

        intakeHardware = new IntakeHardware(hardwareMap);
        bucketHardware = new BucketHardware(hardwareMap);
        slidesHardware = new SlidesHardware(hardwareMap);

        intakeConstants = new IntakeConstants();

        intake = new IntakeSubsystem(intakeHardware);
        bucket = new BucketSubsystem(bucketHardware);
        slides = new SlidesSubsystem(slidesHardware);
        states = new StatesSubsystem();

        register(intake, bucket, slides);

        intakeButton = new Trigger(() -> gamepad1.right_trigger > 0.3);
        incrementIntake = driverPad.getGamepadButton(GamepadKeys.Button.X);
        fullExtendIntake = driverPad.getGamepadButton(GamepadKeys.Button.B);

        flipWrist = new Trigger(() -> gamepad1.left_trigger > 0.3);
        slidesUp = driverPad.getGamepadButton(GamepadKeys.Button.Y);
        slidesDown = driverPad.getGamepadButton(GamepadKeys.Button.A);

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
                new SequentialCommandGroup(
                        new InstantCommand(() -> bucket.resetBucketInternal()),
                        new InstantCommand(() -> states.changeLiftState(StatesSubsystem.LiftState.INTAKING)),
                        new InstantCommand(() -> intake.intakeOn())
                )
        )
                .whenInactive(
                        new SequentialCommandGroup(
                                new InstantCommand(() -> intake.runMotorInReverseFor2Seconds()),
                                new InstantCommand(() -> intake.resetAxonPosition())
                        )
                );

        // not sure this is the correct active command, need to turn trigger on and flip whole arm, when pressed again, reset
        // need to make sure slides are in pos first, before this will execute
        flipWrist.whenActive(
                new SequentialCommandGroup(
                        new InstantCommand(() -> states.changeDepositState(StatesSubsystem.DepositState.IN_PROGRESS)),
                        new InstantCommand(() -> bucket.flipWrist()),
                        new InstantCommand(() -> bucket.wristToFull()),
                        new InstantCommand(() -> states.changeDepositState(StatesSubsystem.DepositState.READY))
                )
        )
                .whenInactive(
                        new InstantCommand(() -> bucket.resetAll())
                );

        slidesUp.whenHeld(
                new InstantCommand(() -> slides.adjustUp()));

        slidesDown.whenHeld(new InstantCommand(() -> slides.adjustDown()));
    }
}
