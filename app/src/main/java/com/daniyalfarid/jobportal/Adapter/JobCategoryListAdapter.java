package com.daniyalfarid.jobportal.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daniyalfarid.jobportal.MainActivity;
import com.daniyalfarid.jobportal.R;
import com.daniyalfarid.jobportal.SecondActivity;

public class JobCategoryListAdapter extends RecyclerView.Adapter<JobCategoryListAdapter.JobCategoryViewHolder> {
    MainActivity obj;
    private int[] icons;
    private String[] titles;
    Context context;

    public JobCategoryListAdapter(Context context,int[] images,String[] _titles){
        icons = images;
        titles = _titles;
        this.context = context;

    }




    @NonNull
    @Override
    public JobCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_jobcategoryitem,viewGroup,false);
        JobCategoryViewHolder jobCategoryViewHolder = new JobCategoryViewHolder(view);

        return jobCategoryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull JobCategoryViewHolder jobCategoryViewHolder, int i) {

        int iconID =icons[i];
        String titleID = titles[i];
        jobCategoryViewHolder.jobCategoryIcon.setImageResource(iconID);
        jobCategoryViewHolder.jobCategoryTitle.setText(titleID);

    }

    @Override
    public int getItemCount() {
        return icons.length;
    }


    public class JobCategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView jobCategoryIcon;
        TextView jobCategoryTitle;


        public JobCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.iconButton).setOnClickListener(this);


            jobCategoryIcon = itemView.findViewById(R.id.jobCategoryIcon);
            jobCategoryTitle = itemView.findViewById(R.id.jobCategoryTitle);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, SecondActivity.class);

            intent.putExtra("TITLE", jobCategoryTitle.getText());
            context.startActivity(intent);


        }
    }
}
