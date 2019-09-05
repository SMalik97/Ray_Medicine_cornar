package com.example.ray_medicals;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Services_page extends Fragment {
    LinearLayout doctors,labtest,household,healthrecode;
    View view;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.bottom_services,container,false);

        doctors=(LinearLayout)view.findViewById(R.id.doctor);
        labtest=(LinearLayout)view.findViewById(R.id.labtest);
        household=(LinearLayout)view.findViewById(R.id.household);
        healthrecode=(LinearLayout)view.findViewById(R.id.healthrecode);

        doctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","doctor");
                startActivity(i);
            }
        });

        labtest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","lab");
                startActivity(i);
            }
        });

        household.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","house");
                startActivity(i);
            }
        });

        healthrecode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","health");
                startActivity(i);
            }
        });


        return view;
    }
}
