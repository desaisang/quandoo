package com.example.quandoo.assignment;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quandoo.unittest.R;

import java.util.List;

public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.ViewHolder> {

    private List<Boolean> mDataset;
    private int mOldPosition = -1;

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView tableImage;
        public TextView tableIndex;
        public boolean isAvailable;
        onClickInterface mOnClickInterfaceListener;

        public ViewHolder(View v, onClickInterface onClickListener) {
            super(v);
            mOnClickInterfaceListener = onClickListener;
            tableImage = (ImageView)v.findViewById(R.id.table_picture);
            tableIndex = (TextView)v.findViewById(R.id.table_index);
            v.setOnClickListener(this);

        }

        public interface onClickInterface {
            void onItemClicked(View v);
        }

        @Override
        public void onClick(View v) {
            mOnClickInterfaceListener.onItemClicked(v);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookingtable, parent, false);
        return new ViewHolder(v, new ViewHolder.onClickInterface() {
            @Override
            public void onItemClicked(View v) {
                int position = (int)v.getTag();
                boolean isTableAvailable = mDataset.get(position);
                if(isTableAvailable){
                    ((ImageView)v.findViewById(R.id.table_picture)).setImageAlpha(50);
                    ((TextView)v.findViewById(R.id.table_index)).setBackgroundColor(Color.RED);
                    mDataset.set(position, false);
                }
            }
        });
    }

    public void setData(List<Boolean> dataSet) {
        mDataset = dataSet;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        boolean isTableAvailable = mDataset.get(position);
        holder.itemView.setTag(position);

        holder.tableImage.setImageResource(R.mipmap.table);
        holder.tableImage.setTag(isTableAvailable);
        /*holder.tableImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isavailable = (boolean)v.getTag();
                if(isavailable) {

                }
            }
        });*/
        if(isTableAvailable){
            holder.tableImage.setImageAlpha(200);
            holder.tableIndex.setBackgroundColor(Color.GREEN);
            holder.isAvailable = true;
        }
        else {
            holder.tableImage.setImageAlpha(50);
            holder.tableIndex.setBackgroundColor(Color.RED);
            holder.isAvailable = false;
        }
        holder.tableIndex.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
