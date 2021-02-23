package com.withmind.ciabluetooth.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.withmind.ciabluetooth.R;

public class AdViewPagerAdapter extends PagerAdapter {

    private int[] images = {R.drawable.img_brush_banner,R.drawable.img_cooler_banner,R.drawable.img_puff_banner,R.drawable.img_silicon_banner};
    private LayoutInflater inflater;
    private Context context;

    public AdViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.viewpager_activity, container, false);
        ImageView imageView = v.findViewById(R.id.image);
        TextView textView = v.findViewById(R.id.page);

        imageView.setImageResource(images[position]);

        String page = (position + 1) + " / "+ 4;
        textView.setText(page);
        container.addView(v);

        v.setOnClickListener(click -> {
            switch (position) {
                case 0:
                    break;
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
            }
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.hosiden.co.kr/"));
            context.startActivity(intent);
        });

        return v;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.invalidate();
    }
}
