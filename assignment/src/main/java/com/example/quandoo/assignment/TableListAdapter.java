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

/**
 * Adapter class that binds the availability status of the tables with the recyclerview.
 */
public class TableListAdapter extends RecyclerView.Adapter<TableListAdapter.ViewHolder> {

    private List<Integer> mDataset;
    onClickListener mOnClickListener;


    public static class ViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/{

        public ImageView tableImage;
        public ImageView reservedStatus;
        public TextView tableIndex;

       // onClickInterface mOnClickInterfaceListener;

        public ViewHolder(View v) {
            super(v);
            tableImage = (ImageView)v.findViewById(R.id.table_picture);
            tableIndex = (TextView)v.findViewById(R.id.table_index);
            reservedStatus = (ImageView)v.findViewById(R.id.reservedstatus);
        }
    }

    /**
     * Callback interface for implementing the behavior when an item is clicked.
     */
    public interface onClickListener{
        void onItemClick(int position);
    }

    /**
     * Constructor
     * @param onItemClickedListener onClickListener interface object
     */
    public TableListAdapter(onClickListener onItemClickedListener) {
        mOnClickListener = onItemClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookingtable, parent, false);
        return new ViewHolder(v);
    }

    public void setData(List<Integer> dataSet) {
        mDataset = dataSet;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int tableStatus = mDataset.get(position);
        holder.itemView.setTag(position);


        holder.tableImage.setImageResource(R.mipmap.table);
        holder.tableImage.setTag(tableStatus);
        if(tableStatus == 1){
            holder.tableImage.setImageAlpha(200);
            holder.tableIndex.setBackgroundColor(Color.GREEN);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnClickListener.onItemClick((int)v.getTag());
                }
            });
        }
        else {
            holder.reservedStatus.setVisibility((tableStatus == 0)?View.GONE:View.VISIBLE);
            holder.tableImage.setImageAlpha(50);
            holder.tableIndex.setBackgroundColor(Color.RED);
        }
        holder.tableIndex.setText(Integer.toString(position + 1));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
