package com.codefestfinal.codefest21_2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.codefestfinal.codefest21_2.FCM.FCMClient;
import com.codefestfinal.codefest21_2.Model.Add;
import com.codefestfinal.codefest21_2.Model.User;
import com.codefestfinal.codefest21_2.databinding.FragmentHomeBinding;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class Home extends Fragment {

    FragmentHomeBinding homeBinding;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    MapsFragment mapsFragment;

    public Home() {
        // Required empty public constructor

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapsFragment = (MapsFragment) getFragmentManager().findFragmentById(R.id.fragment);
        ((MainActivity)getActivity()).showToolbar();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return homeBinding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle arguments = getArguments();
        String userName = arguments.getString("userName");
        homeBinding.userName.setText(userName);
        homeBinding.signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        homeBinding.postAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = homeBinding.NFtitle.getText().toString();
                String body = homeBinding.NFbody.getText().toString();

                if (title != null && body != null) {
                    Add add = new Add();

                    add.setTitle(title);
                    add.setBody(body);

                    db.collection("Add").add(add);
                    db.collection("Users").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                            if(documents.size()>0){
                                for(DocumentSnapshot ds:documents){
                                    User user = ds.toObject(User.class);
                                    String fcmToken = user.getFcmToken();

                                    new FCMClient().execute(fcmToken,title, body);
                                }
                            }
                        }
                    });

                }

            }
        });
    }


    public void signOut() {

        AuthUI.getInstance()
                .signOut(getContext())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        getFragmentManager().popBackStack();
                    }
                });

    }

    public void setData(LatLng l) {

        Log.i("ABC", "awa");
        homeBinding.clat.setText(l.latitude + "");
        homeBinding.clongt.setText(l.longitude + "");
    }
}