package com.example.suckopencv;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.opencv.android.OpenCVLoader;


public class MainActivity extends AppCompatActivity {
    static {
        if(OpenCVLoader.initDebug()){
            Log.d("MainActivity: ","Opencv is loaded");
        }
        else {
            Log.d("MainActivity: ","Opencv failed to load");
        }
    }

    private Button camera_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        camera_button=findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,CameraActivity1243.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

    }
}
//public class MainActivity extends CameraActivity {
//    private static String LOGTAG = "OpenCV_Log";
//
//    private CameraBridgeViewBase mOpenCvCameraView;
//    //Đây là đối tượng được sử dụng để kết nối và điều khiển camera trên thiết bị.
//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status){
//                case LoaderCallbackInterface.SUCCESS:{
//                    Log.v(LOGTAG, "OpenCV Loaded");
//                    mOpenCvCameraView.enableView();
//                } break;
//                default:
//                {
//                    super.onManagerConnected(status);
//                } break;
//            }
//        }
//    };
//
//
//
//    @Override
//    protected List<?extends CameraBridgeViewBase> getCameraViewList(){
//        return Collections.singletonList(mOpenCvCameraView);
//    }
//
//    private CameraBridgeViewBase.CvCameraViewListener2 cvCameraViewListener = new CameraBridgeViewBase.CvCameraViewListener2() {
//        @Override
//        public void onCameraViewStarted(int width, int height) {
//
//        }
//
//        @Override
//        public void onCameraViewStopped() {
//
//        }
//
//        @Override
//        public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//            Mat input_rgba = inputFrame.rgba();
//            Mat input_gray = inputFrame.gray();
//
//            MatOfPoint corners = new MatOfPoint();
//            Imgproc.goodFeaturesToTrack(input_gray,corners, 20, 0.01,10,new Mat(), 3,false);
//            Point[] cornersArr = corners.toArray();
//
//            for(int i = 0; i < corners.rows(); i++){
//                Imgproc.circle(input_rgba, cornersArr[i], 10, new Scalar(0,255,0), 2);
//            }
//
//            return input_rgba;
//        }
//    };
//
//    @Override
//    public void onPause(){
//        super.onPause();
//        if(mOpenCvCameraView != null){
//            mOpenCvCameraView.disableView();
//        }
//    }
//
//    @Override
//    public void onResume(){
//        super.onResume();
//        if(!OpenCVLoader.initDebug()){
//            Log.d(LOGTAG, "OpenCV not found, Initializing");
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION, this, mLoaderCallback);
//        } else {
//            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
//    }
//
//    @Override
//    public void onDestroy(){
//        super.onDestroy();
//        if(mOpenCvCameraView != null){
//            mOpenCvCameraView.disableView();
//        }
//    }
//
//
//
//}