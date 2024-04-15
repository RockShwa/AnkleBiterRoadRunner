package org.firstinspires.ftc.teamcode.CenterstageRobot;

import com.arcrobotics.ftclib.command.CommandOpMode;
import com.arcrobotics.ftclib.command.CommandScheduler;
import com.arcrobotics.ftclib.command.InstantCommand;
import com.arcrobotics.ftclib.command.SequentialCommandGroup;
import com.arcrobotics.ftclib.command.button.Button;
import com.arcrobotics.ftclib.command.button.Trigger;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.CenterstageRobot.Intake.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.CenterstageRobot.util.StatesSubsystem;

public class TeleOp extends CommandOpMode {
    // -- Subsystems --
    private StatesSubsystem states;
    private IntakeSubsystem intake;

    // -- Hardware (Can I put HardwareMap in states and still use tests?) --
    // Some init class for each subsystem?
    private Servo intakeAxonServo;
    private DcMotorEx intakeRollerMotor;
    private Servo topBucketServo;
    private Servo bottomBucketServo;

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

        intakeAxonServo = hardwareMap.get(Servo.class, "intake_servo");
        intakeRollerMotor = hardwareMap.get(DcMotorEx.class, "intake_motor");
        topBucketServo = hardwareMap.get(Servo.class, "top_bucket_servo");
        bottomBucketServo = hardwareMap.get(Servo.class, "bottom_bucket_servo");

        states = new StatesSubsystem();
        intake = new IntakeSubsystem(intakeAxonServo, intakeRollerMotor, topBucketServo, bottomBucketServo);

        register(states, intake);

        intakeButton = new Trigger(() -> gamepad1.right_trigger > 0.3);
        incrementIntake = driverPad.getGamepadButton(GamepadKeys.Button.A);
        fullExtendIntake = driverPad.getGamepadButton(GamepadKeys.Button.B);


        // When pressed, turn on extend, when pressed again, do resetAxon
        // Not sure if this is correct method, we shall see
        fullExtendIntake.toggleWhenPressed(
                new InstantCommand(() -> intake.extend()),
                new InstantCommand(() -> intake.resetAxonPosition())
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
