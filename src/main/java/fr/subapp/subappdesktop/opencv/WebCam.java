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
        EventQueue.invokeLater(() -> {
            WebCam camera = new WebCam();
            camera.startCamera();
        });

    }


    public void startCamera() {


    }






    public WebCam() {
        super("Camera");
        cameraScreen.setBounds(0, 0, 1920, 1080);
        add(cameraScreen);


        setSize(640, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
