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

import java.util.Arrays;
import java.util.Collections;

public class StackDetectionTestToma implements VisionProcessor {
        private final Scalar BLUE = new Scalar(0, 0, 255);
        private final Scalar GREEN = new Scalar(0, 255, 0);
        private final Scalar RED = new Scalar(255, 0, 0);

        //center
        Point leftp1 = new Point(0, 224);
        Point leftp2 = new Point(267, 400);

        //right
        Point centerp1 = new Point(267, 224);
        Point centerp2 = new Point(533, 400);

        //ground color/shade
        Point rightp1 = new Point(533,224);
        Point rightp2 = new Point(800,400);


        Mat centerSubmat = new Mat();
        Mat leftSubmat = new Mat();
        Mat rightSubmat = new Mat();

        Mat YCrCb = new Mat();
        Mat Cb = new Mat();

        Scalar cMean;
        Scalar lMean;
        Scalar rMean;

        private final boolean useGround = true;

        private String pos = "center";

        private void inputToCb(Mat input) {
            Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
            Core.extractChannel(YCrCb, Cb, 2);
        }

        double rightValue;
        double centerValue;
        double leftValue;

        public String getType() {
            return pos;
        }

        @Override
        public void init(int width, int height, CameraCalibration calibration) {

        }

        @Override
        public Object processFrame(Mat frame, long captureTimeNanos) {
            inputToCb(frame);

            leftSubmat = frame.submat(new Rect(leftp1, leftp2));
            centerSubmat = frame.submat(new Rect(centerp1, centerp2));
            rightSubmat = frame.submat(new Rect(rightp1, rightp2));


            cMean = new Scalar((int)Core.mean(centerSubmat).val[0], (int)Core.mean(centerSubmat).val[1], (int) Core.mean(centerSubmat).val[2]);
            lMean = new Scalar((int)Core.mean(leftSubmat).val[0], (int)Core.mean(leftSubmat).val[1], (int)Core.mean(leftSubmat).val[2]);
            rMean = new Scalar((int)Core.mean(rightSubmat).val[0], (int)Core.mean(rightSubmat).val[1], (int)Core.mean(rightSubmat).val[2]);

            //if fully empty:
            //mean(n) = [135-147,140-152,150-170]
            //if zone:
            //mean(n)[2]: 25-60; (both red and blue)

            rightValue = rMean.val[1];
            centerValue = cMean.val[1];
            leftValue = lMean.val[1];
            String pos = "";

            Double[] vals = {rightValue, centerValue, leftValue};
            double max = Collections.max(Arrays.asList(vals));
            if (max == vals[0].doubleValue()) {
                pos = "right";
            } else if (max == vals[1].doubleValue()) {
                pos = "center";
            } else if (max == vals[2].doubleValue()) {
                pos = "left";
            }

            //draws rectangles where the submats are
            Imgproc.rectangle(frame, centerp1, centerp2, GREEN, 2);
            Imgproc.rectangle(frame, leftp1, leftp2, RED, 2);
            Imgproc.rectangle(frame, rightp1, rightp2, BLUE, 2);
            return pos;
        }

        @Override
        public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

        }
}
