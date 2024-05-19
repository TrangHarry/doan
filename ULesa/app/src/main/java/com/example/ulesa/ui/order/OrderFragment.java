package com.example.ulesa.ui.order;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.databinding.FragmentYourOrderBinding;
import com.example.ulesa.model.OrderModel;

import java.util.ArrayList;

public class OrderFragment extends Fragment {

    private FragmentYourOrderBinding binding;
    private RecyclerView orderRecy;
    private ArrayList<OrderModel> listData;
    private DataBaseHelper dataBaseHelper;
    private OrderAdapter orderAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        OrderViewModel homeViewModel =
                new ViewModelProvider(this).get(OrderViewModel.class);

        binding = FragmentYourOrderBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        orderRecy = binding.recyOrder;
        dataBaseHelper = new DataBaseHelper();
        listData = dataBaseHelper.getDataOrder(root.getContext());
        orderAdapter = new OrderAdapter(listData, root.getContext());

        orderRecy.setLayoutManager(new LinearLayoutManager(root.getContext()));

        orderRecy.setAdapter(orderAdapter);
        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        filter.addAction("com.example.appulesa.DATA_DONE2");
        root.getContext().registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);
        return root;
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d("TRANG", "------------" + action);
            if (action.equals("com.example.appulesa.DATA_DONE")) {
                listData = dataBaseHelper.getListOder();
                orderAdapter.notifyDataSetChanged();
            }
            if (action.equals("com.example.appulesa.DATA_DONE2")) {
                int delete = orderAdapter.getDelete();
                if (delete != -1 && listData.size() != 0) {
                    listData.remove(delete);
                    orderAdapter.notifyDataSetChanged();
                    orderAdapter.reloadDelete();
                }
            }
        }

    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        getContext().unregisterReceiver(receiver);
    }
}