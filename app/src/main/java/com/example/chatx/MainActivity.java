package com.example.chatx;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.chatx.fragments.Chat.Chat;
import com.example.chatx.fragments.post.GroupChatFragment;
import com.example.chatx.fragments.Profile_fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;


import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("chatX1", "Loaded MainActivity");
//        BottomNavigationView bottomNav = findViewById(R.id.bottom_nav);

//        FragmentTransaction chatPageTrans = getSupportFragmentManager().beginTransaction();
//        chatPageTrans.replace(R.id.fragment_content, new Chat());
//        chatPageTrans.commit();
//
//        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.navigation_chat:
//                        FragmentTransaction chatPageTrans = getSupportFragmentManager().beginTransaction();
//                        chatPageTrans.replace(R.id.fragment_content, new Chat());
//                        chatPageTrans.commit();
//                        break;
//                    case R.id.navigation_group_chat:
//                        Toast.makeText(MainActivity.this, "selected Group chat option", Toast.LENGTH_SHORT).show();
//                        break;
//                    case R.id.navigation_account_details:
//                        FragmentTransaction profilePageTrans = getSupportFragmentManager().beginTransaction();
//                        profilePageTrans.replace(R.id.fragment_content, new Profile_fragment());
//                        profilePageTrans.commit();
//                        break;
//                }
//                return true;
//            }
//        });
//        }

        BubbleNavigationConstraintView bubbleNavigation = findViewById(R.id.bottom_nav);
        FragmentTransaction chatPageTrans = getSupportFragmentManager().beginTransaction();
        chatPageTrans.replace(R.id.fragment_content, new Chat());
        chatPageTrans.commit();

        bubbleNavigation.setNavigationChangeListener(new BubbleNavigationChangeListener() {
            @Override
            public void onNavigationChanged(View view, int position) {
                switch (position){
                    case 0:
                        FragmentTransaction chatPageTrans = getSupportFragmentManager().beginTransaction();
                        chatPageTrans.replace(R.id.fragment_content, new Chat());
                        chatPageTrans.commit();
                        break;
                    case 1:
                        FragmentTransaction groupChatTrans = getSupportFragmentManager().beginTransaction();
                        groupChatTrans.replace(R.id.fragment_content, new GroupChatFragment());
                        groupChatTrans.commit();
                        break;
                    case 2:
                        FragmentTransaction profilePageTrans = getSupportFragmentManager().beginTransaction();
                        profilePageTrans.replace(R.id.fragment_content, new Profile_fragment());
                        profilePageTrans.commit();
                        
                }
            }
        });
//        BubbleToggleView bubbleToggleView = findViewById(R.id.group_chat);
//        bubbleToggleView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                bubbleToggleView.setActivated(true);
//            }
//        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}