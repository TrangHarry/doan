package com.example.ulesa.ui.detail;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulesa.R;
import com.example.ulesa.model.CommentModel;

import java.util.ArrayList;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private ArrayList<CommentModel> list;
    private Context context;
    private Context mContext;

    public CommentAdapter(ArrayList<CommentModel> list, Context context) {
        this.list = list;
        mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View heroView = inflater.inflate(R.layout.item_cmt, parent, false);
        ViewHolder viewHolder = new ViewHolder(heroView);
        context = parent.getContext();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CommentModel commentModel = list.get(position);

        holder.textStar.setText("" + commentModel.getEvaluate());
        holder.textUser.setText(commentModel.getUserModel().getName());
        holder.textCmt.setText(commentModel.getCmt());
    }

    @Override
    public int getItemCount() {
        Log.d("TRANG","" + list.size());
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textCmt, textStar, textUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textUser = itemView.findViewById(R.id.txt_name);
            textStar = itemView.findViewById(R.id.txt_Evaluate1);
            textCmt = itemView.findViewById(R.id.txt_cmt);
        }
    }
}
