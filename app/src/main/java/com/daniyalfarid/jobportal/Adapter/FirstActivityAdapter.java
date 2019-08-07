package com.daniyalfarid.jobportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daniyalfarid.jobportal.DataModel.MainActivityDataModel;
import com.daniyalfarid.jobportal.R;
import com.daniyalfarid.jobportal.SecondActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FirstActivityAdapter extends RecyclerView.Adapter<FirstActivityAdapter.ViewHolder> {

    Context context;
    ArrayList<MainActivityDataModel> dataModels;

    public FirstActivityAdapter(Context context, ArrayList<MainActivityDataModel> dataModels){
        this.context = context;
        this.dataModels = dataModels;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_jobcategoryitem,viewGroup,false));

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.title.setText(dataModels.get(i).getTitle());
        Picasso.get().load(dataModels.get(i).getLink()).into(viewHolder.icon);
        final String ID = dataModels.get(i).getID();
        final  String TITLE = dataModels.get(i).getTitle();

        viewHolder.jobCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context,ID,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("Selected Job Category",ID);
                intent.putExtra("Title",TITLE);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return dataModels.size();
    }

    class  ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        ImageButton jobCategoryButton;
        RelativeLayout parentID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = (ImageView)itemView.findViewById(R.id.jobCategoryIcon);
            title = (TextView)itemView.findViewById(R.id.jobCategoryTitle);
            jobCategoryButton = (ImageButton) itemView.findViewById(R.id.iconButton);
            parentID = (RelativeLayout)itemView.findViewById(R.id.parentID);
        }
    }
}
