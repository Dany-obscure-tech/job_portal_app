package com.daniyalfarid.jobportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daniyalfarid.jobportal.Adapter.ImageGalleryAdapter;
import com.daniyalfarid.jobportal.AlertDialogs.ImageDisplayAlert;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ThirdActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference buttonTextRef;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;
    TextView Title,thirdActivityDescription;
    ImageButton applybtn;
    TextView buttonText;

    ImageView imageFrame;

    FragmentManager fragmentManager = getSupportFragmentManager();

    private ArrayList<String> mImages;

//    private int[] IMAGES = {R.drawable.img1,R.drawable.img2,R.mipmap.img3,R.drawable.img1,R.drawable.img2,R.mipmap.img3,R.drawable.img1,R.drawable.img2,R.mipmap.img3};

    private ImageGalleryAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        buttonText=(TextView)findViewById(R.id.buttonText);

        Title = (TextView)findViewById(R.id.thirdActivityMainTitle);
        thirdActivityDescription = (TextView)findViewById(R.id.thirdActivityDescription);
        applybtn = (ImageButton)findViewById(R.id.__applyBtn);
        imageFrame = (ImageView)findViewById(R.id.imageFrame);

        database = FirebaseDatabase.getInstance();
        buttonTextRef=database.getReference("Project Text Titles");
        buttonTextRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.child("Third Activity Button").getValue(String.class);
                buttonText.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Intent intent = getIntent();

        final String TITLE = intent.getStringExtra("Title_");
        final String FULL_DESCRIPTION = intent.getStringExtra("Full_Description_");
        final ArrayList<String> IMAGES= intent.getStringArrayListExtra("Images_");
        final String PUSHID = intent.getStringExtra("PUSHID");
        Title.setText(TITLE);
        thirdActivityDescription.setText(FULL_DESCRIPTION);


        recyclerView = (RecyclerView)findViewById(R.id.imageGallery);


        layoutManager = new LinearLayoutManager(ThirdActivity.this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);



        adapter = new ImageGalleryAdapter(this,IMAGES,fragmentManager);

        recyclerView.setAdapter(adapter);

        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this,JobApplicationForm.class);
                intent.putExtra("T",Title.getText());
                intent.putExtra("FULL_DESCRIPTION",FULL_DESCRIPTION);
                intent.putExtra("PUSHID",PUSHID);

                startActivity(intent);
            }
        });

    }

}
