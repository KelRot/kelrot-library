package frc.kelrotlib.vision;

import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation2d;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.PhotonPoseEstimator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.cscore.HttpCamera;

import edu.wpi.first.math.geometry.Transform3d;

import edu.wpi.first.apriltag.AprilTagFieldLayout;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PhotonVisionHelpers {

    ObjectMapper mapper = new ObjectMapper();


    public static class PhotonVisionTarget_Fiducial {

        @JsonProperty("fID")
        public double fiducialID;

        @JsonProperty("fam")
        public String fiducialFamily;

        @JsonProperty("t6c_ts")
        private double[] cameraPose_TargetSpace;

        @JsonProperty("t6r_fs")
        private double[] robotPose_FieldSpace;

        @JsonProperty("t6r_ts")
        private double[] robotPose_TargetSpace;

        @JsonProperty("t6t_cs")
        private double[] targetPose_CameraSpace;

        @JsonProperty("t6t_rs")
        private double[] targetPose_RobotSpace;

        public Pose3d getCameraPose_TargetSpace()
        {
            return toPose3D(cameraPose_TargetSpace);
        }
        public Pose3d getRobotPose_FieldSpace()
        {
            return toPose3D(robotPose_FieldSpace);
        }
        public Pose3d getRobotPose_TargetSpace()
        {
            return toPose3D(robotPose_TargetSpace);
        }
        public Pose3d getTargetPose_CameraSpace()
        {
            return toPose3D(targetPose_CameraSpace);
        }
        public Pose3d getTargetPose_RobotSpace()
        {
            return toPose3D(targetPose_RobotSpace);
        }

        public Pose2d getCameraPose_TargetSpace2D()
        {
            return toPose2D(cameraPose_TargetSpace);
        }
        public Pose2d getRobotPose_FieldSpace2D()
        {
            return toPose2D(robotPose_FieldSpace);
        }
        public Pose2d getRobotPose_TargetSpace2D()
        {
            return toPose2D(robotPose_TargetSpace);
        }
        public Pose2d getTargetPose_CameraSpace2D()
        {
            return toPose2D(targetPose_CameraSpace);
        }
        public Pose2d getTargetPose_RobotSpace2D()
        {
            return toPose2D(targetPose_RobotSpace);
        }
        
        @JsonProperty("ta")
        public double ta;

        @JsonProperty("tx")
        public double tx;

        @JsonProperty("txp")
        public double tx_pixels;

        @JsonProperty("ty")
        public double ty;

        @JsonProperty("typ")
        public double ty_pixels;

        @JsonProperty("ts")
        public double ts;
        
        public PhotonVisionTarget_Fiducial() {
            cameraPose_TargetSpace = new double[6];
            robotPose_FieldSpace = new double[6];
            robotPose_TargetSpace = new double[6];
            targetPose_CameraSpace = new double[6];
            targetPose_RobotSpace = new double[6];
        }
    }

    public static class PhotonVisionTarget_Classifier {

        @JsonProperty("class")
        public String className;

        @JsonProperty("conf")
        public double confidence;

        @JsonProperty("zone")
        public double zone;

        @JsonProperty("tx")
        public double tx;

        @JsonProperty("txp")
        public double tx_pixels;

        @JsonProperty("ty")
        public double ty;

        @JsonProperty("typ")
        public double ty_pixels;

        public  PhotonVisionTarget_Classifier() {
        }
    }

    public static class PhotonVisionTarget_Detector {

        @JsonProperty("class")
        public String className;

        @JsonProperty("classID")
        public double classID;

        @JsonProperty("conf")
        public double confidence;

        @JsonProperty("ta")
        public double ta;

        @JsonProperty("tx")
        public double tx;

        @JsonProperty("txp")
        public double tx_pixels;

        @JsonProperty("ty")
        public double ty;

        @JsonProperty("typ")
        public double ty_pixels;

        public PhotonVisionTarget_Detector() {
        }
    }

    public static class PhotonVisionResults {

        public String cameraName;
        
        public String error;
        
        @JsonProperty("pID")
        public double pipelineID;

        @JsonProperty("tl")
        public double latency_pipeline;

        @JsonProperty("cl")
        public double latency_capture;

        @JsonProperty("ts")
        public double timestamp_PHOTONVISION_publish;

        @JsonProperty("ts_rio")
        public double timestamp_RIOFPGA_capture;

        @JsonProperty("v")
        @JsonFormat(shape = Shape.NUMBER)
        public boolean valid;

        @JsonProperty("botpose")
        public double[] botpose;

        @JsonProperty("botpose_wpired")
        public double[] botpose_wpired;

        @JsonProperty("botpose_wpiblue")
        public double[] botpose_wpiblue;

        @JsonProperty("botpose_tagcount")
        public double botpose_tagcount;
       
        @JsonProperty("botpose_span")
        public double botpose_span;
       
        @JsonProperty("botpose_avgdist")
        public double botpose_avgdist;
       
        @JsonProperty("botpose_avgarea")
        public double botpose_avgarea;

        @JsonProperty("t6c_rs")
        public double[] camerapose_robotspace;

        public Pose3d getBotPose3d() {
            return toPose3D(botpose);
        }
    
        public Pose3d getBotPose3d_wpiRed() {
            return toPose3D(botpose_wpired);
        }
    
        public Pose3d getBotPose3d_wpiBlue() {
            return toPose3D(botpose_wpiblue);
        }

        public Pose2d getBotPose2d() {
            return toPose2D(botpose);
        }
    
        public Pose2d getBotPose2d_wpiRed() {
            return toPose2D(botpose_wpired);
        }
    
        public Pose2d getBotPose2d_wpiBlue() {
            return toPose2D(botpose_wpiblue);
        }

        @JsonProperty("Fiducial")
        public PhotonVisionTarget_Fiducial[] targets_Fiducials;

        @JsonProperty("Classifier")
        public PhotonVisionTarget_Classifier[] targets_Classifier;

        @JsonProperty("Detector")
        public PhotonVisionTarget_Detector[] targets_Detector;

        public PhotonVisionResults() {
            botpose = new double[6];
            botpose_wpired = new double[6];
            botpose_wpiblue = new double[6];
            camerapose_robotspace = new double[6];
            targets_Fiducials = new PhotonVisionTarget_Fiducial[0];
            targets_Classifier = new PhotonVisionTarget_Classifier[0];
            targets_Detector = new PhotonVisionTarget_Detector[0];
        }


    }

    public static PhotonVisionResults buildResultsFromCamera(PhotonCamera camera, Transform3d robotToCamera) {

    PhotonPipelineResult pipelineResult = camera.getLatestResult();
    if (pipelineResult == null) {
        return null;
    }

    PhotonVisionResults results = new PhotonVisionResults();
    results.cameraName = camera.getName(); // hangi kameradan geldiğini kaydet
    results.pipelineID = camera.getPipelineIndex();
    results.latency_pipeline = pipelineResult.getTimestampSeconds();
    results.timestamp_PHOTONVISION_publish = pipelineResult.getTimestampSeconds();
    results.valid = pipelineResult.hasTargets();

    // --- Fiducial (AprilTag) hedeflerini doldur ---
    var targets = pipelineResult.getTargets(); // List<PhotonTrackedTarget>
    results.targets_Fiducials = targets.stream()
        .filter(t -> t.getFiducialId() != -1) // fiducial olanlar
        .map(t -> {
            PhotonVisionTarget_Fiducial f = new PhotonVisionTarget_Fiducial();
            f.fiducialID = t.getFiducialId();
            f.tx = t.getYaw();
            f.ty = t.getPitch();
            f.ta = t.getArea();
            f.ts = pipelineResult.getTimestampSeconds();
            // Pose bilgisi varsa camera->target transform'ını koy
            try {
                Transform3d camToTarget = t.getBestCameraToTarget(); // javadoc mevcuttur
                if (camToTarget != null) {
                    // convert Transform3d -> Pose3d array biçimine (x,y,z,rotX,rotY,rotZ)
                    Pose3d p = new Pose3d(camToTarget.getTranslation(), camToTarget.getRotation());
                    f.cameraPose_TargetSpace = toArray(p); // (private field, ama buradan erişilebilir istersen setter yap)
                }
            } catch (Exception ex) {
                // güvenli: transform yoksa atla
            }
            return f;
        }).toArray(PhotonVisionTarget_Fiducial[]::new);

    // Classifier / detector: mevcut lib'de getClassId() yoksa atla.
    results.targets_Classifier = new PhotonVisionTarget_Classifier[0];
    results.targets_Detector = new PhotonVisionTarget_Detector[0];

    return results;
}


    public static PhotonPoseEstimator createPoseEstimator(AprilTagFieldLayout layout, Transform3d robotToCam, PhotonPoseEstimator.PoseStrategy strategy) {
        return new PhotonPoseEstimator(layout, strategy, robotToCam);
        }



    public static Pose3d toPose3D(double[] inData){
        if(inData.length < 6)
        {
            //System.err.println("Bad LL 3D Pose Data!");
            return new Pose3d();
        }
        return new Pose3d(
            new Translation3d(inData[0], inData[1], inData[2]),
            new Rotation3d(Units.degreesToRadians(inData[3]), Units.degreesToRadians(inData[4]),
                    Units.degreesToRadians(inData[5])));
    }

    public static Pose2d toPose2D(double[] inData){
        if(inData.length < 6)
        {
            //System.err.println("Bad LL 2D Pose Data!");
            return new Pose2d();
        }
        Translation2d tran2d = new Translation2d(inData[0], inData[1]);
        Rotation2d r2d = new Rotation2d(Units.degreesToRadians(inData[5]));
        return new Pose2d(tran2d, r2d);
    }

    public static double[] toArray(Pose3d pose) {
        return new double[]{
            pose.getX(),
            pose.getY(),
            pose.getZ(),
            pose.getRotation().getX(),
            pose.getRotation().getY(),
            pose.getRotation().getZ()
        };
    }

    public static void createCamera(String name, double[] transformArray){
        PhotonCamera camera = new PhotonCamera(name);

        HttpCamera cameraStream = new HttpCamera(name, "http://photonvision.local:1181/stream.mjpg");

        Shuffleboard.getTab("Vision").add(cameraStream);

        boolean[] choices = choiceMenu();

        Translation3d camTrans = new Translation3d(transformArray[0], transformArray[1],transformArray[2]);
        Rotation3d camRot = new Rotation3d(transformArray[3], transformArray[4], transformArray[5]);
        Transform3d robotToCamera = new Transform3d(camTrans, camRot);

        PhotonVisionResults results = buildResultsFromCamera(camera, robotToCamera);

        publishSelected(results, choices, name);

    }    


    public static boolean[] choiceMenu(){

        boolean[] choices = new boolean[]{false, false, false, false, false, false};

        SwingUtilities.invokeLater(() -> {

        JFrame frame = new JFrame("PhotonVision Camera Data Selection for SmartDashboard");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton btn1 = new JButton("AprilTag ID");
        JButton btn3= new JButton("Pipeline Latency");
        JButton btn4 = new JButton("Target Yaw");
        JButton btn5 = new JButton("Target Pitch");
        JButton btn6 = new JButton("Target Area");
        JButton btn7 = new JButton("Pose Ambiguity");
        JButton btn8 = new JButton("Send");

        panel.add(btn1);
        panel.add(btn7);
        panel.add(btn3);
        panel.add(btn4);
        panel.add(btn5);
        panel.add(btn6);
        panel.add(btn8);

        frame.add(panel);
        frame.setVisible(true);

        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choices[0] = true;
            }
        });

        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choices[2] = true;
            }
        });

        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choices[3] = true;
            }
        });

        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choices[4] = true;
            }
        });

        btn6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choices[5] = true;
            }
        });

        btn7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choices[6] = true;
            }
        });

        btn8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });

        while (frame.isDisplayable()) {
            try { Thread.sleep(50); } catch (InterruptedException e) {}
        }

        });

        return choices;
    }

    public static void publishSelected(PhotonVisionResults results, boolean[] choices, String cameraName) {
        if (results == null) return;
    
        if (choices.length > 0 && Boolean.TRUE.equals(choices[0]) && results.targets_Fiducials.length > 0) {
            SmartDashboard.putNumber(cameraName + " AprilTag ID", results.targets_Fiducials[0].fiducialID);
        }
        if (choices.length > 1 && Boolean.TRUE.equals(choices[1]) && results.targets_Fiducials.length > 0) {
            SmartDashboard.putNumber(cameraName + " Target Area", results.targets_Fiducials[0].ta);
        }
        if (choices.length > 2 && Boolean.TRUE.equals(choices[2])) {
            SmartDashboard.putNumber(cameraName + " Pipeline Latency", results.latency_pipeline);
        }
        if (choices.length > 3 && Boolean.TRUE.equals(choices[3]) && results.targets_Fiducials.length > 0) {
            SmartDashboard.putNumber(cameraName + " Target Yaw", results.targets_Fiducials[0].tx);
        }
        if (choices.length > 4 && Boolean.TRUE.equals(choices[4]) && results.targets_Fiducials.length > 0) {
            SmartDashboard.putNumber(cameraName + " Target Pitch", results.targets_Fiducials[0].ty);
        }
        if (choices.length > 5 && Boolean.TRUE.equals(choices[5]) && results.targets_Fiducials.length > 0) {
            SmartDashboard.putNumber(cameraName + " Target Ambiguity", results.targets_Fiducials[0].ts);
        }
        
    }

}