package com.example.ulesa.ui.useraccount;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.ulesa.LoginActivity;
import com.example.ulesa.R;
import com.example.ulesa.data.DataBaseHelper;
import com.example.ulesa.databinding.FragmentUserBinding;
import com.example.ulesa.ui.user.EditActivity;
import com.example.ulesa.ui.user.HelpActivity;
import com.example.ulesa.ui.user.RePasswordActivity;
import com.example.ulesa.ui.user.RulesActivity;


public class UserAccountFragment extends Fragment {

    private FragmentUserBinding binding;
    private TextView textRules, textRepass, textHelp,textDeleteUser,textLogout, textName;
    private LinearLayout helpLayout,repasslayout,ruleslayout, logoutLayout;
    private Button btnEit;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        UserAccountViewModel notificationsViewModel =
                new ViewModelProvider(this).get(UserAccountViewModel.class);

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        textRules = root.findViewById(R.id.text_rules);
        textRepass = root.findViewById(R.id.text_repass);
        textHelp = root.findViewById(R.id.text_help);
        textDeleteUser = root.findViewById(R.id.text_delete_user);
        textLogout = root.findViewById(R.id.text_logout);
        btnEit = root.findViewById(R.id.btn_edit);
        helpLayout = root.findViewById(R.id.layout_help);
        repasslayout = root.findViewById(R.id.layout_repass);
        ruleslayout = root.findViewById(R.id.layout_rules);
        logoutLayout = root.findViewById(R.id.layout_logout);
        textName = root.findViewById(R.id.text_name);
        String name = DataBaseHelper.getName(root.getContext());
        textName.setText("User : " + name);
        repasslayout.setOnClickListener(v -> {
                textRepass.performLongClick();
        });
        helpLayout.setOnClickListener(v -> {
            textHelp.performLongClick();
        });
        ruleslayout.setOnClickListener(v -> {
            textRules.performLongClick();
        });
        textRepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG", " textHelp.setOnClickListener");
               startActivity((new Intent(root.getContext(), RePasswordActivity.class)));
            }
        });
        textHelp.setOnClickListener(v ->{
            Log.d("TRANG", " textHelp.setOnClickListener");
            startActivity(new Intent(root.getContext(), HelpActivity.class));
        });
        btnEit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG", "btnEit.setOnClickListener");
                startActivity(new Intent(root.getContext(), EditActivity.class));
            }
        });
        textRules.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TRANG","textRules.setOnClickListener");
                startActivity(new Intent(root.getContext(),RulesActivity.class));
            }
        });
        logoutLayout.setOnClickListener(v -> {
            DataBaseHelper.xoasTK(root.getContext());
            startActivity(new Intent(root.getContext(), LoginActivity.class));
            getActivity().finish();
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}