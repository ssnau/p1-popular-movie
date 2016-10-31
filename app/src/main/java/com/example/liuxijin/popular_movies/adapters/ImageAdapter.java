package com.example.liuxijin.popular_movies.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by liuxijin on 10/31/16.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return urls.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = (ImageView) convertView;
        if (view == null) {
            view = new ImageView(mContext);
        }

        Picasso
                .with(mContext)
                .load(urls[position])
                .resize(400, 600)
                .centerCrop()
                .into(view);

        return view;
    }

    public void updateURLs(String[] urls) {
        this.urls = urls;
        notifyDataSetChanged();
    }

    // references to our images
    private String[] urls = {};
}