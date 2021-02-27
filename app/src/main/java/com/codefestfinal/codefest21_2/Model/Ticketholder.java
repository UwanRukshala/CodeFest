package com.codefestfinal.codefest21_2.Model;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.codefestfinal.codefest21_2.R;


public class Ticketholder extends RecyclerView.ViewHolder {
    public TextView title;
    public TextView tb;
    public TextView type;





    public Ticketholder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.tt);
        tb = itemView.findViewById(R.id.tb);
        type = itemView.findViewById(R.id.ttype);


    }
}
