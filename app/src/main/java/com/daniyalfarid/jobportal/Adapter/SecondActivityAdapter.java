package com.daniyalfarid.jobportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.daniyalfarid.jobportal.DataModel.SecondActivityDataModel;
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


        final int _position = position;

//        final String TITLE_ = datamodels.get(position).getTitle();

        holder.secondActivityReadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ThirdActivity.class);
                intent.putExtra("Title_",datamodels.get(_position).getTitle());
                context.startActivity(intent);
//                Toast.makeText(context,buttonText,Toast.LENGTH_SHORT).show();

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
        TextView secondActivitybuttonText;

        public NextActivityViewHolder(View itemView) {
            super(itemView);
            title = (TextView)itemView.findViewById(R.id.secondActivityTitle);
            description = (TextView)itemView.findViewById(R.id.secondActivityDescription);
            secondActivityReadButton = (ImageButton)itemView.findViewById(R.id.secondActivityReadButton);
            secondActivitybuttonText = (TextView)itemView.findViewById(R.id.secondActivitybuttonText);




        }
    }
}
