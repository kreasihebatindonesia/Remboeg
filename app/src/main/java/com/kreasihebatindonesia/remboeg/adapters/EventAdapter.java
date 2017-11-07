package com.kreasihebatindonesia.remboeg.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.activities.DetailEventActivity;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.models.EventModel;
import com.kreasihebatindonesia.remboeg.utils.FormatNumber;

import java.util.List;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyViewHolder> {
    private Context mContext;
    private List<EventModel> mEventModelList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleEvent;
        public ImageView mImageEvent;
        public TextView mTicketEvent;
        public TextView txtView;
        public TextView txtLike;


        public MyViewHolder(View view) {
            super(view);
            mTitleEvent = (TextView) view.findViewById(R.id.mTitleEvent);
            mImageEvent = (ImageView) view.findViewById(R.id.mImageEvent);
            mTicketEvent = (TextView) view.findViewById(R.id.mTicketEvent);
            txtView = (TextView) view.findViewById(R.id.txtView);
            txtLike = (TextView) view.findViewById(R.id.txtLike);
        }
    }

    public EventAdapter(Context mContext, List<EventModel> mEventModelList) {
        this.mContext = mContext;
        this.mEventModelList = mEventModelList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_event_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final EventModel mEvent = mEventModelList.get(position);

        holder.mTitleEvent.setText(mEvent.getTitleEvent());
        Glide.with(mContext).load(Const.URL_UPLOADS + mEvent.getImageEvent()).into(holder.mImageEvent);
        holder.mTicketEvent.setText(mEvent.getTicketEvent());
        holder.txtView.setText(FormatNumber.getFormatNumber(mEvent.getTotalViews()) + "");
        holder.txtLike.setText(FormatNumber.getFormatNumber(mEvent.getTotalLikes()) + "");

        holder.mImageEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mContext, DetailEventActivity.class);
                i.putExtra("id_event", mEvent.getIdEvent());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mEventModelList.size();
    }

}