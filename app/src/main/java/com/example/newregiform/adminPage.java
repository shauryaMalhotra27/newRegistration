package com.example.newregiform;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class adminPage extends AppCompatActivity {


    ArrayList<AdminModel> dataEmail = new ArrayList<>();
    private RecyclerView recyclerView;
    private AdminRecyclerAdapter adapter;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);

        Toolbar toolbar3 =(Toolbar) findViewById(R.id.toolBar3);
        toolbar3.setTitle("admin");
        setSupportActionBar(toolbar3);
        toolbar3.setLogo(R.drawable.admin_icon);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.left_keyboard);


        recyclerView = findViewById(R.id.recyclerViewOfAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        adapter = new AdminRecyclerAdapter(dataEmail);

        recyclerView.setAdapter(adapter);


        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(this, "Fetched successfully", Toast.LENGTH_SHORT).show();

                        for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {

                            Log.d("first", documentSnapshot.getId() + "=>" + documentSnapshot.getData());

                            String emailFDB = documentSnapshot.get("email").toString();
                            String idFDB = documentSnapshot.getId().toString();
                            dataEmail.add(new AdminModel(emailFDB, idFDB));
                            adapter.notifyDataSetChanged();

                        }

                    } else {
                        Toast.makeText(this, "ERROR !", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}