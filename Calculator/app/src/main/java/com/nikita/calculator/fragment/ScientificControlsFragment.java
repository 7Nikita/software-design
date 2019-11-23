package com.nikita.calculator.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nikita.calculator.R;
import com.nikita.calculator.service.CalculationsService;

public class ScientificControlsFragment extends Fragment {

    private Button mLogButton;
    private EditText mExpression;
    private final CalculationsService mService = CalculationsService.getInstance();

    public void setExpression(EditText expression) {
        this.mExpression = expression;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_scientific_controls, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mLogButton = view.findViewById(R.id.buttonLog);
        mLogButton.setOnLongClickListener(v -> {
            showPopupMenu(v);
            return false;
        });
    }

    private void showPopupMenu(View v) {
        PopupMenu popupMenu = new PopupMenu(getContext(), v);
        popupMenu.inflate(R.menu.menu_logs);
        popupMenu.setOnMenuItemClickListener(item -> {
            mExpression.setText(mService.evaluate(item.getTitle().toString()));
            return false;
        });
        popupMenu.show();
    }
}
