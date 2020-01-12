package com.zachrawi.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import java.security.Policy;

public class MainActivity extends AppCompatActivity {
    private String cameraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasFlash = getApplicationContext()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (hasFlash) {
            Toast.makeText(this, "Punya flash", Toast.LENGTH_SHORT).show();

            Switch switchFlash = findViewById(R.id.switchFlash);

            final CameraManager camManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

            try {
                cameraId = camManager.getCameraIdList()[0];
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }

            switchFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        try {
                            camManager.setTorchMode(cameraId, true); //Turn ON
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            camManager.setTorchMode(cameraId, false); //Turn OFF
                        } catch (CameraAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        } else {
            Toast.makeText(this, "Tidak punya flash", Toast.LENGTH_SHORT).show();
        }


    }
}
