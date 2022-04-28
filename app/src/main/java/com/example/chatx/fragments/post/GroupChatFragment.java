package com.example.chatx.fragments.post;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatx.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class GroupChatFragment extends Fragment {


    public GroupChatFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextInputLayout textInputLayout = view.findViewById(R.id.mood_edittext_layout);
        textInputLayout.setEndIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postMoodToFirebase(view);
            }
        });


    }

    private void postMoodToFirebase(View view) {
        TextInputEditText moodEdittext = view.findViewById(R.id.mood_edittext);
        String text = Objects.requireNonNull(moodEdittext.getText()).toString();
        if(text.equals("")){
            Toast.makeText(view.getContext(), "Please enter something...", Toast.LENGTH_SHORT).show();
            return ;
        }

        PostFormat postFormat = new PostFormat(text);
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseDatabase.getReference().child("posts/"+ firebaseUser.getPhoneNumber() + "/").setValue(postFormat).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(view.getContext(), "Successfully posted", Toast.LENGTH_SHORT).show();
                moodEdittext.setText("");
                moodEdittext.clearFocus();
            }
        });
    }
}