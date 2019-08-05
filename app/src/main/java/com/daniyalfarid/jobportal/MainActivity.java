package com.daniyalfarid.jobportal;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.daniyalfarid.jobportal.Adapter.JobCategoryListAdapter;


public class MainActivity extends AppCompatActivity {
    RecyclerView jobCategoryList;
    ImageButton button;
    ImageView logo;

    private int[] jobCategoryIcons = {R.drawable.android_icon,R.drawable.web_icon,R.drawable.android_icon,R.drawable.web_icon,R.drawable.android_icon,R.drawable.web_icon,R.drawable.android_icon,R.drawable.web_icon};
    private String[] jobCategoryTitles = {"Android1","Web1","Android2","Web2","Android3","Web3","Android4","Web4","Android5","Web5"};


    private JobCategoryListAdapter adapter;

    public RecyclerView.LayoutManager getJobListlayoutManager() {
        return jobListlayoutManager;
    }

    private RecyclerView.LayoutManager jobListlayoutManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobCategoryList = (RecyclerView)findViewById(R.id.jobCategoryList);
        button = (ImageButton) findViewById(R.id.imageViewAddButton);
        logo = (ImageView) findViewById(R.id.imageViewLogo);


        jobListlayoutManager = new GridLayoutManager(MainActivity.this,2);
        jobCategoryList.setHasFixedSize(true);
        jobCategoryList.setLayoutManager(jobListlayoutManager);


        adapter = new JobCategoryListAdapter(this,jobCategoryIcons,jobCategoryTitles);


        jobCategoryList.setAdapter(adapter);

        AddjobActivity();
    }

    private void AddjobActivity() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddJobActivity.class);
                startActivity(intent);
            }
        });
    }


}
