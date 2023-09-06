package com.example.newregiform;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class AdminRecyclerAdapter extends RecyclerView.Adapter<AdminRecyclerAdapter.ViewHolder> {

    private final String TAG = "AdminRecyclerAdapter";
    ArrayList<AdminModel> dataEmail;

    FirebaseUser users;


    AdminRecyclerAdapter(ArrayList<AdminModel> dataEmail) {
        this.dataEmail = dataEmail;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_card_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.showEmail.setText(dataEmail.get(position).getEmail());




//                users = FirebaseAuth.getInstance().getCurrentUser();
//
//                Log.d("user", "users "+dataEmail.get(position).getId());
//
//
//                if(dataEmail.get(position).getId() != null){
//
//                    Log.d("user in if", "checking user in if " );
//                    users.delete()
//                            .addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//
//
//                            if(task.isSuccessful()){
//                                String userEmailToDelete = dataEmail.get(position).getEmail();
//
//                                removeFirestoreData(userEmailToDelete);
//                            }
//                            else {
//                                Toast.makeText(view.getContext(), "failed to delete !", Toast.LENGTH_SHORT).show();
//                            }
//
//                            Log.d("check", "onComplete: " + dataEmail);
//
//                        }
//                    });
//                }

            }

    @Override
    public int getItemCount() {
        return dataEmail.size();
    }

    public void removeFirestoreData(String userEmailToDelete) {
        Log.d(TAG, "removeFirestoreData: ");

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(userEmailToDelete)
                .delete()
                .addOnSuccessListener(unused -> {
                    Log.d(TAG, "onSuccess: Deleted");

                    dataEmail.remove(userEmailToDelete);
                    notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Log.d("remove", "failed to delete FDB"));

    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        private final TextView showEmail;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            showEmail = itemView.findViewById(R.id.cardEmailText);


        }
    }
}
