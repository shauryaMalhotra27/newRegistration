package com.example.newregiform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class passwordPage extends AppCompatActivity {

    TextView namePassTV, agePassTV, genderPassTV, emailPassTV;

    EditText pass1ET, pass2ET;
    TextView registerBtn, passBackBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore db;


    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_page);

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);


        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        namePassTV = findViewById(R.id.namePassTV);
        agePassTV = findViewById(R.id.agePassTV);
        genderPassTV = findViewById(R.id.genderPassTV);
        emailPassTV = findViewById(R.id.emailPassTV);
        pass1ET = findViewById(R.id.pass1ET);
        pass2ET = findViewById(R.id.pass2ET);
        passBackBtn = findViewById(R.id.passBackBtn);


        Intent j = getIntent();

        String nameShow = j.getStringExtra("NAME");
        namePassTV.setText(nameShow);

        String ageShow = j.getStringExtra("AGE");
        agePassTV.setText(ageShow);

        String genderShow = j.getStringExtra("GENDER");
        genderPassTV.setText(genderShow);

        String emailShow = j.getStringExtra("EMAIL");
        emailPassTV.setText(emailShow);


        passBackBtn.setOnClickListener(new View.OnClickListener() {
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

                String email, password1, password2, firstName, age, gender;

                firstName = namePassTV.getText().toString().trim();
                age = agePassTV.getText().toString().trim();
                gender = genderPassTV.getText().toString().trim();
                email = emailPassTV.getText().toString().trim();
                password1 = pass1ET.getText().toString().trim();
                password2 = pass2ET.getText().toString().trim();

                Map<String, Object> user = new HashMap<>();
                user.put("firstName", firstName);
                user.put("age", age);
                user.put("gender", gender);
                user.put("email", email);

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(passwordPage.this, "Enter Email !", Toast.LENGTH_SHORT).show();
                    return;

                }
                if (TextUtils.isEmpty(password1)) {
                    Toast.makeText(passwordPage.this, "Password is empty !", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (TextUtils.isEmpty(password2)) {
                    Toast.makeText(passwordPage.this, "Confirm Password Empty !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password1.equals(password2)) {
                    Toast.makeText(passwordPage.this, "type same password !", Toast.LENGTH_SHORT).show();
                    return;
                }


                db.collection("users")
                        .add(user)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(passwordPage.this, "data uploaded !", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(passwordPage.this, "data error !", Toast.LENGTH_SHORT).show();
                            }
                        });

                mAuth.createUserWithEmailAndPassword(email, password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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