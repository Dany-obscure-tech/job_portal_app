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

import com.daniyalfarid.jobportal.DataModel.SecondActivityDataModel;
import com.daniyalfarid.jobportal.JobApplicationForm;
import com.daniyalfarid.jobportal.R;
import com.daniyalfarid.jobportal.SecondActivity;
import com.daniyalfarid.jobportal.ThirdActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class SecondActivityAdapter extends RecyclerView.Adapter<SecondActivityAdapter.NextActivityViewHolder> {
    Context context;
    DatabaseReference buttonTextRef;
    FirebaseDatabase database;
    ArrayList<SecondActivityDataModel> datamodels;
    String buttonText;


    public SecondActivityAdapter(Context context, ArrayList<SecondActivityDataModel> datamodels) {
        this.context = context;
        this.datamodels = datamodels;
    }

    @NonNull
    @Override
    public SecondActivityAdapter.NextActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        database = FirebaseDatabase.getInstance();
        return new NextActivityViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_secondactivityitem,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final SecondActivityAdapter.NextActivityViewHolder holder, final int position) {

        holder.title.setVisibility(View.GONE);
        holder.description.setVisibility(View.GONE);
        holder.secondActivityReadButton.setVisibility(View.GONE);
        holder.secondActivitySeperator.setVisibility(View.GONE);
        holder.secondActivitybuttonText.setVisibility(View.GONE);
        holder.relativeLayout.setVisibility(View.GONE);
        holder.wholeLayout.setVisibility(View.GONE);



        if (datamodels.get(position).getActive()){


            holder.title.setVisibility(View.VISIBLE);
            holder.description.setVisibility(View.VISIBLE);
            holder.secondActivityReadButton.setVisibility(View.VISIBLE);
            holder.secondActivitySeperator.setVisibility(View.VISIBLE);
            holder.secondActivitybuttonText.setVisibility(View.VISIBLE);
            holder.relativeLayout.setVisibility(View.VISIBLE);
            holder.wholeLayout.setVisibility(View.VISIBLE);

            holder.title.setText(datamodels.get(position).getTitle());
            holder.description.setText(datamodels.get(position).getDescription());

            buttonTextRef = database.getReference("Project Text Titles");
            buttonTextRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String data = dataSnapshot.child("Second Activity Button Text").getValue(String.class);
                    buttonText = data;
                    holder.secondActivitybuttonText.setText(buttonText);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }




        final int _position = position;

//        final String TITLE_ = datamodels.get(position).getTitle();

        holder.secondActivityReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,ThirdActivity.class);
                intent.putExtra("Title_",datamodels.get(position).getTitle());
                intent.putExtra("Email_",datamodels.get(position).getEmail());
                intent.putExtra("Full_Description_",datamodels.get(position).getFull_description());
                ArrayList<String> s = datamodels.get(position).getImages();
                intent.putExtra("Images_",s);
                intent.putExtra("PUSHID",datamodels.get(position).getPushID());

                Toast.makeText(context,datamodels.get(position).getEmail(),Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return datamodels.size();
    }

    public class NextActivityViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView description;
        ImageButton secondActivityReadButton;
        ImageView secondActivitySeperator;
        TextView secondActivitybuttonText;

        RelativeLayout wholeLayout,relativeLayout;

        public NextActivityViewHolder(View itemView) {
            super(itemView);

            wholeLayout =(RelativeLayout)itemView.findViewById(R.id.wholeLayout);
            relativeLayout =(RelativeLayout)itemView.findViewById(R.id.relativeLayout);

            title = (TextView)itemView.findViewById(R.id.secondActivityTitle);
            description = (TextView)itemView.findViewById(R.id.secondActivityDescription);
            secondActivityReadButton = (ImageButton)itemView.findViewById(R.id.secondActivityReadButton);
            secondActivitybuttonText = (TextView)itemView.findViewById(R.id.secondActivitybuttonText);
            secondActivitySeperator = (ImageView)itemView.findViewById(R.id.secondActivitySeperator);




        }
    }
}
