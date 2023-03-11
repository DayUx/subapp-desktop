package fr.subapp.subappdesktop.opencv;

import fr.subapp.subappdesktop.model.ImpactDTO;
import fr.subapp.subappdesktop.utils.Zone;
import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Camera extends JFrame {


    private static final Scalar pointColor = new Scalar(28,50,129);

    private JLabel cameraScreen = new JLabel();


    public static Mat getTopLeftTarget(Mat mat) {
        Mat img = mat.clone();
        Rect rectCrop = new Rect(0, 0, img.width() / 2, img.height() / 2);
        Mat croppedImage = new Mat(img, rectCrop);
        return croppedImage;
    }

    public static Mat getTopRightTarget(Mat mat) {
        Mat img = mat.clone();
        Rect rectCrop = new Rect(img.width() / 2, 0, img.width() / 2, img.height() / 2);
        Mat croppedImage = new Mat(img, rectCrop);
        return croppedImage;
    }

    public static Mat getBottomLeftTarget(Mat mat) {
        Mat img = mat.clone();
        Rect rectCrop = new Rect(0, img.height() / 2, img.width() / 2, img.height() / 2);
        Mat croppedImage = new Mat(img, rectCrop);
        return croppedImage;
    }

    public static Mat getBottomRightTarget(Mat mat) {
        Mat img = mat.clone();
        Rect rectCrop = new Rect(img.width() / 2, img.height() / 2, img.width() / 2, img.height() / 2);
        Mat croppedImage = new Mat(img, rectCrop);
        return croppedImage;
    }

    public static Mat getCenterTarget(Mat mat) {
        Mat img = mat.clone();
        Rect rectCrop = new Rect(img.width() / 4, img.height() / 4, img.width() / 2, img.height() / 2);
        Mat croppedImage = new Mat(img, rectCrop);
        return croppedImage;
    }


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
        for (MatOfPoint contour : contours) {
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


    public static float getDistance(Point p1, Point p2) {
        return (float) Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
    }

    public static float getAngle(Point p1, Point p2) {
        return (float) Math.atan2(p2.y - p1.y, p2.x - p1.x);
    }


    public static int getPoint(Point center, Point border, Point impact) {
        double length = getDistance(center, border);
        double distance = getDistance(center, impact);
        double percent = distance / length;
        int realLength = 45;
        int realDistance = (int) Math.ceil(realLength * percent);
        int point = 570;
        int i = 0;
        if (realDistance > 45) {
            return 0;
        }
        for (; i < 5 && realDistance > 0; i++) {
            point -= 6;
            realDistance -= 1;
        }
        for (; i < 48 && realDistance > 0; i++) {
            point -= 3;
            realDistance -= 1;
        }
        return point;
    }


    public static Point getPointOnEllipse(Point center, double radiusX, double radiusY, double angle) {
        return new Point(center.x + Math.cos(angle) * radiusX, center.y + Math.sin(angle) * radiusY);
    }


    public static Point rotatePoint(Point center, Point point, double angle) {
        double s = Math.sin(angle);
        double c = Math.cos(angle);

        // translate point back to origin:
        point.x -= center.x;
        point.y -= center.y;

        // rotate point
        double xnew = point.x * c - point.y * s;
        double ynew = point.x * s + point.y * c;

        // translate point back:
        point.x = xnew + center.x;
        point.y = ynew + center.y;
        return point;
    }


    public static List<Point> getPoints(Mat mat) {
        //draw a circle
        Mat circle = new Mat(mat.size(), CvType.CV_8UC1, new Scalar(0, 0, 0));
        Mat redDots = new Mat();

//        image_blur_hsv = cv2.cvtColor(image_blur, cv2.COLOR_RGB2HSV)
//        min_ora = np.array([10, 50, 50])
//        max_ora = np.array([30, 255, 255])
//        mask1 = cv2.inRange(image_blur_hsv, min_ora, max_ora)
//        min_ora2 = np.array([170, 50, 50])
//        max_ora2 = np.array([180, 255, 255])
//        mask2 = cv2.inRange(image_blur_hsv, min_ora2, max_ora2)
//        mask = mask1 + mask2

        Mat hsv = new Mat();
        Imgproc.cvtColor(mat, hsv, Imgproc.COLOR_BGR2HSV);

        List<Mat> hsvChannels = new ArrayList<>();
        Core.split(hsv, hsvChannels);
        redDots = getColor(mat, pointColor);
//        Imshow.show(redDots, "redDots");
//        Imshow.show(hsv, "hsv");
        Mat kernel = Mat.ones(3, 3, CvType.CV_8UC1);
        Imgproc.dilate(redDots, redDots, kernel, new Point(-1, -1), 1);
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(redDots, contours, new Mat(), Imgproc.RETR_LIST, Imgproc.CHAIN_APPROX_SIMPLE);

        List<Point> points = new ArrayList<>();
        for (MatOfPoint contour : contours) {
            if (contour.size().height < 5) {
                continue;
            }
            RotatedRect impact = Imgproc.fitEllipse(new MatOfPoint2f(contour.toArray()));
            points.add(impact.center);
        }
        return points;
    }

    public static Mat getColor(Mat mat, Scalar color) {
        Mat colorMat = new Mat(new Size(1,1), CvType.CV_8UC3, color);
        Mat hsv = new Mat();
        Imgproc.cvtColor(colorMat, hsv, Imgproc.COLOR_BGR2HSV);
        Scalar min = new Scalar(hsv.get(0, 0)[0] - 10, 100, 50);
        Scalar max = new Scalar(hsv.get(0, 0)[0] + 10, 255, 255);
        Mat mask = new Mat();
        Mat hsvMat = new Mat();
        Imgproc.cvtColor(mat, hsvMat, Imgproc.COLOR_BGR2HSV);
        Core.inRange(hsvMat, min, max, mask);
        Mat kernel = Mat.ones(3, 3, CvType.CV_8UC1);
        Imgproc.morphologyEx(mask, mask, Imgproc.MORPH_OPEN, kernel);
        return mask;
    }


    public static RotatedRect getOuterCircle(Mat mat) {
//        OpenCV.loadLocally();
//        Path currentRelativePath = Paths.get("");
//        String s = currentRelativePath.toAbsolutePath().toString();
//        System.out.println("Current absolute path is: " + s);
//        Mat mat = Imgcodecs.imread("./src/main/resources/img.png");
        boolean show = false;
        //draw a circle
        Mat circle = new Mat(mat.size(), CvType.CV_8UC1, new Scalar(0, 0, 0));
        Imgproc.circle(circle, new Point(mat.cols() / 2, mat.rows() / 2), (int) (mat.cols() / 2.2), new Scalar(255, 255, 255), -1);
        if (show) {


            Imshow.show(circle, "circle");
        }
        Mat kernel = Mat.ones(5, 5, CvType.CV_8UC1);


        Mat redDots = getColor(mat, pointColor);
        Imgproc.dilate(redDots, redDots, kernel, new Point(-1, -1), 1);
        Mat removeHoles = new Mat();
        Core.bitwise_not(redDots, redDots);
        if (show) {
            Imshow.show(redDots, "redDots");
        }
//        Imshow.show(redDots, "redDots");

        Core.bitwise_and(mat, mat, removeHoles, redDots);
        if (show) {
            Imshow.show(removeHoles, "removeHoles");
        }
//        Imshow.show(removeHoles, "removeHoles");
        Mat hsv = new Mat();
        Imgproc.cvtColor(removeHoles, hsv, Imgproc.COLOR_BGR2HSV);
        List<Mat> hsvChannels = new ArrayList<>();
        Core.split(hsv, hsvChannels);

        Mat value = hsvChannels.get(2);

        Mat value_mask = new Mat();
        Core.MinMaxLocResult minMaxLocResult = Core.minMaxLoc(value);
        double min = minMaxLocResult.minVal;
        double max = minMaxLocResult.maxVal;
        Core.inRange(value, new Scalar(min), new Scalar(max / 2), value_mask);
        Core.bitwise_and(value_mask, circle, value_mask);

        if (show) {
            Imshow.show(value_mask, "value_mask");
        }
//        Imshow.show(value_mask, "value_mask");

        Mat close = new Mat();
        Imgproc.morphologyEx(value_mask, close, Imgproc.MORPH_CLOSE, kernel);
        if (show) {
            Imshow.show(close, "close");
        }

//        Imshow.show(close, "close");


        Mat open = new Mat();
        Mat kernel2 = Mat.ones(30, 30, CvType.CV_8UC1);

        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(close, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfPoint biggestContour = getBiggestContour(contours);

        Imgproc.drawContours(close, Collections.singletonList(biggestContour), -1, new Scalar(255, 255, 255), -1);
//        Imshow.show(close, "filled");
        Imgproc.morphologyEx(close, open, Imgproc.MORPH_OPEN, kernel2);
//        Imshow.show(open, "open");


        List<MatOfPoint> newContours = new ArrayList<>();

        Imgproc.findContours(open, newContours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
        biggestContour = getBiggestContour(newContours);
        if (biggestContour.toList().size() < 5) {
            return null;
        }
        RotatedRect ellipse = Imgproc.fitEllipse(new MatOfPoint2f(biggestContour.toArray()));

        Mat empty = Mat.zeros(mat.size(), mat.type());
        Imgproc.ellipse(empty, ellipse, new Scalar(255, 255, 255), Imgproc.FILLED);
        Imgproc.circle(mat, ellipse.center, 2, new Scalar(0, 0, 255), -1);
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
            return null;
        }
        RotatedRect xorEllipse = Imgproc.fitEllipse(new MatOfPoint2f(biggestContour.toArray()));
        System.out.println(xorEllipse.size.width + " " + xorEllipse.size.height);
        if (xorEllipse.size.width < xorEllipse.size.height * 0.7 || xorEllipse.size.width > xorEllipse.size.height * 1.3) {
            return null;
        }
        Imgproc.ellipse(mat, xorEllipse, new Scalar(0, 255, 0), 1);


//        Imshow.show(xor, "xor");


        Rect r = Imgproc.boundingRect(biggestContour);


        Imgproc.rectangle(mat, r.tl(), r.br(), new Scalar(0, 0, 255), 1);
        if (show) {
            Imshow.show(mat, "test");
        }
        return ellipse;
    }


    public static byte[] getResult(byte[] image) {

        Mat mat = Imgcodecs.imdecode(new MatOfByte(image), Imgcodecs.IMREAD_COLOR);
        byte[] imageData;
        mat = Camera.extractDocument(mat);
        RotatedRect ellipseTopLeft = Camera.getOuterCircle(Camera.getTopLeftTarget(mat));
        RotatedRect ellipseTopRight = Camera.getOuterCircle(Camera.getTopRightTarget(mat));
        if (ellipseTopRight != null) {
            ellipseTopRight.center.x = ellipseTopRight.center.x + mat.size().width / 2;
        } else {
            return null;
        }
        RotatedRect ellipseCenter = Camera.getOuterCircle(Camera.getCenterTarget(mat));
        if (ellipseCenter != null) {
            ellipseCenter.center.x = ellipseCenter.center.x + mat.size().width / 4;
            ellipseCenter.center.y = ellipseCenter.center.y + mat.size().height / 4;
        } else {
            return null;
        }
        RotatedRect ellipseBottomLeft = Camera.getOuterCircle(Camera.getBottomLeftTarget(mat));
        if (ellipseBottomLeft != null) {
            ellipseBottomLeft.center.y = ellipseBottomLeft.center.y + mat.size().height / 2;
        } else {
            return null;
        }
        RotatedRect ellipseBottomRight = Camera.getOuterCircle(Camera.getBottomRightTarget(mat));
        if (ellipseBottomRight != null) {
            ellipseBottomRight.center.x = ellipseBottomRight.center.x + mat.size().width / 2;
            ellipseBottomRight.center.y = ellipseBottomRight.center.y + mat.size().height / 2;
        } else {
            return null;
        }
        List<Point> impactPoints = Camera.getPoints(mat);
        HashMap<Zone, RotatedRect> ellipseMap = new HashMap<>();
        ellipseMap.put(Zone.TOP_LEFT, ellipseTopLeft);
        ellipseMap.put(Zone.TOP_RIGHT, ellipseTopRight);
        ellipseMap.put(Zone.CENTER, ellipseCenter);
        ellipseMap.put(Zone.BOTTOM_LEFT, ellipseBottomLeft);
        ellipseMap.put(Zone.BOTTOM_RIGHT, ellipseBottomRight);
        List<ImpactDTO> impactDTOList = new ArrayList<>();
        HashMap<Zone, List<Point>> impactMap = new HashMap<>();
        for (Point point : impactPoints) {
            Imgproc.circle(mat, point, 1, new Scalar(0, 255, 0), -1);
            Zone zoneToInsert = null;
            float minDistance = Float.MAX_VALUE;
            for (Zone zone : ellipseMap.keySet()) {
                if (minDistance > Camera.getDistance(point, ellipseMap.get(zone).center)) {
                    minDistance = Camera.getDistance(point, ellipseMap.get(zone).center);
                    zoneToInsert = zone;
                }
            }
            System.out.println(zoneToInsert);
            if (impactMap.containsKey(zoneToInsert)) {
                impactMap.get(zoneToInsert).add(point);
            } else {
                List<Point> pointList = new ArrayList<>();
                pointList.add(point);
                impactMap.put(zoneToInsert, pointList);
            }
        }
        for (Zone zone : impactMap.keySet()) {
            for (Point point : impactMap.get(zone)) {
                double angle = Camera.getAngle(point, ellipseMap.get(zone).center) + Math.toRadians(360);
                double ellipseAngle = Math.toRadians(ellipseMap.get(zone).angle +360);
                angle = angle - (ellipseAngle);
                System.out.println(angle);
                Point pointOnEllipse = Camera.getPointOnEllipse(ellipseMap.get(zone).center, ellipseMap.get(zone).size.width/2,  ellipseMap.get(zone).size.height/2, angle);
                pointOnEllipse = Camera.rotatePoint(ellipseMap.get(zone).center,pointOnEllipse, ellipseAngle + Math.toRadians(180));
                Imgproc.circle(mat, pointOnEllipse, 2, new Scalar(255, 0, 0), -1);
                Imgproc.putText(mat, String.valueOf(Camera.getPoint(ellipseMap.get(zone).center, pointOnEllipse, point)), point, 1, 2, new Scalar(0, 255, 0), 2);
            }
        }
        final MatOfByte buf = new MatOfByte();
        Mat dst = mat.clone();
        Imgcodecs.imencode(".jpg", dst, buf);
        imageData = buf.toArray();
        return imageData;
    }



}
