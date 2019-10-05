package com.tbg.www.thebutterflycorner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.ResultViewHolder> {


    Context mContext;
    ArrayList<ModelResult> resultImages;
    int colorStatus;

    public ResultAdapter(Context mContext, ArrayList<ModelResult> resultImages) {
        this.mContext = mContext;
        this.resultImages = resultImages;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view=LayoutInflater.from(mContext).inflate(R.layout.cardview_result,parent,false);
        ResultViewHolder resultViewHolder = new ResultViewHolder(view);
        return resultViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {

        holder.textView.setText(resultImages.get(position).getStatus());
        File file = new File(resultImages.get(position).getImagePath());
        if(file.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());



            holder.imageView1.setImageBitmap(myBitmap);

        }
        holder.imageView2.setImageResource(resultImages.get(position).getImage());

        if(resultImages.get(position).getStatus().equals("CORRECT"))
            colorStatus = Color.parseColor("#32CD32");
        else
            colorStatus = Color.parseColor("#FF0000");

        holder.textView.setTextColor(colorStatus);

    }


    @Override
    public int getItemCount() {
        return resultImages.size();
    }

    public class ResultViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView1;
        ImageView imageView2;
        public ResultViewHolder(@NonNull View itemView) {
            super(itemView);

            textView=itemView.findViewById(R.id.tvImages);
            imageView1=itemView.findViewById(R.id.ivImages1);
            imageView2=itemView.findViewById(R.id.ivImages2);
        }
    }
}
