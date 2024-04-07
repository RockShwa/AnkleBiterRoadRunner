# CenterStage Robot Notes
- Intake powered by one motor
- Normal drive motors, power is same for each
- Slides, two motors, opp of each other, set one to opp position and then treat them as the same (set to equal power)
Spin on same axel, same speed. 
- Axon servo for the dropdown to stack intake (first mode), degree of rotation is from 30 degrees to 150 degrees for the stack.
Second mode (reach ground) is from 30 degrees to 180 degrees. 30 is start position
  - Do enum for the mode

## Questions
- Does the one motor power both rollers? If so, how?

## TODO List
1) Drivetrain
  - Test behavior?
2) Slides (see Slingshot code)
  - Two motors - set one opp direction
  - They need to spin on the same axel at the same speed
3) Intake (see Tomahawk code)
   - Motor continuously spins intake
   - Servo moves the intake up and down 
     1) Mode 1: dropdown to stack (30-150)
     2) Mode 2: dropdown to ground (30-180)
     3) Mode 3: holding (at 30)