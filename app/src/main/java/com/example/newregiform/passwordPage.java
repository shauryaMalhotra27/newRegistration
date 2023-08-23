package com.example.newregiform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class passwordPage extends AppCompatActivity {

    TextView namePassTV, agePassTV, genderPassTV, emailPassTV;

    EditText pass1ET, pass2ET;
    Button changeBtn, registerBtn;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_page);

        mAuth = FirebaseAuth.getInstance();
        namePassTV = findViewById(R.id.namePassTV);
        agePassTV = findViewById(R.id.agePassTV);
        genderPassTV = findViewById(R.id.genderPassTV);
        emailPassTV = findViewById(R.id.emailPassTV);
        pass1ET = findViewById(R.id.pass1ET);
        pass2ET = findViewById(R.id.pass2ET);


        Intent j = getIntent();

        String nameShow = j.getStringExtra("NAME");
        namePassTV.setText(nameShow);

        String ageShow = j.getStringExtra("AGE");
        agePassTV.setText(ageShow);

        String genderShow = j.getStringExtra("GENDER");
        genderPassTV.setText(genderShow);

        String emailShow = j.getStringExtra("EMAIL");
        emailPassTV.setText(emailShow);

        changeBtn = findViewById(R.id.changeBtn);
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), registerPage.class);
                startActivity(i);
                finish();
            }
        });

        registerBtn = findViewById(R.id.registerBtn);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password1, password2;

                email = emailPassTV.getText().toString();
                password1 = pass1ET.getText().toString();
                password2 = pass2ET.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(passwordPage.this, "Enter Email !", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password1)){
                    Toast.makeText(passwordPage.this, "Password is empty !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(password2)){
                    Toast.makeText(passwordPage.this, "Confirm Password Empty !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password1.equals(password2)){
                    Toast.makeText(passwordPage.this, "type same password !", Toast.LENGTH_SHORT).show();
                    return;
                }


                mAuth.createUserWithEmailAndPassword(email, password1)
                        .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    // If sign in successful, display a message to the user.
                                    Toast.makeText(passwordPage.this, "Account Created .",
                                            Toast.LENGTH_SHORT).show();

                                    Intent i = new Intent(getApplicationContext(), loginPage.class);
                                    startActivity(i);

                                } else {
                                    // If sign in fails, display a message to the user.
                                     Toast.makeText(passwordPage.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();

                                }
                            }
                        });



            }
        });

    }
}