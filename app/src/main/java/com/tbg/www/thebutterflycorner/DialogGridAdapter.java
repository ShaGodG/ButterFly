package com.tbg.www.thebutterflycorner;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class DialogGridAdapter extends BaseAdapter {

    private Context mContext;
    ArrayList<String> gridViewString;
    ArrayList<Integer> gridViewImageId;


    public DialogGridAdapter(Context context, ArrayList<String>gridViewString, ArrayList<Integer>gridViewImageId) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;


    }

    @Override
    public int getCount() {
        return gridViewString.size();
    }

    @Override
    public Object getItem(int i) {
        return gridViewString.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {


            //convertView = new View(mContext);
            convertView = inflater.inflate(R.layout.grid_layout, null);
        }



            TextView textViewAndroid = (TextView) convertView.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) convertView.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString.get(i));
            imageViewAndroid.setImageResource(gridViewImageId.get(i));



        return convertView;
    }
}
