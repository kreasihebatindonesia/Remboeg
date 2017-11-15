package com.kreasihebatindonesia.remboeg.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Matrix;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.activities.DetailEventActivity;
import com.kreasihebatindonesia.remboeg.activities.DetailJobActivity;
import com.kreasihebatindonesia.remboeg.component.CompoundIconTextView;
import com.kreasihebatindonesia.remboeg.component.TopAlignedImageView;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.models.EventModel;
import com.kreasihebatindonesia.remboeg.models.JobModel;
import com.kreasihebatindonesia.remboeg.utils.FormatNumber;

import java.util.List;

/**
 * Created by InfinityLogic on 11/10/2017.
 */

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.MyViewHolder> {
    private Context mContext;
    private List<JobModel> mJobModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleJob;
        public TopAlignedImageView mImageJob;
        public TextView mSalary;
        public TextView mEndDate;
        public CompoundIconTextView txtView;
        public CompoundIconTextView txtLike;


        public MyViewHolder(View view) {
            super(view);
            mTitleJob = (TextView) view.findViewById(R.id.mTitleJob);
            mImageJob = (TopAlignedImageView) view.findViewById(R.id.mImageJob);
            mSalary = (TextView) view.findViewById(R.id.mSalary);
            mEndDate = (TextView) view.findViewById(R.id.mEndDate);
            txtView = (CompoundIconTextView) view.findViewById(R.id.txtView);
            txtLike = (CompoundIconTextView) view.findViewById(R.id.txtLike);
        }
    }

    public JobAdapter(Context mContext, List<JobModel> mJobModelList) {
        this.mContext = mContext;
        this.mJobModelList = mJobModelList;
    }

    @Override
    public JobAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_job_layout, parent, false);

        return new JobAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final JobAdapter.MyViewHolder holder, int position) {
        final JobModel mJob = mJobModelList.get(position);

        holder.mTitleJob.setText(mJob.getTitleJob());

        Glide.with(mContext).load(Const.URL_UPLOADS + mJob.getImageJob()).into(holder.mImageJob);

        holder.mEndDate.setText(mJob.getEndDateJob());
        holder.mSalary.setText(mJob.getSalaryJob());
        holder.txtView.setText(FormatNumber.getFormatNumber(mJob.getTotalViews()) + "");
        holder.txtLike.setText(FormatNumber.getFormatNumber(mJob.getTotalLikes()) + "");

        holder.mImageJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailJobActivity.class);
                i.putExtra("id_job", mJob.getIdJob());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mJobModelList.size();
    }
}
