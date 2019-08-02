package com.daniyalfarid.jobportal;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.daniyalfarid.jobportal.Adapter.SecondActivityListAdapter;


public class SecondActivity extends AppCompatActivity {
    RecyclerView secondActivityList;
    TextView MainTitle;

    private String[] TITLES = {"Title1","Title2","Title3","Title4","Title5"};
    private String[] DESCRIPTIONS = {"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."};
    private int[] BTNS = {R.drawable.read_more_button,R.drawable.read_more_button,R.drawable.read_more_button,R.drawable.read_more_button,R.drawable.read_more_button};
    private int[] LINES = {R.drawable.content_seperator,R.drawable.content_seperator,R.drawable.content_seperator,R.drawable.content_seperator,R.drawable.content_seperator};

    private SecondActivityListAdapter adapter;

    private RecyclerView.LayoutManager layoutManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        MainTitle = (TextView)findViewById(R.id.secondActivityMainTitle);
        String TITLE = getIntent().getStringExtra("TITLE");

        MainTitle.setText(TITLE);


        secondActivityList = (RecyclerView)findViewById(R.id.secondActivityRecyclerView);


        layoutManager = new GridLayoutManager(SecondActivity.this,1);
        secondActivityList.setHasFixedSize(true);
        secondActivityList.setLayoutManager(layoutManager);


        adapter = new SecondActivityListAdapter(this,TITLES,DESCRIPTIONS,BTNS,LINES);

        secondActivityList.setAdapter(adapter);

    }
}
