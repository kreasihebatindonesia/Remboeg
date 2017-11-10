package com.kreasihebatindonesia.remboeg.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
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
import com.kreasihebatindonesia.remboeg.models.NearbyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by InfinityLogic on 11/10/2017.
 */

public class NearbyJobAdapter  extends RecyclerView.Adapter<NearbyJobAdapter.ViewHolder> {

    private Context context;
    private List<NearbyModel> items;


    public NearbyJobAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void setItems(List<NearbyModel> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public NearbyModel getItem(int position){
        return this.items.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTitle;
        private TextView txtCaption;
        private TextView txtTicket;
        private ImageView mImage;
        private CardView mCardView;


        public ViewHolder(View itemView) {
            super(itemView);

            this.txtTitle = (TextView)itemView.findViewById(R.id.txtTiitle);
            this.txtCaption = (TextView)itemView.findViewById(R.id.txtAddress);
            this.txtTicket = (TextView)itemView.findViewById(R.id.txtTicket);
            this.mImage = (ImageView) itemView.findViewById(R.id.mImage);
            this.mCardView = (CardView)itemView.findViewById(R.id.mCardView);
        }

        public void bind(final NearbyModel locationInformation) {
            this.txtTitle.setText(locationInformation.getTitle());
            this.txtCaption.setText(locationInformation.getAddress());
            this.txtTicket.setText(locationInformation.getSalary());
            Glide.with(context).load(Const.URL_UPLOADS + locationInformation.getImage()).into(mImage);

            mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (locationInformation.getIdType()){
                        case 1:
                            Intent i = new Intent(context, DetailEventActivity.class);
                            i.putExtra("id_job", locationInformation.getId());
                            context.startActivity(i);
                            break;
                    }

                }
            });
        }
    }

    @Override
    public NearbyJobAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(this.context).inflate(R.layout.item_card_nearby, parent, false);

        return new NearbyJobAdapter.ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(NearbyJobAdapter.ViewHolder holder, int position) {
        holder.bind(this.items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}
