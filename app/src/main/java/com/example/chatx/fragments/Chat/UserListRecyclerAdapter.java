package com.example.chatx.fragments.Chat;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatx.R;
import com.example.chatx.fragments.message_page.MessagingPage;
import com.example.chatx.ui.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListRecyclerAdapter extends RecyclerView.Adapter<UserListRecyclerAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<User> userArrayList;

    public UserListRecyclerAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public UserListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.userName.setText(userArrayList.get(position).getName());
        User assignedUser = userArrayList.get(position);
        Glide.with(context).load(userArrayList.get(position)
                .getProfileImage())
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(holder.profileImg);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MessagingPage.class);
                intent.putExtra("userName", assignedUser.getName());
                intent.putExtra("userPhoneNumber",assignedUser.getPhoneNumber());
                intent.putExtra("userProfile", assignedUser.getProfileImage());
                Toast.makeText(context, "clicked "+assignedUser.getName(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView userName;
        CircleImageView profileImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameInUserCardView);
            profileImg = itemView.findViewById(R.id.userProfileImageInUserCardView);
        }


    }
}
