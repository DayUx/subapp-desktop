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

        VideoCapture videoCapture = new VideoCapture(0);
        Mat mat = new Mat();
        byte[] imageData;

        ImageIcon icon;

        while (true) {
            videoCapture.read(mat);
            mat = Camera.extractDocument(mat);



            final MatOfByte buf = new MatOfByte();


            int result_cols = mat.cols() - template.cols() + 1;
            int result_rows = mat.rows() - template.rows() + 1;
            if (result_cols <= 0 || result_rows <= 0) {
                return;
            }




            double threshold = 0.95;
            double maxVal;
            Mat dst = mat.clone();
            Mat matCopy = mat.clone();

//            Imgproc.cvtColor(matCopy, matCopy, Imgproc.COLOR_BGR2GRAY);

//            for ( int i = 0; i < 5; i++)
//            {
//                Mat result = new Mat(result_rows, result_cols, CvType.CV_32FC1);
//                Imgproc.matchTemplate(matCopy, template, result, Imgproc.TM_CCOEFF);
//                Core.normalize(result, result, 0, 1, Core.NORM_MINMAX, -1, new Mat());
//                Core.MinMaxLocResult maxr = Core.minMaxLoc(result);
//                Point maxp = maxr.maxLoc;
//                maxVal = maxr.maxVal;
//                Point maxop = new Point(maxp.x + template.width(), maxp.y + template.height());
//                dst = mat.clone();
//                if(maxVal >= threshold)
//                {
//                    Imgproc.rectangle(mat, maxp, new Point(maxp.x + template.cols(),
//                            maxp.y + template.rows()), new Scalar(0, 255, 0),1);
//                    Imgproc.rectangle(matCopy, maxp, new Point(maxp.x + template.cols(),
//                            maxp.y + template.rows()), new Scalar(255,255,255),-1);
//                }else{
//                    break;
//                }
//            }







            List<MatOfPoint> contours = new ArrayList<>();
//            Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//                        for (MatOfPoint contour : contours) {
//                Rect rect = Imgproc.boundingRect(contour);
//                Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
//            }

//            Imgproc.rectangle(mat, matchLoc, new Point(matchLoc.x + template.cols(), matchLoc.y + template.rows()), new Scalar(0, 255, 0), 2);



//            double threshold = 0.8;
//            Mat mask = new Mat();
//            Core.inRange(result, new Scalar(threshold), new Scalar(1.0), mask);
//            List<MatOfPoint> contours = new ArrayList<>();
//            Imgproc.findContours(mask, contours, new Mat(), Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);
//            for (MatOfPoint contour : contours) {
//                Rect rect = Imgproc.boundingRect(contour);
//                Imgproc.rectangle(mat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 255, 0), 2);
//            }



//            mat = Camera.getOuterCircle(mat);

            Imgcodecs.imencode(".jpg", dst, buf);
            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            cameraScreen.setIcon(icon);



        }

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
