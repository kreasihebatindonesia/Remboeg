package com.kreasihebatindonesia.remboeg.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.models.DummyLocalInfoModel;
import com.kreasihebatindonesia.remboeg.models.NearbyModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IT DCM on 07/11/2017.
 */

public class DummyCardAdapter extends RecyclerView.Adapter<DummyCardAdapter.ViewHolder> {

    private Context context;
    private List<NearbyModel> items;


    public DummyCardAdapter(Context context) {
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

        public ViewHolder(View itemView) {
            super(itemView);

            this.txtTitle = (TextView)itemView.findViewById(R.id.text_title);
            this.txtCaption = (TextView)itemView.findViewById(R.id.text_caption);
        }

        public void bind(NearbyModel locationInformation) {
            this.txtTitle.setText(locationInformation.getTitle());
            this.txtCaption.setText(locationInformation.getAddress());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cardView = LayoutInflater.from(this.context).inflate(R.layout.item_card_nearby, parent, false);

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(this.items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }



}