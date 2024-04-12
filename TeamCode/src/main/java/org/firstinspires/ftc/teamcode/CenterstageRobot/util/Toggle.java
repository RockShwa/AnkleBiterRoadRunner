package org.firstinspires.ftc.teamcode.CenterstageRobot.util;

public class Toggle {
    private boolean value;

    // If button is being held, the toggle is locked from changing until button is released
    private boolean buttonLock = false;

    // If toggle changed last update
    private boolean changed = false;

    // If turned true on last toggle (pressed)
    private boolean risingEdge = false;

    // If turned false on last toggle (released)
    private boolean fallingEdge = false;

    public Toggle(boolean initialValue) {
        this.value = initialValue;
    } //false

    public void update(boolean buttonPressed) {
        // first time pressed, true
        // Returns true if value has been toggled, false otherwise
        if (buttonPressed) { // true
            if (!buttonLock) { // !false = true
                value = !value; // true
                risingEdge = value; // false
                fallingEdge = !value; // true
                buttonLock = true;
                changed = true;
                return;
            }
        } else {buttonLock = false; }
        changed = false;
        risingEdge = false;
        fallingEdge = false;
    }

    public void set(boolean value) {this.value = value;}
    public boolean value() {return value;}
    public boolean changed() {return changed;}
    public boolean isFallingEdge() {return fallingEdge;}
    public boolean locked() {return buttonLock;}

}
