package com.daniyalfarid.jobportal;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daniyalfarid.jobportal.Adapter.SecondActivityAdapter;
import com.daniyalfarid.jobportal.DataModel.SecondActivityDataModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SecondActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    DatabaseReference textTitleRef;
    RecyclerView recyclerView;
    ArrayList<SecondActivityDataModel> list;
    SecondActivityAdapter adapter;
    TextView secondActivityMainTitle,addJob2Text;
    String textData;
    ImageButton imageViewAddButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Intent intent = getIntent();
        final String selectedJobCategory = intent.getStringExtra("Selected Job Category");
        final String SelectedJobTitle = intent.getStringExtra("Title");

        secondActivityMainTitle = (TextView)findViewById(R.id.secondActivityMainTitle);
        addJob2Text = (TextView)findViewById(R.id.addJob2Text);

        imageViewAddButton = (ImageButton)findViewById(R.id.imageViewAddButton);
        imageViewAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SecondActivity.this,post_activity.class);
                intent.putExtra("ID",SelectedJobTitle);
                startActivity(intent);
            }
        });








        secondActivityMainTitle.setText(SelectedJobTitle);

        recyclerView = (RecyclerView)findViewById(R.id.secondActivityRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        textTitleRef = FirebaseDatabase.getInstance().getReference().child("Project Text Titles");
        textTitleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data2 = dataSnapshot.child("Second Activity Post Job Text").getValue(String.class);
                addJob2Text.setText(data2);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this,"Error Occured",Toast.LENGTH_LONG).show();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("Job Categories").child(selectedJobCategory);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<SecondActivityDataModel>();
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    SecondActivityDataModel data = dataSnapshot1.getValue(SecondActivityDataModel.class);
                    list.add(data);
                }
                String reference = dataSnapshot.getRef().toString();
                adapter = new SecondActivityAdapter(SecondActivity.this,list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SecondActivity.this,"Error Occured",Toast.LENGTH_LONG).show();
            }
        });

    }
}
