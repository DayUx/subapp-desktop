package fr.subapp.subappdesktop.opencv;

import nu.pattern.OpenCV;
import org.opencv.core.*;
import org.opencv.core.Point;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
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

        mat = Imgcodecs.imread("./src/main/resources/cible.jpg");
        byte[] imageData;

        ImageIcon icon;

//        while (true) {
//            videoCapture.read(mat);
            mat = Camera.extractDocument(mat);
            Imshow.show(Camera.getOuterCircle(Camera.getTopLeftTarget(mat)), "Top Left");
            Imshow.show(Camera.getOuterCircle(Camera.getTopRightTarget(mat)), "Top Right");
            Imshow.show(Camera.getOuterCircle(Camera.getCenterTarget(mat)), "Center");
            Imshow.show(Camera.getOuterCircle(Camera.getBottomLeftTarget(mat)), "Bottom Left");
            Imshow.show(Camera.getOuterCircle(Camera.getBottomRightTarget(mat)), "Bottom Right");





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
