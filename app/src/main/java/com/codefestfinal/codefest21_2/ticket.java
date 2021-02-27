package com.codefestfinal.codefest21_2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefestfinal.codefest21_2.Model.Tick;
import com.codefestfinal.codefest21_2.Model.Ticketholder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class ticket extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);

        RecyclerView rec = findViewById(R.id.rv);

        Query loadnews = db.collection("Tickets");

        FirestoreRecyclerOptions firestoreRecyclerOptions =
                new FirestoreRecyclerOptions.Builder<Tick>().setQuery(loadnews,Tick.class).build();
        //adpater set

        firestoreRecyclerAdapter = new FirestoreRecyclerAdapter<Tick, Ticketholder>(firestoreRecyclerOptions) {

            @NonNull
            @Override
            public Ticketholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ti,parent,false);
                return new Ticketholder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Ticketholder holder, int position, @NonNull Tick model) {
                holder.title.setText(model.getTitle());
                holder.tb.setText(model.getBody());
                holder.type.setText(model.getType());

            }
        };
        recyclerView.setAdapter(firestoreRecyclerAdapter);
    }
}