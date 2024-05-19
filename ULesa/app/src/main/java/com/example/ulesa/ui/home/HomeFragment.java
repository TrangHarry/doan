package com.example.ulesa.ui.home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.databinding.FragmentHomeBinding;
import com.example.ulesa.model.HomeModel;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView homeRecy;
    private ArrayList<HomeModel> listData;
    private HomeAdapter homeAdapter;
    private DataBaseHelper dataBaseHelper;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        homeRecy = binding.homeRecy;
        dataBaseHelper = new DataBaseHelper();
        listData = dataBaseHelper.getDataHome(root.getContext());
        homeAdapter = new HomeAdapter(listData, root.getContext());
        homeRecy.setLayoutManager(new LinearLayoutManager(root.getContext()));
        homeRecy.setAdapter(homeAdapter);
        IntentFilter filter = new IntentFilter("com.example.appulesa.DATA_DONE");
        root.getContext().registerReceiver(receiver, filter, Context.RECEIVER_EXPORTED);

        return root;
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("com.example.appulesa.DATA_DONE")) {
//                listData = dataBaseHelper.getDataHome(getContext());
                homeAdapter.notifyDataSetChanged();
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