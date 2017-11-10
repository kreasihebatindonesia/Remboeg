package com.kreasihebatindonesia.remboeg.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.globals.Const;
import com.kreasihebatindonesia.remboeg.models.SearchEventModel;

import java.util.ArrayList;

/**
 * Created by IT DCM on 08/11/2017.
 */

public class SearchEventAdapter extends BaseAdapter {
    private ArrayList<SearchEventModel> mSearchEventModels;
    private Activity mActivity;

    public SearchEventAdapter(Activity activity, ArrayList<SearchEventModel> arraySearch) {
        mActivity = activity;
        mSearchEventModels = arraySearch;
    }

    @Override
    public int getCount() {
        return mSearchEventModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mSearchEventModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        SearchEventModel sModel = (SearchEventModel) getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_row_search_event, parent, false);
            holder = new ViewHolder();

            holder.mSearchText = (TextView) convertView.findViewById(R.id.mSearchText);
            holder.mTicketText = (TextView) convertView.findViewById(R.id.mTicketText);
            holder.mAddressText = (TextView) convertView.findViewById(R.id.mAddressText);

            holder.mImageSearch = (ImageView) convertView.findViewById(R.id.mImageSearch);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mSearchText.setText(sModel.getTitle());
        holder.mTicketText.setText(sModel.getTicket());
        holder.mAddressText.setText(sModel.getAddress());
        Glide.with(mActivity).load(Const.URL_UPLOADS + sModel.getImage()).into(holder.mImageSearch);

        notifyDataSetChanged();
        return convertView;
    }

    static class ViewHolder {
        TextView mSearchText;
        TextView mTicketText;
        TextView mAddressText;
        ImageView mImageSearch;
    }
}
