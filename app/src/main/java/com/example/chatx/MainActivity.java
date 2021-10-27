package com.example.chatx;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatx.fragments.Chat;
import com.example.chatx.fragments.Profile_fragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.chatx.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        FragmentTransaction chatPageTrans = getSupportFragmentManager().beginTransaction();
        chatPageTrans.replace(R.id.fragment_content, new Chat());
        chatPageTrans.commit();
        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_chat:
                        FragmentTransaction chatPageTrans = getSupportFragmentManager().beginTransaction();
                        chatPageTrans.replace(R.id.fragment_content, new Chat());
                        chatPageTrans.commit();
//                        Toast.makeText(MainActivity.this, "selected chat option", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_group_chat:
                        Toast.makeText(MainActivity.this, "selected Group chat option", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_file_sharing:
                        Toast.makeText(MainActivity.this, "Selected file sharing option", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_phone_call:
                        Toast.makeText(MainActivity.this, "Selected phone call option", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_account_details:
                        FragmentTransaction profilePageTrans = getSupportFragmentManager().beginTransaction();
                        profilePageTrans.replace(R.id.fragment_content, new Profile_fragment());
                        profilePageTrans.commit();
//                        Toast.makeText(MainActivity.this, "selected account details option", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

}