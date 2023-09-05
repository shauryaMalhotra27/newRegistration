package com.example.newregiform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingPage extends AppCompatActivity {

    private TextView editProfile, textMsg;
    private Button logoutSettingBtn;

    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_page);

        editProfile = findViewById(R.id.editProfile);
        logoutSettingBtn = findViewById(R.id.logoutSettingBtn);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

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