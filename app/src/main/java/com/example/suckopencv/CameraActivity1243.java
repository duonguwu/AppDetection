package com.example.suckopencv;

import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraActivity;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CameraActivity1243 extends CameraActivity {
    private Mat mRgba;
    private Mat mGray;
    private static final String TAG="MainActivity";

    private CameraBridgeViewBase mOpenCvCameraView;
    private com.example.suckopencv.objectDetectorClass objectDetectorClass;
    private BaseLoaderCallback mLoaderCallback =new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status){
                case LoaderCallbackInterface
                        .SUCCESS:{
                    Log.i(TAG,"OpenCv Is loaded");
                    mOpenCvCameraView.enableView();
                }
                default:
                {
                    super.onManagerConnected(status);

                }
                break;
            }
        }
    };

    @Override
    protected List<?extends CameraBridgeViewBase> getCameraViewList(){
        return Collections.singletonList(mOpenCvCameraView);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Giữ cho màn hình không tắt khi app chạy
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_camera1243);


        // Lấy cái view từ layout để gán cho biến mOpenCvCameraView
        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.camera_view);
        // Này dùng để hiển thị mOpenCvCameraView lên app
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        // Này để lắng nghe các sự kiện liên quan đến camera và xử lý ảnh. Sau này sẽ dùng
        mOpenCvCameraView.setCvCameraViewListener(cvCameraViewListener);

        try {
            // input size is 300 for this model
            objectDetectorClass = new objectDetectorClass(getAssets(), "ssd_mobilenet.tflite", "labelmap.txt", 300);
            Log.d("MainActivity", "Model is successfully loaded!");
        } catch (IOException e){
            Log.d("MainActivity", "Model is loi tum lum");
            e.printStackTrace();
        }
    }

    private CameraBridgeViewBase.CvCameraViewListener2 cvCameraViewListener = new CameraBridgeViewBase.CvCameraViewListener2() {
        @Override
        public void onCameraViewStarted(int width, int height) {

        }

        @Override
        public void onCameraViewStopped() {

        }

        @Override
        public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
            mRgba=inputFrame.rgba();
            mGray=inputFrame.gray();

            Mat out=new Mat();
            out=objectDetectorClass.recognizeImage(mRgba);

            return out;
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        if (OpenCVLoader.initDebug()){
            //if load success
            Log.d(TAG,"Opencv initialization is done");
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
        else{
            //if not loaded
            Log.d(TAG,"Opencv is not loaded. try again");
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_4_0,this,mLoaderCallback);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }
    }

    public void onDestroy(){
        super.onDestroy();
        if(mOpenCvCameraView !=null){
            mOpenCvCameraView.disableView();
        }

    }
}