package com.example.ulesa.ui.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ulesa.R;

public class ImageFragment extends Fragment {
    private View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.item_image_pager, container, false);
        Bundle bundle = getArguments();
        String image = (String) bundle.get("image_url");
        ImageView imageView = mView.findViewById(R.id.image_detail);
        Glide.with(this).load(image).into(imageView);
        return mView;
    }
}
