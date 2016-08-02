package com.sh3h.water.test0233;

/**
 * Created by zangwei on 2016/8/2.
 */

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

/**
 * Provide views to RecyclerView with data from mDataSet.
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private static final String TAG = "CustomAdapter";

    //private static final int TYPE_ITEM = 0;
    //private static final int TYPE_FOOTER = 1;

    private List<String> mDataSet;

    private int mPosition;

    // BEGIN_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Provide a reference to the type of views that you are using (custom viewholder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ProgressBar mProgressBar;
        private final TextView mTextView;

        public ViewHolder(View v) {
            super(v);
            mProgressBar = (ProgressBar) v.findViewById(R.id.progressbar);
            mTextView = (TextView) v.findViewById(R.id.textView);
        }

        public TextView getmTextView() {
            return mTextView;
        }

        public ProgressBar getmProgressBar() {
            return mProgressBar;
        }
    }
    // END_INCLUDE(recyclerViewSampleViewHolder)
    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(List<String> dataSet) {
        mDataSet = dataSet;
        mPosition = 0;
    }

    public void setmDataSet(List<String> dataSet) {
        mDataSet = dataSet;
        mPosition = 0;
        notifyDataSetChanged();
    }

    // BEGIN_INCLUDE(recyclerViewOnCreateViewHolder)
    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        // Create a new view.
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.text_row_item, viewGroup, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    // END_INCLUDE(recyclerViewOnCreateViewHolder)

    // BEGIN_INCLUDE(recyclerViewOnBindViewHolder)
    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        //Log.d(TAG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.getmTextView().setText(mDataSet.get(position));
        if (position == mDataSet.size() - 1) {
            viewHolder.getmProgressBar().setVisibility(View.VISIBLE);
        } else {
            viewHolder.getmProgressBar().setVisibility(View.GONE);
        }

        mPosition = position;
    }
    // END_INCLUDE(recyclerViewOnBindViewHolder)

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

//    @Override
//    public int getItemViewType(int position) {
//        // 最后一个item设置为footerView
//        if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        } else {
//            return TYPE_ITEM;
//        }
//    }

    public int getCurrentPosition() {
        return mPosition;
    }
}