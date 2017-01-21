package kr.co.teampierce.forfitproject.camera_screen;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

import static java.lang.System.exit;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{

    private SurfaceHolder surfaceHolder;
    private Camera camera = null;

    public CameraPreview(Context context) {

        super(context);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }

    public void surfaceCreated(SurfaceHolder holder) {

        camera = Camera.open();

        camera.setDisplayOrientation(90);

        try {
            camera.setPreviewDisplay(surfaceHolder);
        } catch (IOException ioException) {
            Log.d("CameraPreview", "Failed to set preview display", ioException);
            exit(-1);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        camera.startPreview();
    }

    public void surfaceDestroyed(SurfaceHolder holder) {

        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public boolean shoot(Camera.PictureCallback pictureCallback) {

        if (camera != null) {
            camera.takePicture(null, null, pictureCallback);
            return true;
        } else {
            return false;
        }
    }
}
