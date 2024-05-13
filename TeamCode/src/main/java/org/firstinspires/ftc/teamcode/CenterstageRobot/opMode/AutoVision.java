package org.firstinspires.ftc.teamcode.CenterstageRobot.opMode;

import static org.firstinspires.ftc.teamcode.CenterstageRobot.vision.TeamPropProcessor.PropLocation.LEFT;

import android.util.Size;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.teamcode.CenterstageRobot.vision.TeamPropProcessor;
import org.firstinspires.ftc.vision.VisionPortal;


@Autonomous
public class AutoVision extends LinearOpMode {

    TeamPropProcessor processorPipeline;
    VisionPortal portal;
    @Override
    public void runOpMode() throws InterruptedException {

        processorPipeline = new TeamPropProcessor();

        portal = new VisionPortal.Builder()
                .setCamera(hardwareMap.get(CameraName.class, "Webcam 1"))
                .setCameraResolution(new Size(640, 480))
                .addProcessor(processorPipeline)
                .build();

        waitForStart();

        TeamPropProcessor.PropLocation propLocation = processorPipeline.getPropLocation();

        // Do stuff depending on prop location
//        switch (propLocation) {
//            case LEFT:
//                break;
//            case MIDDLE:
//                break;
//            case RIGHT:
//                break;
//            case NOT_FOUND:
//                break;
//        }
    }
}
