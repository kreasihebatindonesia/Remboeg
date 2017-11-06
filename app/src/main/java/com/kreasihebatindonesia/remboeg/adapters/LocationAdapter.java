package com.kreasihebatindonesia.remboeg.adapters;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.kreasihebatindonesia.remboeg.R;
import com.kreasihebatindonesia.remboeg.activities.LocationActivity;
import com.kreasihebatindonesia.remboeg.models.LocationModel;

import java.util.ArrayList;

/**
 * Created by IT DCM on 06/11/2017.
 */

public class LocationAdapter extends BaseAdapter implements Filterable {

    private ArrayList<LocationModel> mArrayList;
    private ArrayList<LocationModel> mFilteredList;
    private LocationActivity mActivity;
    private LocationFilter locFilter;
    private LocationModel mCurrentLocation;
    private LocationModel locModel;

    public LocationAdapter(LocationActivity activity, LocationModel currentLocation, ArrayList<LocationModel> arrayLoc) {
        mActivity = activity;
        this.mArrayList = arrayLoc;
        this.mFilteredList = arrayLoc;
        mCurrentLocation = currentLocation;
        getFilter();
    }

    @Override
    public int getCount() {
        return mFilteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return mFilteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        locModel = (LocationModel) getItem(position);

        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.listview_row_location, parent, false);
            holder = new ViewHolder();

            holder.mCityName = (TextView) view.findViewById(R.id.mCityName);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }



        holder.mCityName.setText(locModel.GetNameCity());
        if(mCurrentLocation != null){
            int mDrawable = locModel.GetIdCity() == mCurrentLocation.GetIdCity()?R.drawable.icon_check_20dp:null;
            holder.mCityName.setCompoundDrawablesWithIntrinsicBounds(0, 0, mDrawable, 0);
        }

        notifyDataSetChanged();
        return view;
    }

    @Override
    public Filter getFilter() {
        if (locFilter == null) {
            locFilter = new LocationFilter();
        }

        return locFilter;
    }

    private class LocationFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults filterResults = new FilterResults();
            if (constraint!=null && constraint.length()>0) {
                ArrayList<LocationModel> tempList = new ArrayList<LocationModel>();

                for (LocationModel loc : mArrayList) {
                    if (loc.GetNameCity().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        tempList.add(loc);
                    }
                }

                filterResults.count = tempList.size();
                filterResults.values = tempList;
            } else {
                filterResults.count = mArrayList.size();
                filterResults.values = mArrayList;
            }

            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mFilteredList = (ArrayList<LocationModel>) results.values;
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        TextView mCityName;
    }
}