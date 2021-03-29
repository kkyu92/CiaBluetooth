package com.withmind.ciabluetooth.util;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.withmind.ciabluetooth.Constants;
import com.withmind.ciabluetooth.R;

import java.util.Objects;


//다이얼로그 밖의 화면은 흐리게 만들어줌
//        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
//                layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//                layoutParams.dimAmount = 0.8f;
//                getWindow().setAttributes(layoutParams);



public class CustomDialog extends DialogFragment {
    private static final String TAG = CustomDialog.class.getSimpleName();
    public static boolean DIALOG_STATE = false;

    private Activity mActivity;
    private int mNoticeType;
    private String mTitle, mContents;

    private String mStringData;
    private OnDialogClick mOnDialogClick;

    public void setNoticeType(Activity activity, int type) {
        mActivity = activity;
        mNoticeType = type;
    }

    public void setNoticeTypePauseText(Activity activity, int type, String title, String contents) {
        mActivity = activity;
        mNoticeType = type;
        mTitle = title;
        mContents = contents;
    }

    public void setStringData(String data) {
        mStringData = data;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mActivity == null) {
            dismiss();
            return null;
        }
        String tag = getTag();
        assert tag != null;

        Objects.requireNonNull(getDialog().getWindow()).requestFeature(Window.FEATURE_NO_TITLE);
//        if (!tag.equals(Constants.INTERVIEW_DIALOG)) {
//            getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//        }

        switch (tag) {
            case Constants.APP_FINISH_DIALOG:
                return appFinishDialog(inflater, container);
            case Constants.GRAPH_DIALOG:
                return graphDialog(inflater, container);
        }
        return null;
    }

    private View graphDialog(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.custom_dialog, container);
        Log.i(TAG, "sexDialog");

        LinearLayout btnContainer = view.findViewById(R.id.btn_container);
//        Button ok = view.findViewById(R.id.ok_btn);
//        Button cancel = view.findViewById(R.id.cancel_btn);

        btnContainer.setVisibility(View.GONE);
//        ok.setOnClickListener(mOnClickListener);
//        cancel.setOnClickListener(mOnClickListener);
        return view;
    }

    private View appFinishDialog(LayoutInflater inflater, ViewGroup container) {
        View view = inflater.inflate(R.layout.custom_dialog, container);
        Log.i(TAG, "logoutDialog");

        TextView title = view.findViewById(R.id.title);
        TextView contents = view.findViewById(R.id.content);
        Button ok = view.findViewById(R.id.ok_btn);
        Button cancel = view.findViewById(R.id.cancel_btn);

        title.setText("AU 앱을 종료하시겠습니까?");
        contents.setVisibility(View.GONE);
        ok.setOnClickListener(mOnClickListener);
        cancel.setOnClickListener(mOnClickListener);

        return view;
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ok_btn:
                    // 종료
                    if (mOnDialogClick != null) {
                        mOnDialogClick.onDialogClick(mNoticeType, true);
                        dismiss();
                    }
                    break;
                case R.id.cancel_btn:
                    // 닫기
                    dismiss();
                    DIALOG_STATE = false;
                    if (mOnDialogClick != null) {
                        mOnDialogClick.onDialogClick(mNoticeType, false);
                    }
                    break;
            }
        }
    };

    public void setOnDialogClick(OnDialogClick onDialogClick) {
        mOnDialogClick = onDialogClick;
    }

    public interface OnDialogClick {
        void onDialogClick(int type, boolean isOk);
    }
}