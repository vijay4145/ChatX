package com.example.chatx.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.chatx.MainActivity;
import com.example.chatx.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
                EditText name = findViewById(R.id.userName);
                String userName = name.getText().toString();
                Toast.makeText(set_user_profile.this, "clicked", Toast.LENGTH_SHORT).show();
                if(selectedImageUri != null || userName != null){
                    dialog.setMessage("Setting Profile....");
                    dialog.show();
                    StorageReference reference = firebaseStorage.getReference().child("profiles").child(auth.getUid());
                    reference.putFile(selectedImageUri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageUrl = uri.toString();
                                        String uid = auth.getUid();
                                        String phone = auth.getCurrentUser().getPhoneNumber();
                                        User user = new User(uid, userName, phone, imageUrl);
                                        MainActivity.userDetails = user;
                                        MainActivity.profilePhotoUri = selectedImageUri;
                                        firebaseDatabase.getReference().child("users").setValue(user)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void unused) {
                                                        dialog.dismiss();
                                                        Intent intent = new Intent(set_user_profile.this, MainActivity.class);
                                                        startActivity(intent);
                                                    }
                                                });
                                    }
                                });
                            }
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