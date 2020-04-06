package com.example.costcoproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.costcoproject.R;

public class ErrorFragment extends BaseFragment {

    public static final String NAME = "error-fragment";
    private static final String ARG_ERROR_KEY = "arg-error-key";
    private TextView errorText;
    private String errorStr;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) {
            errorStr = getArguments().getString(ARG_ERROR_KEY, "");
        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        errorText = (TextView) view.findViewById(R.id.errorText);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (errorText != null) {
            if (getContext() != null) {
                errorText.setText(getContext().getString(R.string.error_occurred, errorStr));
            }
        }
    }

    public static ErrorFragment newInstance(String errorStr) {
        ErrorFragment errorFragment = new ErrorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ERROR_KEY, errorStr);
        errorFragment.setArguments(args);
        return errorFragment;
    }
}
