package com.example.ciabluetooth.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.ciabluetooth.R;

public class StepContainer extends ConstraintLayout {
    ImageView img;
    TextView step;
    TextView contents;

    public StepContainer(Context context) {
        super(context);
        initView();
    }

    public StepContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
        getAttrs(attrs);
    }

    public StepContainer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs);
        initView();
        getAttrs(attrs, defStyle);
    }

    private void initView() {
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        View v = li.inflate(R.layout.step_container, this, false);
        addView(v);
        img = (ImageView) findViewById(R.id.img);
        step = (TextView) findViewById(R.id.step);
        contents = (TextView) findViewById(R.id.contents);
    }

    private void getAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StepContainer);
        setTypeArray(typedArray);
    }

    private void getAttrs(AttributeSet attrs, int defStyle) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.StepContainer, defStyle, 0);
        setTypeArray(typedArray);
    }

    private void setTypeArray(TypedArray typedArray) {
        int img_resID = typedArray.getResourceId(R.styleable.StepContainer_img, R.drawable.img_brush_guide1);
        img.setImageResource(img_resID);

//        int stepColor = typedArray.getColor(R.styleable.StepContainer_stepColor, 0);
//        int contentsColor = typedArray.getColor(R.styleable.StepContainer_contentsColor, 0);
//        step.setTextColor(stepColor);
//        contents.setTextColor(contentsColor);

        String step_string = typedArray.getString(R.styleable.StepContainer_step);
        step.setText(step_string);
        String contents_string = typedArray.getString(R.styleable.StepContainer_contents);
        step.setText(contents_string);
        typedArray.recycle();
    }

    void setImg(int img_resID) {
        img.setImageResource(img_resID);
    }

//    void setTextStepColor(int color) {
//        step.setTextColor(color);
//    }
    void setTextStep(String step_string) {
        step.setText(step_string);
    }
    void setTextStep(int step_resID) {
        step.setText(step_resID);
    }

//    void setTextContentsColor(int color) {
//        step.setTextColor(color);
//    }
    void setTextContents(String contents_string) {
        step.setText(contents_string);
    }
    void setTextContents(int contents_resID) {
        step.setText(contents_resID);
    }
}