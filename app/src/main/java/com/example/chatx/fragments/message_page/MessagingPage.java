package com.example.chatx.fragments.message_page;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;


import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatx.MsgDatabase;
import com.example.chatx.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class MessagingPage extends AppCompatActivity {
    String name, receiverPhoneNumber,userProfileLink, senderPhoneNumber;
    private DatabaseReference mDatabase;
    ArrayList<String> previousMessageWithThisUser;
    private MsgDatabase msgDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging_page);
        SharedPreferences shrd = getSharedPreferences("UserProfile", MODE_PRIVATE);
        senderPhoneNumber = shrd.getString("USERNUMBER", "NO NUMBER");

        Intent intent = getIntent();
        name = intent.getStringExtra("userName");
        receiverPhoneNumber = intent.getStringExtra("userPhoneNumber");
        userProfileLink = intent.getStringExtra("userProfile");
        mDatabase = FirebaseDatabase.getInstance().getReference();



        TextView userNameTextView = findViewById(R.id.userName);
        userNameTextView.setText(name);

        setBackButton();
        setProfilePic();

        msgDatabase = new MsgDatabase(getApplicationContext());
        previousMessageWithThisUser = msgDatabase.getMessage(receiverPhoneNumber);

        RecyclerView messageRecyclerView = findViewById(R.id.messages_in_recyclerView_messagingPage);
        MessagesRecyclerAdapter messagesRecyclerAdapter = new MessagesRecyclerAdapter(this, previousMessageWithThisUser);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        messageRecyclerView.setLayoutManager(linearLayoutManager);
        messageRecyclerView.setAdapter(messagesRecyclerAdapter);

        listenMessageFromFireBase(messagesRecyclerAdapter);
        ImageButton sendButton = findViewById(R.id.send_button_in_activity_messaging_page);
        bindSendButton(sendButton,messagesRecyclerAdapter);
    }

    private void setProfilePic() {
        CircleImageView profilePic = findViewById(R.id.profile_image);
        Glide.with(MessagingPage.this).load(userProfileLink)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(profilePic);
    }

    private void setBackButton() {
        ImageButton backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSupportNavigateUp();
            }
        });
    }

    private void listenMessageFromFireBase(MessagesRecyclerAdapter messagesRecyclerAdapter) {
        if(senderPhoneNumber.equals(receiverPhoneNumber)) return;
        mDatabase.child("messages").child(receiverPhoneNumber).child(senderPhoneNumber).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String newMessage = snapshot.getValue(String.class);
                if(newMessage != null && !newMessage.equals("")) {
                    snapshot.getRef().removeValue().addOnSuccessListener(unused -> {
                        previousMessageWithThisUser.add("0"+newMessage);
                        msgDatabase.addMessage(receiverPhoneNumber, "0"+newMessage);
                        messagesRecyclerAdapter.notifyItemInserted(previousMessageWithThisUser.size()-1);
                    });

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void bindSendButton(ImageButton sendButton, MessagesRecyclerAdapter messagesRecyclerAdapter) {
        EditText messageText = findViewById(R.id.message_to_send_in_activity_messaging_page);
        sendButton.setOnClickListener(v -> {
            String message = messageText.getText().toString();
            previousMessageWithThisUser.add("1"+message);
            messagesRecyclerAdapter.notifyItemInserted(previousMessageWithThisUser.size()-1);
            msgDatabase.addMessage(receiverPhoneNumber, "1"+message);
            pushMessageToFirebase(senderPhoneNumber, receiverPhoneNumber, message);
            messageText.setText(null);
        });
    }

    private void pushMessageToFirebase(String senderNumber, String receiverPhoneNumber, String message) {
        mDatabase.child("messages").child(senderNumber).child(receiverPhoneNumber).setValue(message);
    }


    @Override
    public boolean onSupportNavigateUp() {
        //saveToDatabaseWhenExitedFromThisChat
        msgDatabase.close();
        finish();
        return super.onSupportNavigateUp();
    }
}