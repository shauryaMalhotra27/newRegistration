package com.example.newregiform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {


    TextView showDetailsMainTV;
    Button logoutMainBtn;

    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);


        showDetailsMainTV = findViewById(R.id.showDetailsMainTV);
        logoutMainBtn = findViewById(R.id.logoutMainBtn);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if(user == null){
            Intent i = new Intent(getApplicationContext(), loginPage.class);
            startActivity(i);
            finish();
        }

        else {
            showDetailsMainTV.setText(user.getEmail());
        }

        logoutMainBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getApplicationContext(), loginPage.class);
                startActivity(i);
                finish();
            }
        });




    }
}