package com.codefestfinal.codefest21_2;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codefestfinal.codefest21_2.Model.Tick;
import com.codefestfinal.codefest21_2.Model.Ticketholder;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class Tickets extends Fragment {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter firestoreRecyclerAdapter;

    public Tickets() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tickets, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView =(RecyclerView) view.findViewById(R.id.rv);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

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
                Log.i("ABC","aas");
            }
        };
        recyclerView.setAdapter(firestoreRecyclerAdapter);
    }

}