package com.example.chatx.fragments.Chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chatx.R;
import com.example.chatx.ui.User;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserListRecyclerAdapter extends RecyclerView.Adapter<UserListRecyclerAdapter.ViewHolder> {
    private final Context context;
    private ArrayList<User> userArrayList;

    public UserListRecyclerAdapter(Context context, ArrayList<User> userArrayList) {
        this.context = context;
        this.userArrayList = userArrayList;
        Log.d("size", "1"+userArrayList.size());
    }

    @NonNull
    @Override
    public UserListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.users_card_view, parent, false);
        Log.d("size", ""+userArrayList.size());
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Toast.makeText(context.getApplicationContext(), userArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
        holder.userName.setText(userArrayList.get(position).getName());
        if(!userArrayList.get(position).getProfileImage().equals("NO IMAGE")){
            Glide.with(context).load(userArrayList.get(position).getProfileImage()).into(holder.profileImg);
        }
    }


    @Override
    public int getItemCount() {
        Log.d("size","inside getItemCount " + userArrayList.size());
        return userArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView userName;
        CircleImageView profileImg;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userNameInUserCardView);
            profileImg = itemView.findViewById(R.id.userProfileImageInUserCardView);
            Log.d("size", userName.getText().toString());

        }
    }
}
