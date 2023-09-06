package com.example.newregiform;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingPage extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;

    private String TAG = "SettingPage";
    private TextView editProfile, textMsg, logoutSettingBtn, deleteSettingBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);


        editProfile = findViewById(R.id.editProfile);
        logoutSettingBtn = findViewById(R.id.logoutSettingBtn);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        user = FirebaseAuth.getInstance().getCurrentUser();


        deleteSettingBtn = findViewById(R.id.deleteSettingBtn);

        deleteSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                user.delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Log.d(TAG, "User account deleted.");
                                }
                            }
                        });

            }
        });




        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), EditPageForUser.class);
                startActivity(i);

            }
        });

        logoutSettingBtn.setOnClickListener(new View.OnClickListener() {
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
}