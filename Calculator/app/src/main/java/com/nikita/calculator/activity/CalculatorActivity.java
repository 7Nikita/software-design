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
import com.nikita.calculator.fragment.BasicControlsFragment;
import com.nikita.calculator.fragment.ScientificControlsFragment;

import com.google.common.collect.ImmutableList;
import com.udojava.evalex.Expression;

import java.util.List;


public class CalculatorActivity extends AppCompatActivity {

    private EditText expression;

    private final double mathPi = 3.14;
    private final double mathE = 2.72;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final List<Fragment> fragmentList = ImmutableList.of(
                new BasicControlsFragment(),
                new ScientificControlsFragment()
        );

        expression = findViewById(R.id.expressionText);

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
        switch (view.getId()) {
            case R.id.buttonPi:
                expression.append(String.valueOf(mathPi));
                break;
            case R.id.buttonExp:
                expression.append(String.valueOf(mathE));
                break;
            case R.id.buttonSin:
            case R.id.buttonCos:
            case R.id.buttonTan:
            case R.id.buttonLog:
                expression.append(((Button) view).getText() + "(");
                break;
            case R.id.buttonSqrt:
                expression.append("sqrt(");
                break;
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
        if (expression.length() > 0)
            expression.setText(expression.getText().delete(expression.length() - 1, expression.length()));
    }

    private void onEquals() {
        Expression exp = new Expression(expression.getText().toString());
        try {
            expression.setText(exp.eval().toString());
        } catch (ArithmeticException | IllegalStateException e) {
            expression.setText(e.getMessage());
        }

    }
}

