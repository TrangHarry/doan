package com.example.ulesa.ui.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ulesa.R;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyViewHolder> {

    private Context context;
    private String[] listImage;

    public ImageAdapter(Context context, String[] listImage) {
        this.context = context;
        this.listImage = listImage;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_image_pager, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.textView.setText((position +1) + "/" + listImage.length);
        Glide.with(context).load(listImage[position]).into(holder.imgBanner);

    }

    @Override
    public int getItemCount() {
        return listImage.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgBanner;
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgBanner = itemView.findViewById(R.id.image_detail);
            textView = itemView.findViewById(R.id.text_detail);
        }
    }
}