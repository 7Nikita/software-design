package com.nikita.calculator.fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.nikita.calculator.R;
import com.nikita.calculator.service.CalculationsService;

public class BasicControlsFragment extends Fragment {

    private EditText mExpression;
    private Button mDeleteButton;
    private CountDownTimer mTimer;
    private final CalculationsService mService = CalculationsService.getInstance();

    public void setExpression(EditText expression) {
        this.mExpression = expression;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_basic_controls, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDeleteButton = view.findViewById(R.id.buttonDelete);
        mDeleteButton.setOnTouchListener(this::onTouch);
    }

    private boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                deleteChar();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mTimer.cancel();
                break;
        }
        return false;
    }

    private void deleteChar(){
        mTimer = new CountDownTimer(Long.MAX_VALUE, 250) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(mExpression.getText().toString().length() > 0){
                    mExpression.setText(mService.evaluate(mDeleteButton.getText().toString()));
                }
            }
            @Override
            public void onFinish() {
                mTimer.cancel();
            }
        };
        mTimer.start();
    }

}
