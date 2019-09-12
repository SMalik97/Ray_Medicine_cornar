package com.example.ray_medicals;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomePage extends Fragment {
    View view;
    LinearLayout doctor,obp,medicine,diet;
    CardView babycare,familycare,women,hair;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         view=inflater.inflate(R.layout.activity_home_page,container,false);
         doctor=(LinearLayout)view.findViewById(R.id.doctor);
         obp=(LinearLayout) view.findViewById(R.id.obp);
         medicine=(LinearLayout) view.findViewById(R.id.medicine);
        diet=(LinearLayout) view.findViewById(R.id.diet);
        babycare=(CardView)view.findViewById(R.id.babycare);
        familycare=(CardView)view.findViewById(R.id.familycare);
        women=(CardView)view.findViewById(R.id.women);
        hair=(CardView)view.findViewById(R.id.hair);

         obp.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
//                 Intent i=new Intent(getContext(),doctors.class);
//                 i.putExtra("a","prescription");
//                 startActivity(i);
                 Intent i=new Intent(getContext(),orderby_Prescription.class);
                 startActivity(i);

             }
         });


         doctor.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i=new Intent(getContext(),doctors.class);
                 i.putExtra("a","doctor");
                 startActivity(i);
             }
         });

         medicine.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 Intent i=new Intent(getContext(),doctors.class);
                 i.putExtra("a","medicine");
                 startActivity(i);
             }
         });

        diet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","diet");
                startActivity(i);
            }
        });

        babycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","baby");
                startActivity(i);
            }
        });

        familycare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","family");
                startActivity(i);
            }
        });

        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","women");
                startActivity(i);
            }
        });

        hair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getContext(),doctors.class);
                i.putExtra("a","hair");
                startActivity(i);
            }
        });


        return view;
    }
}
