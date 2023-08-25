package com.example.newregiform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.DateFormatSymbols;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class registerPage extends AppCompatActivity {


    EditText nameRegiET, ageRegiET, emailRegiET;
    Spinner  genderRegiET;
    TextView goToPassBtn, goToLoginBtnRegi;

    String[] gender;


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


        populateSpinner();

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
                String gender1 = genderRegiET.getSelectedItem().toString();
                String email1 = emailRegiET.getText().toString().trim();

                if (name1.isEmpty() || age1.isEmpty() || gender1.isEmpty() || email1.isEmpty()) {

                    warnToast();

                } else {
                    Intent i = new Intent(getApplicationContext(), passwordPage.class);

                    i.putExtra("NAME", name1);
                    i.putExtra("AGE", age1);
                    i.putExtra("GENDER", gender1);
                    i.putExtra("EMAIL", email1);

                    startActivity(i);

                }

            }
        });


    }

    public void warnToast() {
        Toast toastWarn = new Toast(getApplicationContext());
        View warnView = getLayoutInflater().inflate(R.layout.custom_toast_yellow, (ViewGroup) findViewById(R.id.warningLayout));
        toastWarn.setView(warnView);
        TextView textMsg = warnView.findViewById(R.id.textYellow);
        textMsg.setText("FILL ALL DETAILS");
        toastWarn.setDuration(Toast.LENGTH_SHORT);
        toastWarn.setGravity(Gravity.TOP, 0, 30);
        toastWarn.show();

    }

    private void populateSpinner() {

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.gender, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderRegiET.setAdapter(adapter);



    }
}