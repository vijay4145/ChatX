package com.example.chatx.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatx.MainActivity;
import com.example.chatx.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class Profile_fragment extends Fragment{
    DatabaseReference mDataBase;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SharedPreferences shrd = Objects.requireNonNull(requireContext().getSharedPreferences("UserProfile", Context.MODE_PRIVATE));
        String userNameString = shrd.getString("USERNAME","NO NAME");
        if(MainActivity.profilePhotoUri != null) {
            ImageView profileImg = view.findViewById(R.id.profile_pic_in_profile_fragment);
            profileImg.setImageURI(MainActivity.profilePhotoUri);
        }

        TextView userName = view.findViewById(R.id.userName);
        userName.setText(userNameString);
    }
}