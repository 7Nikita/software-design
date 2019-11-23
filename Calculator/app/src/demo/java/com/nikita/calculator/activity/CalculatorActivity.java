package com.nikita.calculator.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.nikita.calculator.ControlsAdapter;
import com.nikita.calculator.R;
import com.nikita.calculator.fragment.BasicControlsFragment;

import com.google.common.collect.ImmutableList;
import com.nikita.calculator.service.CalculationsService;

import java.util.List;


public class CalculatorActivity extends AppCompatActivity {

    private EditText mExpression;
    private final CalculationsService mService = CalculationsService.getInstance();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mExpression = findViewById(R.id.expressionText);

        final BasicControlsFragment basicControlsFragment = new BasicControlsFragment();
        basicControlsFragment.setExpression(mExpression);

        final List<Fragment> fragmentList = ImmutableList.of(
                basicControlsFragment
        );

        final ViewPager viewPager = findViewById(R.id.frame);
        viewPager.setAdapter(new ControlsAdapter(getSupportFragmentManager(), fragmentList));
    }

    public void onKeyPress(View view) {
        mExpression.setText(mService.evaluate(((Button) view).getText().toString()));
    }

}

