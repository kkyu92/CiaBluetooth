package com.example.ciabluetooth.fragment.manual;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.fragment.BaseFragment;

public class ManualHeadFragment extends BaseFragment {

    public static ManualHeadFragment getInstance() { return new ManualHeadFragment(); }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_manual_head, container, false);
    }
}
