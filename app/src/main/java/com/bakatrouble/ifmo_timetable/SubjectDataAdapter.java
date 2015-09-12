package com.bakatrouble.ifmo_timetable;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by bakatrouble on 31.08.2015.
 */
public class SubjectDataAdapter extends RecyclerView.Adapter<SubjectDataAdapter.ViewHolder>  {
    public ArrayList<Subject> mDataset;
    ViewGroup mParent;
    public ArrayList<Integer> processed = new ArrayList<>();

    // Provide a suitable constructor (depends on the kind of dataset)
    public SubjectDataAdapter() {
        mDataset = new ArrayList<>();
    }

    public void clearDataset(){
        mDataset.clear();
    }

    public void updateDataset(ArrayList<Subject> newDataset){
        this.clearDataset();
        mDataset.addAll(newDataset);
        notifyDataSetChanged();
    }

    // Create new views (invoked by the layout manager)
    @Override
    public SubjectDataAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        mParent = parent;

        // create a new view
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.subject_card, null);

        // create ViewHolder

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        if(processed.contains(position)){
            return;
        }
        processed.add(position);
        if(position > 0){
            String[] end = mDataset.get(position-1).time_end.split(":");
            String[] begin = mDataset.get(position).time_begin.split(":");
            int iEnd = Integer.parseInt(end[0]) * 60 + Integer.parseInt(end[1]);
            int iBegin = Integer.parseInt(begin[0]) * 60 + Integer.parseInt(begin[1]);
            if(iBegin - iEnd > 80){
                viewHolder.window.setVisibility(View.VISIBLE);
            }
        }
        viewHolder.time_begin.setText(mDataset.get(position).time_begin);
        viewHolder.time_end.setText(mDataset.get(position).time_end);
        viewHolder.title.setText(mDataset.get(position).title);
        viewHolder.teacher.setText(mDataset.get(position).teacher);
        if(mDataset.get(position).teacher.isEmpty())
            viewHolder.teacher.setVisibility(View.GONE);
        viewHolder.place.setText(mDataset.get(position).place);
        if(mDataset.get(position).addons.size() > 0){
            for(int i=0; i<mDataset.get(position).addons.size(); i++){
                viewHolder.teacher.setText(viewHolder.teacher.getText() + ", " + mDataset.get(position).addons.get(i).teacher);
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView time_begin;
        public TextView time_end;
        public TextView title;
        public TextView teacher;
        public TextView place;
        public RelativeLayout window;
        public LinearLayout wrapper;
        public RelativeLayout card_root;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            time_begin = (TextView) itemLayoutView.findViewById(R.id.time_begin);
            time_end = (TextView) itemLayoutView.findViewById(R.id.time_end);
            title = (TextView) itemLayoutView.findViewById(R.id.subject);
            teacher = (TextView) itemLayoutView.findViewById(R.id.teacher);
            place = (TextView) itemLayoutView.findViewById(R.id.place);
            window = (RelativeLayout) itemLayoutView.findViewById(R.id.window_text_wrapper);
            wrapper = (LinearLayout) itemLayoutView.findViewById(R.id.card_wrapper);
            card_root = (RelativeLayout) itemLayoutView.findViewById(R.id.subject_root);
        }
    }
}
