package com.daniyalfarid.jobportal;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
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
    DatabaseReference titleDatabaseRef;
    RecyclerView recyclerView;
    ImageButton imageViewAddButton,postJobButton;
    ArrayList<MainActivityDataModel> list;
    FirstActivityAdapter firstActivityAdapter;
    TextView buttonText,postJobButtonText;


    TextView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("jobCategoryIcons");
        titleDatabaseRef = FirebaseDatabase.getInstance().getReference().child("Project Text Titles");
        logo = (TextView)findViewById(R.id.textViewLogo);
        buttonText = (TextView)findViewById(R.id.buttonText);
        postJobButtonText = (TextView)findViewById(R.id.postJobButtonText);


        titleDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.child("Main Activity Title").getValue(String.class);
                String data2 = dataSnapshot.child("Main Activity Add your CV Text").getValue(String.class);
                String data3 = dataSnapshot.child("Main Activity Post Job Button Text").getValue(String.class);

                logo.setText(data);
                buttonText.setText(data2);
                postJobButtonText.setText(data3);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postJobButton = (ImageButton)findViewById(R.id.postJobButton);
        postJobButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,post_activity.class);
                intent.putExtra("ID","");
                startActivity(intent);
            }
        });


        imageViewAddButton = (ImageButton)findViewById(R.id.imageViewAddButton);
        imageViewAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddJobActivity.class);
                startActivity(intent);
            }
        });


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
