package com.example.ciabluetooth.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ciabluetooth.R;
import com.example.ciabluetooth.data.GuideData;
import com.example.ciabluetooth.databinding.ItemGuideStepBinding;
import com.example.ciabluetooth.interfaces.OnGuideItemClickListener;

import java.util.ArrayList;

public class GuideAdapter extends RecyclerView.Adapter<GuideAdapter.ViewHolder> {
    private static final String TAG = GuideAdapter.class.getSimpleName();

    private Context mContext;
    private ArrayList<GuideData> mData;

    private OnGuideItemClickListener mListener;

    public GuideAdapter(Context context, ArrayList<GuideData> data, OnGuideItemClickListener listener) {
        mContext = context;
        mData = data;
        mListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemGuideStepBinding binding = ItemGuideStepBinding.inflate(LayoutInflater.from(mContext), viewGroup, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.onBind(mData.get(i));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemGuideStepBinding mBinding;

        ViewHolder(@NonNull ItemGuideStepBinding binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        void onBind(GuideData guide) {
            mBinding.guideContainer.setTag(guide);
            mBinding.guideContainer.setOnClickListener(this);

            mBinding.guideContentText.setText(guide.title);
            if (guide.check) {
                mBinding.guideContainer.setBackgroundResource(R.drawable.pink_round);
                mBinding.guidePointImg.setVisibility(View.GONE);
            } else {
                mBinding.guideContainer.setBackgroundResource(R.drawable.white_round);
                mBinding.guidePointImg.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.guide_container:
                    GuideData guideData = (GuideData) v.getTag();
                    Log.e(TAG, "click guideContainer");
                    Log.e(TAG, guideData.title + guideData.check + getAdapterPosition());
                    if (guideData.check) {
                        mBinding.guideContainer.setBackgroundResource(R.drawable.white_round);
                        mBinding.guidePointImg.setVisibility(View.VISIBLE);
                    } else {
                        mBinding.guideContainer.setBackgroundResource(R.drawable.pink_round);
                        mBinding.guidePointImg.setVisibility(View.GONE);
                    }
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
