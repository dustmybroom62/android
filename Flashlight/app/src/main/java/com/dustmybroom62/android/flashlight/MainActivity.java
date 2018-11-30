package com.dustmybroom62.android.flashlight;

import android.Manifest;
import android.app.Activity;
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
import android.support.design.widget.Snackbar;

public class MainActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_REQUEST_CAMERA = 1017;
    TextView tv1;
    Switch swPower;
    Switch swSos;
    Camera camera = null;
    View coordView;
    boolean hasCameraRights = false;
    private StrobeRunner strobeRunner;
    private Thread srThread;
    private double mcDot = 125;
    private double mcDash = 250;
    double[] sosPattern = {mcDot, mcDot, mcDot, mcDash, mcDash, mcDash, mcDot, mcDot, mcDot};
    double sosGap = 200;
    private boolean flashOn = false;
    private boolean sosOn = false;
    private boolean appStartup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appStartup = true;
        coordView = (View) findViewById(R.id.coordLayout1);
        tv1 = (TextView) findViewById(R.id.textView1);
        swPower = (Switch) findViewById(R.id.switch1);
        swPower.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean on) {
            if (!hasCameraRights) {
                buttonView.setChecked(false);
                tv1.setText("Disabled");
                showMessage("Not Allowed: Flashlight requires Camera privelege.");
                return;
            }
            if (on) {
                flashOn = true;
                swSos.setChecked(false);
                tv1.setText("On");
                flashlightOn();
            } else {
                flashOn = false;
                if (!sosOn) {
                    tv1.setText("Off");
                    flashlightOff();
                }
            }
            }
        });

        swSos = (Switch) findViewById(R.id.switchSos);
        swSos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (!hasCameraRights) {
                buttonView.setChecked(false);
                showMessage("Not Allowed: Flashlight requires Camera privelege.");
                return;
            }
            if (isChecked) {
                sosOn = true;
                swPower.setChecked(false);
                tv1.setText("SOS Mode");
                sosOn();
            } else {
                sosOn = false;
                if (!flashOn) {
                    tv1.setText("Off");
                    flashlightOff();
                }
            }
            }
        });
        strobeRunner = StrobeRunner.getInstance();
    }

    private void showMessage(String message) {
        Snackbar.make(coordView, message, Snackbar.LENGTH_SHORT)
           .show();
    }

    protected void sosOn() {
        try {
            strobeRunner.delayOff = sosGap;
            strobeRunner.onPattern = sosPattern.clone();
            if (!strobeRunner.isRunning) {
                srThread = new Thread(strobeRunner);
                srThread.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(coordView, e.getMessage(), Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    protected void flashlightOn() {
        try {
            strobeRunner.delayOff = 0;
            strobeRunner.onPattern = new double[]{100};
            if (!strobeRunner.isRunning) {
                srThread = new Thread(strobeRunner);
                srThread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(coordView, e.getMessage(), Snackbar.LENGTH_SHORT)
                .show();
        }
    }

    protected void flashlightOff() {
        try {
            strobeRunner.requestStop = true;
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(coordView, e.getMessage(), Snackbar.LENGTH_SHORT)
                .show();
        }
    }

    private void requestCameraPermission() {
        final Activity activity = this;
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
            Snackbar.make(coordView, "Camera access is required for Flashlight.",
                Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
                }
            }).show();
        } else {
            Snackbar.make(coordView, "Requesting camera permission.",
                Snackbar.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSION_REQUEST_CAMERA);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != MY_PERMISSION_REQUEST_CAMERA) {
            return;
        }

        if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            hasCameraRights = true;
            Snackbar.make(coordView, "Camera permission was granted.", Snackbar.LENGTH_SHORT).show();
            swPower.setEnabled(true);
            tv1.setText(swPower.isChecked() ? "On" : "Off");
        } else {
            hasCameraRights = false;
            tv1.setText("Disabled");
            swPower.setEnabled(false);
            swSos.setEnabled(false);
            Snackbar.make(coordView, "Camera permission request was denied.", Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            hasCameraRights = true;
        } else {
            requestCameraPermission();
        }
        if (appStartup) {
            appStartup = false;
            swPower.setChecked(true);
        }
    }

    @Override
    protected void onDestroy() {
        strobeRunner.requestStop = true;
        swSos.setChecked(false);
        swPower.setChecked(false);

        super.onDestroy();
    }

}
