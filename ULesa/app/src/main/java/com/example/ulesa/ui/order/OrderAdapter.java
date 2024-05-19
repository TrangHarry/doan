package com.example.ulesa.ui.order;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ulesa.LoginActivity;
import com.example.ulesa.R;
import com.example.ulesa.RegisterActivity;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.OrderModel;
import com.example.ulesa.model.PriceVND;
import com.example.ulesa.ui.home.UnBookingSuccessActivity;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<OrderModel> list;
    private Context context;
    private Context mContext;
    private int delete = -1;
    public OrderAdapter(ArrayList<OrderModel> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.item_order, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orderModel = list.get(position);
        holder.txtName.setText(orderModel.getRoomName());
        Glide.with(mContext).load(orderModel.getPictureMain()).into(holder.imageView);
        holder.txttotal.setText(PriceVND.changeToVND(orderModel.getTotal()));
        holder.txtstatus.setText(orderModel.getStatus() == 1? "Hoàn thành" : "Không hoàn thành");
        holder.txtIdbill.setText( orderModel.getbillID());
        holder.btnevaluate.setOnClickListener( v -> {
            Intent intent = new Intent(mContext, EvaluateRoomActivity.class);
            intent.putExtra("room_id", orderModel.getNameID());
            mContext.startActivity(intent);
        });
        holder.btncanclebook.setOnClickListener(v ->{
            DataBaseHelper.deleteOrder(mContext, orderModel.getbillID(), 2);
            delete = position;
            Intent intent = new Intent(mContext, UnBookingSuccessActivity.class);
            mContext.startActivity(intent);
        });
    }
    public int getDelete(){
        return delete;
    }
    public void reloadDelete(){
        delete = -1;
    }
    @Override
    public int getItemCount() {
        Log.d("TRANG","" + list.size());
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView txtName,txtIdbill,txttotal,txtstatus;
        Button btnevaluate, btncanclebook;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_room);
            txtstatus = itemView.findViewById(R.id.txt_status);
            txtIdbill = itemView.findViewById(R.id.txt_idbill);
            txtName = itemView.findViewById(R.id.txt_name);
            btnevaluate = itemView.findViewById(R.id.btn_evaluateroom);
            txttotal = itemView.findViewById(R.id.txt_total);
            btncanclebook = itemView.findViewById(R.id.btn_cancle_book);
        }
    }
}
