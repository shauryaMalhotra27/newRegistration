package com.example.newregiform;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
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

import java.util.HashMap;
import java.util.Map;

public class EditPageForUser extends AppCompatActivity {
    String[] items = {"Male", "Female", "None"};
    TextView firstNameField, ageField, genderField;
    Button updateUser;
    AutoCompleteTextView autoCompleteTextView;
    ArrayAdapter<String> adapterItems;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_page_for_user);

        Window g = getWindow();
        g.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);


        Toolbar toolbar = findViewById(R.id.toolBar);
        toolbar.setTitle("update details");
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_keyboard);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        autoCompleteTextView = findViewById(R.id.autoCompleteTV);

        adapterItems = new ArrayAdapter<String>(this, R.layout.list_item, items);
        autoCompleteTextView.setAdapter(adapterItems);

        firstNameField = findViewById(R.id.outlinedNameField);
        ageField = findViewById(R.id.outlinedAgeField);
        genderField = findViewById(R.id.autoCompleteTV);
        updateUser = findViewById(R.id.updateUserBTN);
        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String emailDb = auth.getCurrentUser().getEmail();

                String name = firstNameField.getText().toString();
                String age = ageField.getText().toString();
                String gender = genderField.getText().toString();

                updateDataInDB( name, age, gender);

                firstNameField.setText("");
                ageField.setText("");
                genderField.setText("");


            }
        });


    }

    private void updateDataInDB(  String name, String age, String gender) {


        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("firstName", name);
        userDetails.put("gender", gender);
        userDetails.put("age", age);


        db.collection("users")

                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful() && !task.getResult().isEmpty()) {

                            DocumentSnapshot document = task.getResult().getDocuments().get(0);
                            String documentID = document.getId();
                            db.collection("users")
                                    .document(documentID)
                                    .update(userDetails)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Toast.makeText(EditPageForUser.this, "successfuly updated", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Toast.makeText(EditPageForUser.this, "not updated", Toast.LENGTH_SHORT).show();

                                        }
                                    });


                        } else {
                            Toast.makeText(EditPageForUser.this, "ERROR !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }


}