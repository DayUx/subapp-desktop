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
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WebCam extends JFrame {

    private JLabel cameraScreen = new JLabel();

    private Mat template;


    public static void main(String[] args) {
        OpenCV.loadLocally();
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                WebCam camera = new WebCam();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        camera.startCamera();
                    }
                }).start();
            }
        });

    }


    public void startCamera() {

        VideoCapture videoCapture = new VideoCapture(1);
        Mat mat = new Mat();

        mat = Imgcodecs.imread("./src/main/resources/cibleAvecImpacts.jpg");
        byte[] imageData;

        ImageIcon icon;

//        while (true) {
//            videoCapture.read(mat);
        mat = Camera.extractDocument(mat);
        RotatedRect ellipseTopLeft = Camera.getOuterCircle(Camera.getTopLeftTarget(mat));




        RotatedRect ellipseTopRight = Camera.getOuterCircle(Camera.getTopRightTarget(mat));
        ellipseTopRight.center.x = ellipseTopRight.center.x + mat.size().width / 2;



        RotatedRect ellipseCenter = Camera.getOuterCircle(Camera.getCenterTarget(mat));
        ellipseCenter.center.x = ellipseCenter.center.x + mat.size().width / 4;
        ellipseCenter.center.y = ellipseCenter.center.y + mat.size().height / 4;
        RotatedRect ellipseBottomLeft = Camera.getOuterCircle(Camera.getBottomLeftTarget(mat));
        ellipseBottomLeft.center.y = ellipseBottomLeft.center.y + mat.size().height / 2;

        RotatedRect ellipseBottomRight = Camera.getOuterCircle(Camera.getBottomRightTarget(mat));
        ellipseBottomRight.center.x = ellipseBottomRight.center.x + mat.size().width / 2;
        ellipseBottomRight.center.y = ellipseBottomRight.center.y + mat.size().height / 2;
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
        int result_cols = mat.cols() - template.cols() + 1;
        int result_rows = mat.rows() - template.rows() + 1;
        if (result_cols <= 0 || result_rows <= 0) {
            return;
        }

        Mat dst = mat.clone();


        Imgcodecs.imencode(".jpg", dst, buf);
        imageData = buf.toArray();
        icon = new ImageIcon(imageData);
        cameraScreen.setIcon(icon);


//        }

    }






    public WebCam() {
        super("Camera");

        template = Imgcodecs.imread("./src/main/resources/target_pattern.jpg");
        Imgproc.resize(template, template, new Size(200, 200));
        Imgproc.cvtColor(template, template, Imgproc.COLOR_BGR2GRAY);

        cameraScreen.setBounds(0, 0, 1920, 1080);
        add(cameraScreen);


        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
