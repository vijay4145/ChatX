package com.example.chatx.fragments.message_page;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chatx.R;

import java.util.ArrayList;

public class MessagesRecyclerAdapter extends RecyclerView.Adapter<MessagesRecyclerAdapter.ViewHolder> {
    Context context;
    ArrayList<String> messaages;

    public MessagesRecyclerAdapter(Context context, ArrayList<String> messsages) {
        this.context = context;
        this.messaages = messsages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.message_card_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(messaages.get(position).charAt(0) == '0') { //i.e from receiver side
            holder.msgSenderSidTextView.setVisibility(View.INVISIBLE);
            holder.msgReceiverSideTextView.setVisibility(View.VISIBLE);
            holder.msgReceiverSideTextView.setText(messaages.get(position).substring(1));

        }else{
            holder.msgReceiverSideTextView.setVisibility(View.INVISIBLE);
            holder.msgSenderSidTextView.setText(messaages.get(position).substring(1));
            holder.msgSenderSidTextView.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return messaages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView msgSenderSidTextView;
        TextView msgReceiverSideTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            msgSenderSidTextView = itemView.findViewById(R.id.messages_frm_sender_in_message_card_view);
            msgReceiverSideTextView = itemView.findViewById(R.id.messages_frm_receiver_in_message_card_view);
        }
    }
}
