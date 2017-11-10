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
import com.kreasihebatindonesia.remboeg.models.SearchJobModel;

import java.util.ArrayList;

/**
 * Created by InfinityLogic on 11/10/2017.
 */

public class SearchJobAdapter  extends BaseAdapter {
    private ArrayList<SearchJobModel> mSearchJobModels;
    private Activity mActivity;

    public SearchJobAdapter(Activity activity, ArrayList<SearchJobModel> arraySearch) {
        mActivity = activity;
        mSearchJobModels = arraySearch;
    }

    @Override
    public int getCount() {
        return mSearchJobModels.size();
    }

    @Override
    public Object getItem(int position) {
        return mSearchJobModels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        SearchJobModel sModel = (SearchJobModel) getItem(position);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.listview_row_search_job, parent, false);
            holder = new ViewHolder();

            holder.mSearchText = (TextView) convertView.findViewById(R.id.mSearchText);
            holder.mSalaryText = (TextView) convertView.findViewById(R.id.mSalaryText);
            holder.mAddressText = (TextView) convertView.findViewById(R.id.mAddressText);

            holder.mImageSearch = (ImageView) convertView.findViewById(R.id.mImageSearch);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mSearchText.setText(sModel.getTitle());
        holder.mSalaryText.setText(sModel.getSalary());
        holder.mAddressText.setText(sModel.getAddress());
        Glide.with(mActivity).load(Const.URL_UPLOADS + sModel.getImage()).into(holder.mImageSearch);

        notifyDataSetChanged();
        return convertView;
    }

    static class ViewHolder {
        TextView mSearchText;
        TextView mSalaryText;
        TextView mAddressText;
        ImageView mImageSearch;
    }
}