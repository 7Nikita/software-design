package com.nikita.calculator.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.nikita.calculator.ControlsAdapter;
import com.nikita.calculator.R;
import com.nikita.calculator.fragment.BasicControlsFragment;
import com.nikita.calculator.fragment.ScientificControlsFragment;

import com.google.common.collect.ImmutableList;

import java.util.List;


public class CalculatorActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final List<Fragment> fragmentList = ImmutableList.of(
                new BasicControlsFragment(),
                new ScientificControlsFragment()
        );

        final ViewPager viewPager = findViewById(R.id.frame);
        viewPager.setAdapter(new ControlsAdapter(getSupportFragmentManager(), fragmentList));
        //viewPager.setCurrentItem(0);
    }
}

