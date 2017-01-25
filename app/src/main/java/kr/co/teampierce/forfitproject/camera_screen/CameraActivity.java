package kr.co.teampierce.forfitproject.camera_screen;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

import kr.co.teampierce.forfitproject.R;

public class CameraActivity extends Activity {

    private CameraPreview cameraPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        init();
    }

    private void init(){

        checkPermissions();

        cameraPreview = new CameraPreview(getApplicationContext());
        FrameLayout previewLayout = (FrameLayout)findViewById(R.id.camera_preview);
        previewLayout.addView(cameraPreview);

        LinearLayout cameraCover = (LinearLayout)findViewById(R.id.camera_cover);
        cameraCover.bringToFront();
    }

    private void checkPermissions(){

        String[] permissions = {
                //Manifest.permission.READ_EXTERNAL_STORAGE,
                //Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA
        };

        int permissionCheck;

        for(String permissionRequired : permissions){
            permissionCheck = ContextCompat.checkSelfPermission(this, permissionRequired);
            if (permissionCheck == PackageManager.PERMISSION_DENIED){
                requestPermissionRuntime(permissionRequired);
            }
        }
    }

    public void requestPermissionRuntime(String permissionRequired){

        if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissionRequired)){
            //TODO:Explain the reason why we need this permission
        }
        else{
            ActivityCompat.requestPermissions(this, new String[]{permissionRequired}, 1);
        }
    }

    public void onShootButton(View v) {

        LinearLayout cameraCover = (LinearLayout)findViewById(R.id.camera_cover);
        cameraCover.setVisibility(View.GONE);

        final Camera.PictureCallback pictureCallback = new Camera.PictureCallback() {
            @Override
            public void onPictureTaken(byte[] byteStream, Camera camera) {

                FileOutputStream fileOutputStream = null;

                try{
                    fileOutputStream= getApplicationContext().openFileOutput("001", Context.MODE_PRIVATE);
                    //TODO: make it have distinct file name for each time the camera shoots
                    fileOutputStream.write(byteStream);
                }
                catch(Exception exception){
                    Log.d("Camera_shoot", "Failed to write byte stream", exception);
                }
                finally {
                    try {
                        fileOutputStream.close();
                    }
                    catch(Exception exception){
                        Log.d("Camera_shoot", "Failed to close file output stream", exception);
                    }
                }
            }
        };

        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cameraPreview.shoot(pictureCallback);
            }
        });

        ImageView baseline = (ImageView)findViewById(R.id.camera_baseline);
        baseline.setVisibility(View.VISIBLE);
        baseline.bringToFront();
    }
}

