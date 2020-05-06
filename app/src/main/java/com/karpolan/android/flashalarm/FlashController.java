package com.karpolan.android.flashalarm;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

/**
 * Allows to control the Flash light of the main camera
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class FlashController {

    public Boolean flashExists; // Todo: make readonly private with getter
    private CameraManager cameraManager;
    private String cameraId;

    public FlashController(Context context) {
        flashExists = context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!flashExists) {
            return; // There is no Flash Light on this device :(
        }

        cameraManager = (CameraManager) context.getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0]; // Todo: maybe there is a flash light on other cameras
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    public void turnOn() {
        if (!flashExists) {
            return; // There is no Flash Light on this device :(
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void turnOff() {
        if (!flashExists) {
            return; // There is no Flash Light on this device :(
        }

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                cameraManager.setTorchMode(cameraId, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
