package org.firstinspires.ftc.teamcode.CenterstageRobot.vision;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TeamPropProcessor implements VisionProcessor {
    // This was a follow along of Ruckus Robotics' tutorial for OpenCV
    // Top left corner as 0,0 and bottom left will be width of frame, height of frame
    Rect LEFT_RECTANGLE;
    Rect MIDDLE_RECTANGLE;
    Rect RIGHT_RECTANGLE;

    Mat hsvMat = new Mat();
    Mat lowMat = new Mat();
    Mat highMat = new Mat();
    Mat detectedMat = new Mat();

    // HSV HUE IS 0-180, NOT 0-360
    Scalar lowerRedThresholdLow = new Scalar(0, 125, 125);
    Scalar lowerRedThresholdHigh = new Scalar(10, 255, 255);
    Scalar upperRedThresholdLow = new Scalar(165, 125, 125);
    Scalar upperRedThresholdHigh = new Scalar(180, 255, 255);

    // Can play around with these values
    double leftThreshold = 0.1;
    double middleThreshold = 0.1;
    double rightThreshold = 0.1;

    PropLocation propLocation = PropLocation.NOT_FOUND;

    @Override
    public void init(int width, int height, CameraCalibration cameraCalibration) {
        LEFT_RECTANGLE = new Rect(
                new Point(0, 0),
                new Point(0.33 * width, height)
        );

        MIDDLE_RECTANGLE = new Rect(
                new Point(0.33 * width, 0),
                new Point(0.66 * width, height)
        );

        RIGHT_RECTANGLE = new Rect(
                new Point(0.66 * width, 0),
                new Point(width, height)
        );
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        // frame has RGB values at a specific point
        // Don't want RGB because it's additive, harder to detect differences between colors
        Imgproc.cvtColor(frame, hsvMat, Imgproc.COLOR_RGB2HSV); // input, output, convert to
        // Red: Find these on a color slider thingy :)
            // Hue: 0-20 or 340-360
            // Saturation: 50%
            // Value: 50%

        // store all the color within the red ranges into lowMat
        // All pixels within the range will be white, and everything else will be black
        Core.inRange(hsvMat, lowerRedThresholdLow, lowerRedThresholdHigh, lowMat);
        Core.inRange(hsvMat, upperRedThresholdLow, upperRedThresholdHigh, highMat);

        // All this does is if in lowMat or highMat, if a point in either of them is white, we put it in detected mat
        // essentially combined the mats
        Core.bitwise_or(lowMat, highMat, detectedMat);

        // look at part of detectedMat that falls within the area of LEFT_RECTANGLE and sum the elements
        // Black returns 0 and white returns 255, higher num in this area means there's more white
        // val[0] just grabs that value of sumElems, divide by 255 to find the number of pixels within this area are white
        // Dividing by the area allows us to make a fair comparison among different sized rectangles: how many pixels were white in comparison to the area?
        double leftPercentage = (Core.sumElems(detectedMat.submat(LEFT_RECTANGLE)).val[0] / 255) / LEFT_RECTANGLE.area();
        double middlePercentage = (Core.sumElems(detectedMat.submat(MIDDLE_RECTANGLE)).val[0] / 255) / MIDDLE_RECTANGLE.area();
        double rightPercentage = (Core.sumElems(detectedMat.submat(RIGHT_RECTANGLE)).val[0] / 255) / RIGHT_RECTANGLE.area();

        // RGB
        Scalar redBorder = new Scalar(255, 0, 0);
        Scalar greenBorder = new Scalar(0, 255, 0);

        if (leftPercentage > middlePercentage && leftPercentage > rightPercentage && leftPercentage > leftThreshold) {
            // left is the greatest and it's greater than the threshold we set (so we pick up actual big blobs)
            propLocation = PropLocation.LEFT;

            // Not really supposed to do it this way (probably should find a way to put this in onDrawFrame)
            Imgproc.rectangle(frame, LEFT_RECTANGLE, greenBorder);
            Imgproc.rectangle(frame, MIDDLE_RECTANGLE, redBorder);
            Imgproc.rectangle(frame, RIGHT_RECTANGLE, redBorder);

        } else if (middlePercentage > leftPercentage && middlePercentage > rightPercentage && middlePercentage > middleThreshold) {
            propLocation = PropLocation.MIDDLE;

            Imgproc.rectangle(frame, LEFT_RECTANGLE, redBorder);
            Imgproc.rectangle(frame, MIDDLE_RECTANGLE, greenBorder);
            Imgproc.rectangle(frame, RIGHT_RECTANGLE, redBorder);

        } else if (rightPercentage > middlePercentage && rightPercentage > leftPercentage && rightPercentage > rightThreshold) {
            propLocation = PropLocation.RIGHT;

            Imgproc.rectangle(frame, LEFT_RECTANGLE, redBorder);
            Imgproc.rectangle(frame, MIDDLE_RECTANGLE, redBorder);
            Imgproc.rectangle(frame, RIGHT_RECTANGLE, greenBorder);

        } else {
            // object was not detected
            propLocation = PropLocation.NOT_FOUND;

            Imgproc.rectangle(frame, LEFT_RECTANGLE, redBorder);
            Imgproc.rectangle(frame, MIDDLE_RECTANGLE, redBorder);
            Imgproc.rectangle(frame, RIGHT_RECTANGLE, redBorder);
        }
        return null;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onScreenWidth, int onScreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

    public PropLocation getPropLocation() {
        return propLocation;
    }

    public enum PropLocation {
        LEFT(1),
        MIDDLE(2),
        RIGHT(3),
        NOT_FOUND(0);

        public final int posNum;

        PropLocation(int posNum) {
            this.posNum = posNum;
        }
    }
}
