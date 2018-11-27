package com.dustmybroom62.android.flashlight;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 1;
    TextView tv1;
    Switch power;
    Camera camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv1 = (TextView) findViewById(R.id.textView1);
        power = (Switch) findViewById(R.id.switch1);
        power.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean on) {
                if (on) {
                    tv1.setText("On");
                    flashlightOn();
                } else {
                    tv1.setText("Off");
                    flashlightOff();
                }
            }
        });

//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
//        }
    }

    protected void flashlightOn() {
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void flashlightOff() {
        try {
            camera = Camera.open();
            Camera.Parameters parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            camera.release();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    private void requestCameraPermission() {
//
//
//        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
//            Snackbar.make(findViewById(android.R.id.content), "Camera access is required for Flashlight.",
//                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
//                @Override public void onClick(View view) {
//                    requestPermissions( new String[] {Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
//                }
//            }).show();
//        } else{
//            Snackbar.make(getActivity().findViewById(android.R.id.content), "Requesting camera permission.",
//                    Snackbar.LENGTH_SHORT).show();
//            requestPermissions(new String[] {Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
//        }
//
//
//    }
//
//    @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
//                                                     @NonNull int[] grantResults) {
//        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
//            return;
//        }
//
//        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//            Snackbar.make(getActivity().findViewById(android.R.id.content), "Camera permission was granted. Now you can scan QR code", Snackbar.LENGTH_SHORT).show();
//            initQRCodeReaderView();
//        } else {
//            Snackbar.make(getActivity().findViewById(android.R.id.content), "Camera permission request was denied, Can't able to start QR scan", Snackbar.LENGTH_SHORT)
//                    .show();
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Switch p = findViewById(R.id.switch1);
//        onPower(p);
//    }

//    protected void onPower(View v) {
//        Switch p = (Switch) v;
//        //Toast t = new Toast(this );
//        if ( p.isChecked() ) {
//            //t.setText("Power On");
//            tv1.setText("On");
//        } else {
//            //t.setText("Power Off");
//            tv1.setText("Off");
//        }
//        //t.show();
//    }
}
