package com.example.ulesa.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.HomeModel;
import com.example.ulesa.model.PriceVND;
import com.example.ulesa.ui.Manager.EditRoomActivity;
import com.example.ulesa.ui.detail.DetailRoomActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<HomeModel> list;
    private Context context;
    private Context mContext;
    private Bitmap mBitmap;
    private int delete = -1;

    public HomeAdapter(ArrayList<HomeModel> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.item_home, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        HomeModel homeModel = list.get(position);
        holder.textName.setText(homeModel.getRoomName());
        Glide.with(mContext).load(homeModel.getPictureMain()).into(holder.imageView);
        holder.textLocation.setText(homeModel.getLocation());
        holder.textStar.setText("" + homeModel.getEvaluate());
        holder.textNumberDes.setText("" + homeModel.getViewEvaluate());
        holder.textBooking.setText("" + homeModel.getViewBooking());
        holder.textPrice.setText(PriceVND.changeToVND(homeModel.getPrice()));
        holder.itemLayout.setOnClickListener( v -> {
            Intent intent = new Intent(mContext, DetailRoomActivity.class);
            if(DataBaseHelper.getUserName(mContext).equals("trang@gmail.com")) {
                intent = new Intent(mContext, EditRoomActivity.class);
            }
            Bundle bundle = new Bundle();
            bundle.putSerializable("room_detail", (Serializable) homeModel);
            intent.putExtra("room_id", homeModel.getNameID());
            mContext.startActivity(intent);
        });
    }
    public int getDelete() {
        return delete;
    }

    public void setDelete(int delete) {
        this.delete = delete;
    }

    @Override
    public int getItemCount() {
        Log.d("TRANG","" + list.size());
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textLocation, textStar, textNumberDes, textBooking, textName,textPrice;
        LinearLayout itemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_main);
            textLocation = itemView.findViewById(R.id.text_location);
            textStar = itemView.findViewById(R.id.text_star);
            textNumberDes = itemView.findViewById(R.id.text_number_decs);
            textBooking = itemView.findViewById(R.id.text_bookings);
            textName = itemView.findViewById(R.id.text_name);
            itemLayout = itemView.findViewById(R.id.item_layout);
            textPrice = itemView.findViewById(R.id.txt_price);
        }
    }
}
