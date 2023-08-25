package com.example.newregiform;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.badge.BadgeUtils;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginPage extends AppCompatActivity {

    TextView goToRegiBtn, loginBtn, adminBtn;

    TextView textMsg;

    EditText emailLogInET, passLogInET;

    FirebaseAuth mAuth;

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
        setContentView(R.layout.activity_login_page);

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);


        emailLogInET = findViewById(R.id.emailLogInET);
        passLogInET = findViewById(R.id.passLogInET);
        loginBtn = findViewById(R.id.loginBtn);
        adminBtn = findViewById(R.id.adminBtn);

        mAuth = FirebaseAuth.getInstance();
        goToRegiBtn = findViewById(R.id.goToRegiBtn);


        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                final Dialog dialog = new Dialog(loginPage.this);
                dialog.setContentView(R.layout.activity_admin_dialouge_box);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                TextView adminLogin = dialog.findViewById(R.id.adminLoginBtn);
                TextView adminCancel = dialog.findViewById(R.id.adminCancelBtn);
                EditText adminPassText = dialog.findViewById(R.id.adminPassText);

                adminCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                adminLogin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String passText = adminPassText.getText().toString();

                        String adminPass = "1234";

                        if (TextUtils.isEmpty(passText)) {
                            warnToast();
                            textMsg.setText("admin pass empty !");
                            return;
                        }

                        if (passText.equals(adminPass)) {
                            successToast();
                            textMsg.setText("WELCOME ADMIN !");
                            Intent i = new Intent(getApplicationContext(), adminPage.class);
                            startActivity(i);
                            finish();
                        } else {

                            adminPassText.setText("");
                            failToast();
                            textMsg.setText("ADMIN LOGIN FAILED !");
                        }

                    }
                });


                dialog.show();
            }
        });

        goToRegiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), registerPage.class);
                startActivity(i);
                finish();
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                email = emailLogInET.getText().toString().trim();
                password = passLogInET.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    warnToast();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    warnToast();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    successToast();

                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                    finish();

                                } else {

                                    failToast();
                                }
                            }
                        });


            }
        });


    }

    public void successToast() {

        Toast toastSuccess = new Toast(getApplicationContext());
        View successView = getLayoutInflater().inflate(R.layout.custom_toast_message_green, (ViewGroup) findViewById(R.id.successLayout));
        toastSuccess.setView(successView);
        textMsg = successView.findViewById(R.id.textGreen);
        textMsg.setText("LOGIN SUCCESSFUL !");
        toastSuccess.setDuration(Toast.LENGTH_SHORT);
        toastSuccess.setGravity(Gravity.TOP, 0, 30);
        toastSuccess.show();


    }

    public void failToast() {

        Toast toastFail = new Toast(getApplicationContext());
        View failView = getLayoutInflater().inflate(R.layout.custom_toast_message_red, (ViewGroup) findViewById(R.id.wrongLayout));
        toastFail.setView(failView);
        textMsg = failView.findViewById(R.id.textRed);
        textMsg.setText("LOGIN FAILED !");
        toastFail.setDuration(Toast.LENGTH_SHORT);
        toastFail.setGravity(Gravity.TOP, 0, 30);
        toastFail.show();


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