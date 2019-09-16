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

public class CustomGridAdapter extends BaseAdapter {

    private Context mContext;
    Dialog myDialog;
    ArrayList<String> gridViewString;
    ArrayList<Integer> gridViewImageId;
    ArrayList<String> gridDesc;

    public CustomGridAdapter(Context context, ArrayList<String>gridViewString, ArrayList<Integer>gridViewImageId,ArrayList<String> gridDesc) {
        mContext = context;
        this.gridViewImageId = gridViewImageId;
        this.gridViewString = gridViewString;
        this.gridDesc=gridDesc;

    }

    @Override
    public int getCount() {
        return gridViewString.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup parent) {
        View gridViewAndroid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {



            gridViewAndroid = new View(mContext);
            gridViewAndroid = inflater.inflate(R.layout.grid_layout, null);





            TextView textViewAndroid = (TextView) gridViewAndroid.findViewById(R.id.android_gridview_text);
            ImageView imageViewAndroid = (ImageView) gridViewAndroid.findViewById(R.id.android_gridview_image);
            textViewAndroid.setText(gridViewString.get(i));
            imageViewAndroid.setImageResource(gridViewImageId.get(i));
           /* ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    myDialog = new Dialog(mContext);



                    TextView  dialogName= v.findViewById(R.id.dialog_name);
                    TextView dialogDesc =v.findViewById(R.id.textviewDesc);
                    ImageView imgDesc= v.findViewById(R.id.dialog_icon);

                    dialogName.setText(gridViewString[i]);
                    dialogDesc.setText(gridDesc[i]);
                    imgDesc.setImageResource(gridViewImageId[i]);


                }
            });*/
        } else {
            gridViewAndroid = (View) convertView;
        }

        return gridViewAndroid;
    }
}