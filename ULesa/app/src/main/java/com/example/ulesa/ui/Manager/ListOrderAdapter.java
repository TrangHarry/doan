package com.example.ulesa.ui.Manager;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulesa.R;
import com.example.ulesa.model.Date;
import com.example.ulesa.model.OrderModel;

import java.util.ArrayList;

public class ListOrderAdapter extends RecyclerView.Adapter<ListOrderAdapter.ViewHolder> {

    private ArrayList<OrderModel> list;
    private Date date;

    private ArrayList<Date> listdate;
    private Context context;
    private Context mContext;
    public ListOrderAdapter(ArrayList<OrderModel> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.item_list_orderr, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderModel orderModel = list.get(position);
        holder.txtdate.setText( orderModel.getDate());
        holder.txtstatus.setText(orderModel.getStatus() == 1? "Hoàn thành" : "Không hoàn thành");
        holder.txtIdbill.setText( orderModel.getbillID());
        holder.btneditt.setOnClickListener(v -> {
            Intent intent = new Intent(mContext, EditStatusActivity.class);
            intent.putExtra("id_bill", orderModel.getBillID());
            intent.putExtra("status", orderModel.getStatus());
            mContext.startActivity(intent);
        });
        holder.layoutdetailorder.setOnClickListener(v ->{
            Intent intent = new Intent(mContext, ManOrderDetailActivity.class);
            intent.putExtra("id_bill", orderModel.getBillID());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        Log.d("TRANG","" + list.size());
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtIdbill,txtdate,txtstatus;
        ImageView btneditt;
        LinearLayout layoutdetailorder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtstatus = itemView.findViewById(R.id.text_status);
            txtIdbill = itemView.findViewById(R.id.text_idbill);
            txtdate = itemView.findViewById(R.id.text_date);
            btneditt = itemView.findViewById(R.id.btn_editt);
            layoutdetailorder = itemView.findViewById(R.id.layout_detail_order);
        }
    }
}
