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
import com.udojava.evalex.Expression;

import java.util.List;


public class CalculatorActivity extends AppCompatActivity {

    private EditText expression;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final List<Fragment> fragmentList = ImmutableList.of(
                new BasicControlsFragment()
        );

        expression = findViewById(R.id.expressionText);

        final ViewPager viewPager = findViewById(R.id.frame);
        viewPager.setAdapter(new ControlsAdapter(getSupportFragmentManager(), fragmentList));
    }

    public void onKeyPress(View view) {
        switch (view.getId()) {
            case R.id.buttonDelete:
                onDelete();
                break;
            case R.id.buttonEquality:
                onEquals();
                break;
            default:
                expression.append(((Button) view).getText());
        }
    }

    private void onDelete() {
        final int expressionLength = expression.length();
        if (expressionLength > 0)
            expression.setText(expression.getText().delete(expressionLength - 1, expressionLength));
    }

    private void onEquals() {
        Expression exp = new Expression(expression.getText().toString());
        try {
            expression.setText(exp.eval().toString());
        } catch (Exception e) {
            expression.setText(e.getMessage());
        }
    }
}

