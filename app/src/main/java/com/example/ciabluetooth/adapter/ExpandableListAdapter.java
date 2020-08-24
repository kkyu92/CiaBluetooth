package com.example.ciabluetooth.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ciabluetooth.Constants;
import com.example.ciabluetooth.R;
import com.example.ciabluetooth.data.Permission;

import java.util.ArrayList;

public class ExpandableListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<Permission> mData;

    public ExpandableListAdapter(Context context, ArrayList<Permission> data)
    {
        mContext = context;
        mData = data;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        float dp = context.getResources().getDisplayMetrics().density;

        if (mData.get(i).type == Constants.EXPANDABLE_CHILD)
        {
            TextView itemTextView = new TextView(context);
            itemTextView.setTextColor(mContext.getResources().getColor(R.color.text_color));
            itemTextView.setTextSize(14);
            itemTextView.setPadding((int) (dp * 20), 0, 0, (int) (dp * 10));
            itemTextView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            itemTextView.setGravity(Gravity.CENTER_VERTICAL);
            return new RecyclerView.ViewHolder(itemTextView){};
        }

        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.expandable_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).type;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final Permission item = mData.get(i);

        switch (item.type)
        {
            case Constants.EXPANDABLE_CHILD:
                TextView itemTextView = (TextView) viewHolder.itemView;
                itemTextView.setText(mData.get(i).text);
                itemTextView.setTextSize(16);
                break;
            case Constants.EXPANDABLE_PARENT:
                final ViewHolder itemController = (ViewHolder) viewHolder;
                itemController.permissionItem = item;
                itemController.text.setText(item.text);
                itemController.text.setTextSize(18);
                itemController.text.setTypeface(null, Typeface.BOLD);

                itemController.icon.setImageResource(item.icon);
                if (item.invisibleChildren == null)
                {
                    itemController.clickView.setImageResource(R.drawable.ic_icon_arrow);
                }
                else
                {
                    itemController.clickView.setImageResource(R.drawable.ic_icon_arrow_down);
                }
                itemController.clickView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (item.invisibleChildren == null)
                        {
                            item.invisibleChildren = new ArrayList<>();
                            int count = 0;
                            int pos = mData.indexOf(itemController.permissionItem);
                            while (mData.size() > pos + 1 && mData.get(pos + 1).type == Constants.EXPANDABLE_CHILD)
                            {
                                item.invisibleChildren.add(mData.remove(pos + 1));
                                count++;
                            }
                            notifyItemRangeRemoved(pos + 1, count);
                            itemController.clickView.setImageResource(R.drawable.ic_icon_arrow_down);
                        }
                        else
                        {
                            int pos = mData.indexOf(itemController.permissionItem);
                            int index = pos + 1;
                            for (Permission i : item.invisibleChildren)
                            {
                                mData.add(index, i);
                                index++;
                            }
                            notifyItemRangeInserted(pos + 1, index - pos - 1);
                            itemController.clickView.setImageResource(R.drawable.ic_icon_arrow);
                            item.invisibleChildren = null;
                        }
                    }
                });
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        public TextView text;
        ImageView clickView;
        Permission permissionItem;

        ViewHolder(View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.icon);
            text = itemView.findViewById(R.id.text);
            clickView = itemView.findViewById(R.id.click_view);
        }
    }
}
