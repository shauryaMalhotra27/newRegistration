package com.example.newregiform;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class SettingPage extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    FirebaseFirestore db;

    private String TAG = "SettingPage";
    private TextView editProfile, textMsg, logoutSettingBtn, deleteSettingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);

        Toolbar toolbar = findViewById(R.id.toolBar2);
        toolbar.setTitle("settings");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_keyboard);


        //user = auth.getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();
        editProfile = findViewById(R.id.editProfile);
        logoutSettingBtn = findViewById(R.id.logoutSettingBtn);
        auth = FirebaseAuth.getInstance();
        deleteSettingBtn = findViewById(R.id.deleteSettingBtn);
        db = FirebaseFirestore.getInstance();

        deleteSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(SettingPage.this);
                dialog.setContentView(R.layout.setting_delete_dialouge_box);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                TextView deleteCancel = dialog.findViewById(R.id.deleteCancelDB);
                TextView deleteOk = dialog.findViewById(R.id.deleteOkDB);

                deleteCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();


                    }
                });

                deleteOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        delteDataFromFDB();

                        user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()) {
                                    Toast.makeText(SettingPage.this, "account deleted", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getApplicationContext(), loginPage.class);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(i);


                                } else {
                                    Toast.makeText(SettingPage.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });


                    }
                });

                dialog.show();

            }
        });


        logoutSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog2 = new Dialog(SettingPage.this);
                dialog2.setContentView(R.layout.setting_dialouge_box_logout);
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.setCancelable(false);

                TextView logoutCancel = dialog2.findViewById(R.id.logoutCancelDB);
                TextView logoutOk = dialog2.findViewById(R.id.logoutOkDB);

                logoutCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog2.dismiss();
                    }
                });

                logoutOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        warnToast();
                        textMsg.setText("USER LOGGGED OUT !");

                        FirebaseAuth.getInstance().signOut();
                        Intent i = new Intent(getApplicationContext(), loginPage.class);
                        startActivity(i);
                        finish();

                    }
                });


                dialog2.show();


            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), EditPageForUser.class);
                startActivity(i);

            }
        });

    }


    public void warnToast() {
        Toast toastWarn = new Toast(getApplicationContext());
        View warnView = getLayoutInflater().inflate(R.layout.custom_toast_yellow, (ViewGroup) findViewById(R.id.warningLayout));
        toastWarn.setView(warnView);
        textMsg = warnView.findViewById(R.id.textYellow);
        textMsg.setText("EMAIL OR PASSWORD MISSING");
        toastWarn.setDuration(Toast.LENGTH_SHORT);
        toastWarn.setGravity(Gravity.TOP, 0, 30);
        toastWarn.show();

    }

    public void delteDataFromFDB() {

        db.collection("users")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {

                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String documentId = document.getId();
                            db.collection("users")
                                    .document(documentId)
                                    .delete()
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                            Toast.makeText(SettingPage.this, "data also deleted from FDB", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SettingPage.this, "some error deleting data from FDB", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                        } else {
                            Toast.makeText(SettingPage.this, "FAILED", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }

}