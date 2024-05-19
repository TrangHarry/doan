package com.example.ulesa.ui.Manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.model.UserModel;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private ArrayList<UserModel> list;
    private Context context;
    private Context mContext;
    private int delete = -1;

    public UserAdapter(ArrayList<UserModel> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        UserModel userModel = list.get(position);
        holder.textmname.setText(userModel.getName());
        holder.textmgmail.setText(userModel.getGmail());
        holder.imageView.setOnClickListener(v -> {
            DataBaseHelper.deleteUser(context, userModel.getIDUser(), 2);
            delete = position;
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
        TextView  textmname, textmgmail;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textmname = itemView.findViewById(R.id.text_mname);
            textmgmail = itemView.findViewById(R.id.text_mgmail);
            imageView = itemView.findViewById(R.id.image_delete);
        }
    }
}
