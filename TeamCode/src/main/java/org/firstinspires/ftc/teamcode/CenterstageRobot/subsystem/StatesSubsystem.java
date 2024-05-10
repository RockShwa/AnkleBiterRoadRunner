package org.firstinspires.ftc.teamcode.CenterstageRobot.subsystem;

import com.arcrobotics.ftclib.command.SubsystemBase;

public class StatesSubsystem extends SubsystemBase {
    // States for slides: in progress, ready (for deposit)
    // States for deposit: in progress, ready (to flip back)

    public enum LiftState {
        INTAKING, DEPOSIT
    }

    public enum DepositState {
        // Ready to flip back
        READY,
        // In the process of depositing
        IN_PROGRESS
    }

    public LiftState liftState;
    public DepositState depositState;

    public StatesSubsystem() {
        liftState = LiftState.INTAKING;
        depositState = DepositState.READY;
    }

    public void changeLiftState(LiftState newState) {
        liftState = newState;
    }

    public void changeDepositState(DepositState newState) {
        depositState = newState;
    }
}
