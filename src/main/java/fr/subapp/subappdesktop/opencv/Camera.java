package fr.subapp.subappdesktop.opencv;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgproc.Imgproc;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Camera extends JFrame {

    private JLabel cameraScreen = new JLabel();



    public static Mat extractDocument(Mat mat) {

        Mat img = mat.clone();
        Mat kernel = Mat.ones(5, 5, CvType.CV_8UC1);
        Imgproc.morphologyEx(img, img, Imgproc.MORPH_CLOSE, kernel, new Point(-1, -1), 3);



        // convert the image to grayscale, blur it, and find edges
        // in the image
        Mat gray = new Mat();
        Imgproc.cvtColor(img, gray, Imgproc.COLOR_BGR2GRAY);

        //background equalization
        Mat backgroundRemoved = new Mat();
        int maxValue = 255;
        Mat blurred = new Mat();
        Imgproc.GaussianBlur(gray, blurred, new Size(131, 131), 50);
        // gray/blurred image
        Core.subtract(gray, blurred, backgroundRemoved);
        //backgroundRemoved = (backgroundRemoved*max_value/np.max(backgroundRemoved)).astype(np.uint8)
        Core.multiply(backgroundRemoved, new Scalar(maxValue / Core.minMaxLoc(backgroundRemoved).maxVal), backgroundRemoved);







        Imgproc.GaussianBlur(backgroundRemoved, gray, new Size(5, 5), 0);






        Mat edged = new Mat();




        Imgproc.Canny(gray, edged, 75, 200);

        Imgproc.dilate(edged, edged, kernel, new Point(-1, -1), 2);

        // find the contours in the edged image, keeping only the
        // largest ones, and initialize the screen contour
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(edged, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);
        contours.sort((o1, o2) -> Double.compare(Imgproc.contourArea(o2), Imgproc.contourArea(o1)));

        MatOfPoint2f screenCnt = null;

        //loop over the contours
        for(MatOfPoint contour : contours) {
            // approximate the contour
            MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
            double approxDistance = Imgproc.arcLength(contour2f, true) * 0.02;
            MatOfPoint2f approx = new MatOfPoint2f();
            Imgproc.approxPolyDP(contour2f, approx, approxDistance, true);

            boolean valid = true;


            // if our approximated contour has four points, then
            // we can assume that we have found our screen

            if (approx.total() == 4) {
                //verify angles
                double[] angles = new double[4];
                for (int i = 0; i < 4; i++) {
                    Point p1 = approx.toArray()[i];
                    Point p2 = approx.toArray()[(i + 1) % 4];
                    Point p3 = approx.toArray()[(i + 2) % 4];
                    double dx1 = p1.x - p2.x;
                    double dy1 = p1.y - p2.y;
                    double dx2 = p3.x - p2.x;
                    double dy2 = p3.y - p2.y;
                    double angle = Math.abs(Math.atan2(dx1 * dy2 - dy1 * dx2, dx1 * dx2 + dy1 * dy2) * 180 / Math.PI);
                    angles[i] = angle;
                }
                for (double angle : angles) {

                    if (angle < 70 || angle > 110) {
                        valid = false;
                    }

                }
                //verify size
                double area = Imgproc.contourArea(approx);
                double ratio = area / (mat.cols() * mat.rows());
                if (ratio < 0.1) {
                    valid = false;
                }

                if (valid) {
                    screenCnt = approx;
                    break;
                }

            }
        }

        if (screenCnt == null) {
            System.out.println("No contour found");
            return mat;
        }

        MatOfPoint screenCnt2 = new MatOfPoint(screenCnt.toArray());

        //draw contour
        Imgproc.drawContours(mat, Collections.singletonList(screenCnt2), -1, new Scalar(0, 255, 0), 3);


        //apply the four point transform to obtain a top-down
        //view of the original image
        MatOfPoint2f approx = new MatOfPoint2f(screenCnt.toArray());
        MatOfPoint2f dst = new MatOfPoint2f(new Point(0, 0), new Point(mat.cols() - 1, 0), new Point(mat.cols() - 1, mat.rows() - 1), new Point(0, mat.rows() - 1));
        Mat transform = Imgproc.getPerspectiveTransform(approx, dst);
        Mat warped = new Mat();
        Imgproc.warpPerspective(mat, warped, transform, mat.size());
        Imgproc.resize(warped, warped, new Size(1000, 1000));
        return warped;
    }






    private static MatOfPoint getBiggestContour(List<MatOfPoint> contours) {
        double maxArea = 0;
        MatOfPoint biggestContour = null;
        for (MatOfPoint contour : contours) {
            double area = Imgproc.boundingRect(contour).area();
            if (area > maxArea) {
                maxArea = area;
                biggestContour = contour;
            }
        }
        return biggestContour;
    }



    public void startCamera() {

    }


    public static void main(String[] args) {
        OpenCV.loadLocally();
        WebCam camera = new WebCam();
        camera.setVisible(true);
        camera.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        camera.setSize(640, 480);
        camera.setLocationRelativeTo(null);
        camera.setTitle("Camera");
        camera.setResizable(false);
        camera.startCamera();


    }


    public static Mat getOuterCircle(Mat mat) {
//        OpenCV.loadLocally();
//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current absolute path is: " + s);
//        Mat mat = Imgcodecs.imread("./src/main/resources/img.png");
        Mat kernel = Mat.ones(5, 5, CvType.CV_8UC1);
        Mat redDots = new Mat();
        Core.inRange(mat, new Scalar(0, 0, 150), new Scalar(100, 100, 255), redDots);
        Imgproc.dilate(redDots, redDots, kernel, new Point(-1, -1), 1);
        Mat removeHoles = new Mat();
        Core.bitwise_not(redDots, redDots);
//        Imshow.show(redDots, "redDots");

        Core.bitwise_and(mat, mat, removeHoles, redDots);
//        Imshow.show(removeHoles, "removeHoles");
        Mat hsv = new Mat();
        Imgproc.cvtColor(removeHoles, hsv, Imgproc.COLOR_BGR2HSV);
        List<Mat> hsvChannels = new ArrayList<>();
        Core.split(hsv, hsvChannels);

        Mat value = hsvChannels.get(2);

        Mat value_mask = new Mat();
        Core.inRange(value, new Scalar(0), new Scalar(150), value_mask);
//        Imshow.show(value_mask, "value_mask");

        Mat close = new Mat();
        Imgproc.morphologyEx(value_mask, close, Imgproc.MORPH_CLOSE, kernel);

//        Imshow.show(close, "close");


        Mat open = new Mat();
        Mat kernel2 = Mat.ones(30, 30, CvType.CV_8UC1);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(close, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint biggestContour = getBiggestContour(contours);

        Imgproc.drawContours(close, Collections.singletonList(biggestContour), -1, new Scalar(255, 255, 255), Imgproc.FILLED);
//        Imshow.show(close, "filled");
        Imgproc.morphologyEx(close, open, Imgproc.MORPH_OPEN, kernel2);
//        Imshow.show(open, "open");


        List<MatOfPoint> newContours = new ArrayList<>();

        Imgproc.findContours(open, newContours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        biggestContour = getBiggestContour(newContours);
        if (biggestContour.toList().size() < 5) {
            return mat;
        }
        RotatedRect ellipse = Imgproc.fitEllipse(new MatOfPoint2f(biggestContour.toArray()));

        Mat empty = Mat.zeros(mat.size(), mat.type());
        Imgproc.ellipse(empty, ellipse, new Scalar(255, 255, 255), Imgproc.FILLED);
//        Imshow.show(empty, "ellipse");

        Imgproc.cvtColor(empty, empty, Imgproc.COLOR_BGR2GRAY);
        Mat xor = new Mat();
        Core.bitwise_xor(empty, open, xor);



        Imgproc.morphologyEx(xor, xor, Imgproc.MORPH_OPEN, kernel);
        Core.bitwise_not(xor, xor);
        Core.bitwise_and(open, xor, xor);

        List<MatOfPoint> xorContours = new ArrayList<>();
        Imgproc.findContours(xor, xorContours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        biggestContour = getBiggestContour(xorContours);
        if (biggestContour == null || biggestContour.toList().size() < 5) {
            return mat;
        }
        RotatedRect xorEllipse = Imgproc.fitEllipse(new MatOfPoint2f(biggestContour.toArray()));
        System.out.println(xorEllipse.size.width + " " + xorEllipse.size.height);
        if (xorEllipse.size.width < xorEllipse.size.height * 0.7 || xorEllipse.size.width > xorEllipse.size.height * 1.3) {
            return mat;
        }
        Imgproc.ellipse(mat, xorEllipse, new Scalar(0, 255, 0), 1);



//        Imshow.show(xor, "xor");






        Rect r = Imgproc.boundingRect(biggestContour);






        Imgproc.rectangle(mat, r.tl(), r.br(), new Scalar(0, 0, 255), 1);
//        Imshow.show(mat, "test");
        return mat;
    }

}
