package kr.co.teampierce.forfitproject.camera_screen;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;

import kr.co.teampierce.forfitproject.R;

public class CameraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_camera);

        init();
    }

    private void init(){

        checkPermissions();

        final CameraPreview cameraPreview = new CameraPreview(getApplicationContext());
        FrameLayout previewLayout = (FrameLayout)findViewById(R.id.camera_preview);
        previewLayout.addView(cameraPreview);
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
}

