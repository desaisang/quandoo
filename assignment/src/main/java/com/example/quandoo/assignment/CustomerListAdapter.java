package com.example.quandoo.assignment;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.quandoo.assignment.model.CustomerResponseJson;
import com.example.quandoo.unittest.R;

import java.util.List;

/**
 * Adapter class that binds the customer data with the recyclerview.
 */
public class CustomerListAdapter extends RecyclerView.Adapter<CustomerListAdapter.ViewHolder> {

    private List<CustomerResponseJson> mDataset;

    private onClickListener mOnClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView firstName;
        public TextView lastName;
        public TextView customerID;

        public ViewHolder(View v) {
            super(v);
            firstName = (TextView) v.findViewById(R.id.firstName);
            lastName = (TextView) v.findViewById(R.id.lastName);
            customerID = (TextView) v.findViewById(R.id.customerID);
        }
    }

    public interface onClickListener{
        void onItemClick();
    }

    public CustomerListAdapter(onClickListener onItemClickedListener) {
        mOnClickListener = onItemClickedListener;
    }

    public void setData(List<CustomerResponseJson> dataSet) {
        mDataset = dataSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customerlist, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final String name = mDataset.get(position).mcustomerFirstName;
        holder.firstName.setText(name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onItemClick();
            }
        });
        holder.customerID.setText(mDataset.get(position).mID);
        holder.lastName.setText(mDataset.get(position).mcustomerLastName);
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
