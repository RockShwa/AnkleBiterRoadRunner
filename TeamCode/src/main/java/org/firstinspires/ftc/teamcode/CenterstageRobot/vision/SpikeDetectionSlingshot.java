package org.firstinspires.ftc.teamcode.CenterstageRobot.vision;

import android.graphics.Canvas;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Rect;
import org.opencv.core.Point;

public class SpikeDetectionSlingshot implements VisionProcessor {
    private static final Scalar BLUE = new Scalar(0, 0, 255);
    private static final Scalar GREEN = new Scalar(0, 255, 0);
    private static final Scalar RED = new Scalar(255, 0, 0);

    //640x480

    //center
    Point centerLeft = new Point(20, 210+90);
    Point centerRight = new Point(240, 300+90);

    //right
    Point rightLeft = new Point(390, 200+110);
    Point rightRight = new Point(580, 480);

    //ground color/shade
    Point groundLeft = new Point(290,430);
    Point groundRight = new Point(350,450);

    Mat centerSubmat = new Mat();
    Mat rightSubmat = new Mat();
    Mat groundSubmat = new Mat();

    Scalar cMean, rMean, gMean;

    private final boolean useGround = true;

    private SPIKE_POS pos = SPIKE_POS.CENTER;

    private void inputToCb(Mat input) {
        // TODO: Only use one channel?
        Mat YCrCb = new Mat();
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
    }


    public SPIKE_POS getType() {
        return pos;
    }
    public Scalar getcMean(){
        return cMean;
    }
    public Scalar getrMean(){
        return rMean;
    }
    public double getGroundC(){
        return gMean.val[1];
    }

    public String debug() {
        return String.format("Pipeline debug:\nCenter: %s\nRight: %s\nGround: %s\nColor: %s", cMean, rMean, gMean, pos);
    }

    @Override
    public void init(int width, int height, CameraCalibration calibration) {
    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        // TODO: Hold on, are we just not using YcrCb????m yes
        inputToCb(frame);

        centerSubmat = frame.submat(new Rect(centerLeft, centerRight));
        rightSubmat = frame.submat(new Rect(rightLeft, rightRight));
        groundSubmat = frame.submat(new Rect(groundLeft,groundRight));


        cMean = castScalarToInt(Core.mean(centerSubmat));
        rMean = castScalarToInt(Core.mean(rightSubmat));
        gMean = castScalarToInt(Core.mean(groundSubmat));

        double groundC = gMean.val[1] - 30;

        double centerValue = cMean.val[1];
        double rightValue = rMean.val[1];

        boolean center;
        boolean right;

        if (useGround) {
            center  = centerValue < groundC;
            right = rightValue < groundC;
        } else {
            center  = centerValue < 180;
            right = rightValue < 180;
        }

        if(center && right){
            pos = (centerValue < rightValue) ? SPIKE_POS.CENTER : SPIKE_POS.RIGHT;
        } else if (center){
            pos = SPIKE_POS.CENTER;
        } else if (right){
            pos = SPIKE_POS.RIGHT;
        } else {
            pos = SPIKE_POS.LEFT;
        }

        // draws rectangles where the submats are
        Imgproc.rectangle(frame, centerLeft, centerRight, center?GREEN:RED, 2);
        Imgproc.rectangle(frame, rightLeft, rightRight, right?GREEN:RED, 2);
        Imgproc.rectangle(frame, groundLeft, groundRight, BLUE, 2);
        return frame;
    }

    private Scalar castScalarToInt(Scalar s) {
        Scalar out = new Scalar(0,0,0,0);
        for (int i = 0; i < s.val.length; i++) {
            out.val[i] = (int)s.val[i];
        }
        return out;
    }

    @Override
    public void onDrawFrame(Canvas canvas, int onscreenWidth, int onscreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {

    }

    public enum SPIKE_POS {
        LEFT("LEFT", 0),
        CENTER("CENTER", 1),
        RIGHT("RIGHT", 2);

        private final String debug;
        private final int index;
        SPIKE_POS(String debug, int index) { this.debug = debug; this.index = index;}

        public String toString() { return debug; }
        public int index() { return index; }
    }
}
