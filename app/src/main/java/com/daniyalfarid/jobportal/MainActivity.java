package com.daniyalfarid.jobportal;


import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.daniyalfarid.jobportal.Adapter.FirstActivityAdapter;
import com.daniyalfarid.jobportal.DataModel.MainActivityDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ArrayList<MainActivityDataModel> list;
    FirstActivityAdapter firstActivityAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView)findViewById(R.id.jobCategoryList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("jobCategoryIcons");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<MainActivityDataModel>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    MainActivityDataModel data = dataSnapshot1.getValue(MainActivityDataModel.class);
                    list.add(data);
                }
                firstActivityAdapter = new FirstActivityAdapter(MainActivity.this,list);
                recyclerView.setAdapter(firstActivityAdapter);

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(MainActivity.this,"Error Occured",Toast.LENGTH_LONG).show();
            }
        });
    }
}
