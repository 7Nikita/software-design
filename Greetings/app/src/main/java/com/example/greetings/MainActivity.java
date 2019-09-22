package com.example.greetings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context context = getApplicationContext();
        textView = findViewById(R.id.application_verison);

        try {
            PackageInfo pInfo = context.getPackageManager().getPackageInfo(getPackageName(), 0);
            String versionName = pInfo.versionName;
            textView.setText(getString(R.string.version_name, versionName));
        } catch (PackageManager.NameNotFoundException e) {
            textView.setText(R.string.version_error);
            e.printStackTrace();
        }

    }
}
