package org.firstinspires.ftc.teamcode.CenterstageRobot.vision;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import org.opencv.core.Rect;

import org.firstinspires.ftc.robotcore.internal.camera.calibration.CameraCalibration;
import org.firstinspires.ftc.vision.VisionProcessor;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

public class TestingVisionPipeline implements VisionProcessor {

    private final Scalar BLUE = new Scalar(0, 0, 255);
    private final Scalar GREEN = new Scalar(0, 255, 0);
    private final Scalar RED = new Scalar(255, 0, 0);
    @Override
    public void init(int width, int height, CameraCalibration cameraCalibration) {

    }

    @Override
    public Object processFrame(Mat frame, long captureTimeNanos) {
        //Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGB2GRAY);
        return null;
    }

    // This scales the OpenCV coordinate system to Canvas
//    private android.graphics.Rect makeGraphicsRect(Rect rect, float scaleBmpPxToCanvasPx) {
//        int left = Math.round(rect.x * scaleBmpPxToCanvasPx);
//        int top = Math.round(rect.y * scaleBmpPxToCanvasPx);
//        int right = left + Math.round(rect.width * scaleBmpPxToCanvasPx);
//        int bottom = top + Math.round(rect.height * scaleBmpPxToCanvasPx);
//
//        return new android.graphics.Rect(left, top, right, bottom);
//    }

    @Override
    public void onDrawFrame(Canvas canvas, int onScreenWidth, int onScreenHeight, float scaleBmpPxToCanvasPx, float scaleCanvasDensity, Object userContext) {
        Rect rect = new Rect(20, 20, 50, 50);

        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.RED);
        rectPaint.setStyle(Paint.Style.STROKE);
        rectPaint.setStrokeWidth(scaleCanvasDensity * 4);

        //canvas.drawRect(makeGraphicsRect(rect, scaleBmpPxToCanvasPx), rectPaint);
    }
}
