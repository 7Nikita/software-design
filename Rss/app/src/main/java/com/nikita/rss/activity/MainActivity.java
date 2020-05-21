package com.nikita.rss.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.nikita.rss.R;
import com.nikita.rss.fragment.RssListviewFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.findFragmentByTag("RssListviewFragment") == null) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();

            RssListviewFragment fragment = new RssListviewFragment();
            transaction.replace(R.id.frameLayout, fragment, "RssListviewFragment");
            transaction.commit();
        }

        setContentView(R.layout.activity_main);
    }
}
