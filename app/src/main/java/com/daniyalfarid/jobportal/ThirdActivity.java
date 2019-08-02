package com.daniyalfarid.jobportal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.daniyalfarid.jobportal.Adapter.ImageGalleryAdapter;


public class ThirdActivity extends AppCompatActivity {
    RecyclerView imagGallery;
    TextView Title;
    ImageButton applybtn;

    private int[] IMAGES = {R.drawable.img1,R.drawable.img2,R.drawable.img1,R.drawable.img2,R.drawable.img1,R.drawable.img2,R.mipmap.img3};

    private ImageGalleryAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Title = (TextView)findViewById(R.id.thirdActivityMainTitle);
        applybtn = (ImageButton)findViewById(R.id.applyBtn);
        String TITLE = getIntent().getStringExtra("TITLE2");
        Title.setText(TITLE);


        imagGallery = (RecyclerView)findViewById(R.id.imageGallery);


        layoutManager = new LinearLayoutManager(ThirdActivity.this);
        ((LinearLayoutManager) layoutManager).setOrientation(LinearLayoutManager.HORIZONTAL);
        imagGallery.setHasFixedSize(true);
        imagGallery.setLayoutManager(layoutManager);


        adapter = new ImageGalleryAdapter(this,IMAGES);

        imagGallery.setAdapter(adapter);

        jobApplication();

    }

    private void jobApplication() {
        applybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ThirdActivity.this,JobApplicationForm.class);
                startActivity(intent);
            }
        });
    }

    public void AlertDialog(){
        imagGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
