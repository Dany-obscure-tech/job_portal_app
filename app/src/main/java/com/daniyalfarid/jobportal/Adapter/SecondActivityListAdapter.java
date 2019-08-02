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

import com.daniyalfarid.jobportal.R;
import com.daniyalfarid.jobportal.ThirdActivity;

public class SecondActivityListAdapter extends RecyclerView.Adapter<SecondActivityListAdapter.SecondActivityListViewHolder> {
    private int[] readMoreBtn;
    private String[] secondActivitytitles;
    private int[] seperators;
    private String[] secondActivityDescriptions;
    Context context;

    public SecondActivityListAdapter(Context context,String[] _titles,String[] _descriptions,int[] btns,int[] lines){
        readMoreBtn = btns;
        secondActivitytitles = _titles;
        seperators = lines;
        secondActivityDescriptions = _descriptions;
        this.context = context;

    }



    @NonNull
    @Override
    public SecondActivityListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_secondactivityitem,viewGroup,false);
        SecondActivityListViewHolder secondActivityListViewHolder = new SecondActivityListViewHolder(view);

        return secondActivityListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SecondActivityListViewHolder secondActivityListViewHolder, int i) {

        String titleID = secondActivitytitles[i];
        String descriptionID = secondActivityDescriptions[i];
        int btnID = readMoreBtn[i];
        int seperatorID = seperators[i];

        secondActivityListViewHolder._SATitle.setText(titleID);
        secondActivityListViewHolder._SADescription.setText(descriptionID);
        secondActivityListViewHolder._readMoreBtn.setImageResource(btnID);
        secondActivityListViewHolder._lineSeperator.setImageResource(seperatorID);

    }

    @Override
    public int getItemCount() {
        return secondActivitytitles.length;
    }


    public class SecondActivityListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView _readMoreBtn,_lineSeperator;
        TextView _SATitle, _SADescription;
        public SecondActivityListViewHolder(@NonNull View itemView) {
            super(itemView);

            _SATitle = itemView.findViewById(R.id.secondActivityTitle);
            _SADescription = itemView.findViewById(R.id.secondActivityDescription);
            _readMoreBtn = itemView.findViewById(R.id.secondActivityReadButton);
            _lineSeperator = itemView.findViewById(R.id.secondActivitySeperator);

            itemView.findViewById(R.id.secondActivityReadButton).setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, ThirdActivity.class);
            intent.putExtra("TITLE2", _SATitle.getText());
            context.startActivity(intent);

        }
    }
}
