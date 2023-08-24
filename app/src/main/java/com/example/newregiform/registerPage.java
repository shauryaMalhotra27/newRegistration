package com.example.newregiform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class registerPage extends AppCompatActivity {


    EditText nameRegiET, ageRegiET, genderRegiET, emailRegiET;
    TextView goToPassBtn, goToLoginBtnRegi  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);
        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);


        //here we called all the editText by their respective ID's
        nameRegiET = findViewById(R.id.nameRegiET);
        ageRegiET = findViewById(R.id.ageRegiET);
        genderRegiET = findViewById(R.id.genderRegiET);
        emailRegiET = findViewById(R.id.emailRegiET);
        goToPassBtn = findViewById(R.id.goToPassBtn);
        goToLoginBtnRegi = findViewById(R.id.goToLoginBtnRegi);





        goToLoginBtnRegi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), loginPage.class);
                startActivity(i);
                finish();
            }
        });
        goToPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name1 = nameRegiET.getText().toString().trim();
                String age1 = ageRegiET.getText().toString().trim();
                String gender1 = genderRegiET.getText().toString().trim();
                String email1 = emailRegiET.getText().toString().trim();

                Intent i = new Intent(getApplicationContext(), passwordPage.class);

                i.putExtra("NAME", name1);
                i.putExtra("AGE", age1);
                i.putExtra("GENDER", gender1);
                i.putExtra("EMAIL", email1);

                startActivity(i);



            }
        });


    }
}