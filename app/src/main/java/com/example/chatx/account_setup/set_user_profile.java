package com.example.chatx.account_setup;


import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatx.MainActivity;
import com.example.chatx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class set_user_profile extends AppCompatActivity {
    public static final int GALLERY_REQ_CODE = 1234;
    FirebaseAuth auth;
    FirebaseDatabase firebaseDatabase;
    FirebaseStorage firebaseStorage;
    Uri selectedImageUri;
    ActivityResultLauncher<Intent> intentLauncher;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        setContentView(R.layout.activity_set_user_profile);
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        CircleImageView profilePic = findViewById(R.id.profile_pic);
        intentLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        selectedImageUri = data.getData();
                        profilePic.setImageURI(data.getData());
                    }
                }
        );
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button continueBtn = findViewById(R.id.continue_button_in_set_user_profile);
        continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.setMessage("Setting Profile....");
                EditText name = findViewById(R.id.userName);
                String userName = name.getText().toString();
                Toast.makeText(set_user_profile.this, "clicked", Toast.LENGTH_SHORT).show();
                Log.d("chatX1", userName);
                if(selectedImageUri != null && !userName.equals("")){
                    Log.d("chatX1", userName);
                    dialog.show();
                    StorageReference reference = firebaseStorage.getReference().child("profiles").child(auth.getUid());
                    reference.putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Log.d("chatX1", "succesfully pushed to firebase");
                                        String imageUrl = uri.toString();
                                        String uid = auth.getUid();
                                        String phone = auth.getCurrentUser().getPhoneNumber();
                                        SharedPreferences shrd = getSharedPreferences("UserProfile", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = shrd.edit();
                                        editor.putString("USERNAME", userName);
                                        editor.putString("USERPROFILE", imageUrl);
                                        editor.putString("USERNUMBER",phone);
                                        editor.apply();
                                        Log.d("chatX1", "success2");
                                        User user = new User(userName, phone, imageUrl, uid);
                                        firebaseDatabase.getReference().child("users" + "/" + phone).setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialog.dismiss();
                                                        Log.d("chatX1", "success3");
                                                        Intent intent = new Intent(set_user_profile.this, MainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                });
                                    }
                                });
                            }else{
                                Log.d("chatX1", task.toString());
                            }
                        }
                    });
                }
                else if(!userName.equals("")){
                    dialog.show();
                    SharedPreferences shrd = getSharedPreferences("UserProfile", MODE_PRIVATE);
                    SharedPreferences.Editor editor = shrd.edit();
                    editor.putString("USERNAME", userName);
                    editor.apply();
                    String uid = auth.getUid();
                    String phone = auth.getCurrentUser().getPhoneNumber();
                    User user = new User(uid, userName, phone);
//                    MainActivity.userDetails = user;
                    firebaseDatabase.getReference().child("users/" + phone).setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Intent intent = new Intent(set_user_profile.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            });
                }
            }
        });
    }

    private void openGallery(){
        ActivityCompat.requestPermissions(set_user_profile.this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        }, GALLERY_REQ_CODE);

        int result = ContextCompat.checkSelfPermission(set_user_profile.this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if(result == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            intentLauncher.launch(intent);
//            startActivity(intent);
        }
    }

}