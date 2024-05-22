package org.firstinspires.ftc.teamcode.CenterstageRobot.vision;

import static org.opencv.imgproc.Imgproc.MORPH_ELLIPSE;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.dilate;
import static org.opencv.imgproc.Imgproc.erode;
import static org.opencv.imgproc.Imgproc.getStructuringElement;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class PixelDetectionSlingSim implements VisionProcessor {
    private static final Scalar BLUE = new Scalar(0, 0, 255);
    private static final Scalar GREEN = new Scalar(0, 255, 0);
    private static final Scalar BROWN = new Scalar(42, 42, 165);

    private boolean isBlue = false;

    //base items TODO: tune these
    private double[] EXPECTED_RATIO_VALS = {40, 25 }; //x, y (expected size)
    private final double EXPECTED_RATIO = EXPECTED_RATIO_VALS[0] / EXPECTED_RATIO_VALS[1];

    public double MATDIVLINE = 150;

    // remove extraneous info
    public double MINIMUM_AREA = 750;
    public double MAXIMUM_AREA = 100000;

    public double upperThres = 225;
    public double lowerThres = 480;
    private final Point EXPECTED_LOCATION = new Point(320 - (50 / 2.0), 300 - (75 / 2.0));
    //640x480

    public Rect bestRectLeft;
    public Rect bestRectRight;
    //ground

    // TODO: Make sure this is at the right location for any reasonable robot placement
    public Point GROUND_LEFT = new Point(290, 430);
    public Point GROUND_RIGHT = new Point(350, 450);

    public double correction = 0.0;
    public boolean foundCandidates = false;

    public boolean foundRightCandidate = false;
    public boolean foundLeftCandidate = false;

    double groundMean;
    //sim bools
    public int erodeSize = 16;
    public int dilateSize = 12;
    public int erodeSize2 = 4;

//    public Rect testerRect = new Rect(new Point(0, 0), new Point(20,20));

    public double thresIncrease = 65.0;
    public boolean seeThres = false;
    public boolean seeMorph = true;


    public PixelDetectionSlingSim() {
        this.isBlue = false;
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {

        //testers

        Mat grayscaleFrame = new Mat();
        Imgproc.cvtColor(frame, grayscaleFrame, Imgproc.COLOR_BGR2GRAY);

        Mat groundSubmat = grayscaleFrame.submat(new Rect(GROUND_LEFT, GROUND_RIGHT));
        // I'm pretty sure grayscale is the same number for all three channels
        groundMean = Core.mean(groundSubmat).val[0];
        Imgproc.rectangle(frame, Imgproc.boundingRect(groundSubmat), new Scalar(200, 175, 125), 2);



//        morphologicalTransformationsMatrix.copyTo(frame);

        // Threshold the image to obtain a binary image with white areas
        Mat thresholdMat = new Mat();
        Imgproc.threshold(grayscaleFrame, thresholdMat, groundMean+thresIncrease, 255, Imgproc.THRESH_BINARY);

        if(seeThres){thresholdMat.copyTo(frame);}
        // TODO: I'd suggest looking at this threshold image to see if it's what you expect
        // TODO: Its very important that we never get false positives, or detect stacks where they aren't supposed to be
        // TODO: TLDR: False negatives are ok, false positives are BAD

        //change the img with morphology
        Mat erodeKernel1 = getStructuringElement(MORPH_ELLIPSE,
                new Size(2 * erodeSize + 1, 2 * erodeSize + 1),
                new Point(erodeSize, erodeSize));
        Mat dilateKernel = getStructuringElement(MORPH_RECT,
                new Size(2 * dilateSize + 1, 2 * dilateSize + 5),
                new Point(dilateSize, dilateSize));
        Mat erodeKernel2 = getStructuringElement(MORPH_RECT,
                new Size(2 * erodeSize2 + 1, 2 * erodeSize2 + 1),
                new Point(erodeSize2, erodeSize2));

        Mat morphTransform = new Mat();

        // Interesting operation order, but if it works... ¯\_(ツ)_/¯
        dilate(thresholdMat, morphTransform, dilateKernel);
        erode(morphTransform, morphTransform, erodeKernel1);

        dilate(morphTransform, morphTransform, dilateKernel);

//        erode(morphTransform, morphTransform, erodeKernel2);

        if(seeMorph){morphTransform.copyTo(frame);}
        // Find contours in the binary image
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(morphTransform, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

//        morphTransform.copyTo(frame);


        double bestRatioRight = 10000000.0; // Filler number
        double bestRatioLeft = 10000000.0; // Filler number
        foundCandidates = false; // Ensures that calculations don't run if there are no valid contours
        foundLeftCandidate = false;
        foundRightCandidate = false;

        for (Mat x : contours) {
            Rect boundRect = Imgproc.boundingRect(x);
            //if the mat is within the upper and low limits
            if (boundRect.y+boundRect.height >= upperThres && boundRect.y + boundRect.height <= lowerThres) {
                // TODO: Remove rectangles that are too far from the center
                if (boundRect.area() >= MINIMUM_AREA && boundRect.area() <= MAXIMUM_AREA) {
                    // Draw candidate rectangles as blue
                    Imgproc.rectangle(frame, boundRect, BLUE, 2);

                    // Careful for integer division
                    double ratio = (double) boundRect.width / (double) boundRect.height;

                    // If current ratio is closer to expectedRatio than the previous best:
                    // update bestRatio and bestRect
                    if(boundRect.x + boundRect.width/2 > MATDIVLINE) {
                        //case right
                        if (Math.abs(ratio - EXPECTED_RATIO) < bestRatioRight) {
                            bestRatioRight = Math.abs(ratio - EXPECTED_RATIO);
                            bestRectRight = boundRect;
                            foundCandidates = true;
                            foundRightCandidate = true;
                        }
                    }else{
                        //case left (the center is to the left of the center)
                        if (Math.abs(ratio - EXPECTED_RATIO) < bestRatioLeft) {
                            bestRatioLeft = Math.abs(ratio - EXPECTED_RATIO);
                            bestRectLeft = boundRect;
                            foundLeftCandidate = true;
                            foundCandidates = true;
                        }

                    }

                    //                ratioDev.add(Math.abs(ratio - EXPECTED_RATIO));
                    //                    distDev.add(boundRect.x - boundRect.width / 2.0);
                    //                prunedContours.add(x);
                }
            }
        }
//        Imgproc.rectangle(frame, testerRect, new Scalar(0, 255,255), 2);
        correction = 0;

        if(foundRightCandidate) {
            Imgproc.rectangle(frame, bestRectRight, GREEN, 2);
            correction = bestRectRight.x - EXPECTED_LOCATION.x;
        }
        if(foundLeftCandidate) {
            Imgproc.rectangle(frame, bestRectLeft, GREEN, 2);
//            correction = bestRectRight.x - EXPECTED_LOCATION.x;
        }
        return frame;
    }



    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
    }
    public Rect getBestRectLeft(){
        return bestRectLeft;
    }
    public Rect getBestRectRight(){
        return bestRectRight;
    }
}
