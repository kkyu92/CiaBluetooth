package com.withmind.ciabluetooth.fragment.manual;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.withmind.ciabluetooth.R;
import com.withmind.ciabluetooth.fragment.BaseFragment;

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
