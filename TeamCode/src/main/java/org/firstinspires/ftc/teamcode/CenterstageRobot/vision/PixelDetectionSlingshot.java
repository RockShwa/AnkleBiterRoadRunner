package org.firstinspires.ftc.teamcode.CenterstageRobot.vision;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class PixelDetectionSlingshot implements VisionProcessor {
    String debug;

    PType filter;

    // hsv hue range is 0-179, others are 0-255

    // 53 (360), 41(100), 48(100)
    Scalar red_color = new Scalar(202,95,36);
    Scalar red_l = new Scalar(
            scaleNum(210,360,179),
            scaleNum(20,100,255),
            scaleNum(70,100,255),
            0
    );
    Scalar red_h = new Scalar(
            scaleNum(240,360,179),
            scaleNum(100,100,255),
            scaleNum(100,100,255),
            255
    );



    Scalar blue_color = new Scalar(202,95,36);
    Scalar blue_l = new Scalar(
            scaleNum(210,360,179),
            scaleNum(20,100,255),
            scaleNum(70,100,255),
            0
    );
    Scalar blue_h = new Scalar(
            scaleNum(240,360,179),
            scaleNum(100,100,255),
            scaleNum(100,100,255),
            255
    );
    Scalar l = new Scalar(
            scaleNum(0,360,179),
            scaleNum(0,100,255),
            scaleNum(0,100,255),
            0
    );
    // H: [0-179]
    // S: [0-255]
    // V: [0-255]
    // A:
    Scalar h = new Scalar(
            scaleNum(360,360,179),
            scaleNum(100,100,255),
            scaleNum(100,100,255),
            255
    );

    static double PERCENT_COLOR_THRESHOLD = 0.4;

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
        debug = "";

//        filter = new Pixel_Type(red_color, red_l, red_h);
        filter = new PType(blue_color, l, h);
    }

    private Mat threshold(Mat input, PType filter) {
        Mat mask = new Mat();

        // Useful for detection because background for ftc is often grey mats
        // TODO: Check if it needs to be rgba
        Imgproc.cvtColor(input, mask, Imgproc.COLOR_RGB2HSV);

        // Extract pixels within the color range
        Core.inRange(mask, filter.getLowerHSV(), filter.getUpperHSV(), mask);
        return mask;
    }

    @Override
    public Mat processFrame(Mat frame, long captureTimeNanos) {
        // Convert from rgb to hsv
        Mat thresh = threshold(frame, filter);
//        Mat thresh = frame;

        // TODO: Remove noise?

        // Use Canny Edge Detection to find edges
        // you might have to tune the thresholds for hysteresis
//        Mat edges = new Mat();
//        Imgproc.Canny(mask, edges, 100, 300);

        // TODO: Check type and init more efficiently
        Mat kernel = new Mat(3,3, CvType.CV_8U);
        kernel.put(1,1,1);
        kernel.put(1,1,1);
        kernel.put(1,1,1);

        Mat opening = new Mat();
        Imgproc.morphologyEx(thresh, opening, Imgproc.MORPH_OPEN, kernel, new Point(-1,-1), 2);
        opening.convertTo(opening, CvType.CV_8UC3);

        Mat sureBackground = new Mat();
        Imgproc.dilate(opening, sureBackground, kernel, new Point(-1, -1), 3);

        Mat distTransform = new Mat();
        Imgproc.distanceTransform(opening, distTransform, Imgproc.DIST_L2, 5);
        Mat sureForeground = new Mat();
//        // TODO: 0.7*dist_transform.max()
//        // TODO: Check type
        Imgproc.threshold(distTransform, sureForeground,200, 255, Imgproc.THRESH_BINARY_INV);
//
//        // TODO: Check this
        Mat markers = new Mat(opening.size(), CvType.CV_8U, new Scalar(0));
        sureForeground.convertTo(sureForeground, CvType.CV_8U);
        sureBackground.convertTo(sureBackground, CvType.CV_8U);
        Core.add(sureForeground, sureBackground, markers);

//        markers.convertTo(markers, CvType.CV_32S);
//        Imgproc.watershed(opening, markers);
//        markers.convertTo(markers, CvType.CV_8U);
//
//        return markers;
        sureForeground.copyTo(frame);
        return frame;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

    private int scaleNum(int num, int num_scale, int target_scale) {
        return (int) ((double)num / (double)num_scale * (double)target_scale);
    }

    public String getDebug() {
        return debug;
    }

    private class PType {
        Scalar drawColor, lowerHSV, upperHSV;

        PType(Scalar drawColor, Scalar lowerHSV, Scalar upperHSV) {
            this.drawColor = drawColor;
            this.lowerHSV = lowerHSV;
            this.upperHSV = upperHSV;
        }

        public Scalar getDrawColor() {
            return drawColor;
        }
        public Scalar getLowerHSV() {
            return lowerHSV;
        }
        public Scalar getUpperHSV() {
            return upperHSV;
        }
    }
}
