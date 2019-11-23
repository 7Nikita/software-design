package com.example.greetings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 1;

    private Button deviceIdButton;
    private TextView applicationVersionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String applicationVersion = getApplicationVersion();

        deviceIdButton = findViewById(R.id.deviceId);
        applicationVersionView = findViewById(R.id.applicationVerison);

        applicationVersionView.setText(getString(R.string.version_name, applicationVersion));

        deviceIdButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (isReadPhoneStatePermissionGranted()) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.device_id, getDeviceId()), Toast.LENGTH_LONG).show();
                } else {
                    if (shouldAskReadPhoneStatePermission()) {
                        explainReadPhoneStatePermission();
                    } else {
                        askReadPhoneStatePermission();
                    }
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_PHONE_STATE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getApplicationContext(),
                            getString(R.string.device_id, getDeviceId()), Toast.LENGTH_LONG).show();
                }
            }
            return;
        }
    }

    private String getApplicationVersion() {
        return BuildConfig.VERSION_NAME;
    }

    private String getDeviceId() {
        return Settings.Secure.getString(MainActivity.this.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    private boolean isReadPhoneStatePermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                == PackageManager.PERMISSION_GRANTED;
    }

    private boolean shouldAskReadPhoneStatePermission() {
        return ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.READ_PHONE_STATE);
    }

    private void askReadPhoneStatePermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE},
                MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
    }

    private void explainReadPhoneStatePermission() {
        new AlertDialog.Builder(this)
                .setMessage(R.string.rps_permission_request)
                .setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        askReadPhoneStatePermission();
                    }
                })
                .setNegativeButton(R.string.cancel_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

}
