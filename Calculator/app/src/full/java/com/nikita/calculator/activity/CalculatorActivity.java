package com.nikita.calculator.activity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.nikita.calculator.ControlsAdapter;
import com.nikita.calculator.R;
import com.nikita.calculator.service.CalculationsService;
import com.nikita.calculator.fragment.BasicControlsFragment;
import com.nikita.calculator.fragment.ScientificControlsFragment;

import com.google.common.collect.ImmutableList;

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

        final ScientificControlsFragment scientificControlsFragment = new ScientificControlsFragment();
        scientificControlsFragment.setExpression(mExpression);

        final List<Fragment> fragmentList = ImmutableList.of(
                basicControlsFragment,
                scientificControlsFragment
        );


        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            final ViewPager viewPager = findViewById(R.id.frame);
            viewPager.setAdapter(new ControlsAdapter(getSupportFragmentManager(), fragmentList));
        } else {
            final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction
                    .add(R.id.basicMode, fragmentList.get(0))
                    .add(R.id.scientificMode, fragmentList.get(1))
                    .commit();
        }
    }

    public void onKeyPress(@NonNull View view) {
        mExpression.setText(mService.evaluate(((Button) view).getText().toString()));
    }

}

