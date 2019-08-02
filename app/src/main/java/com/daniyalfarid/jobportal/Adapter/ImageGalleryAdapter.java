package com.daniyalfarid.jobportal.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.daniyalfarid.jobportal.ImageDisplayActivity;
import com.daniyalfarid.jobportal.R;
import com.daniyalfarid.jobportal.TestAvtivity;


public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryViewHolder>{

    private int[] pics;



    Context context;

    public ImageGalleryAdapter(Context context, int[] btns){
        pics = btns;
        this.context = context;

    }



    @NonNull
    @Override
    public ImageGalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_imageitem,viewGroup,false);


        ImageGalleryViewHolder imageGalleryViewHolder = new ImageGalleryViewHolder(view);

        return imageGalleryViewHolder;
    }








    @Override
    public void onBindViewHolder(@NonNull ImageGalleryViewHolder imageGalleryViewHolder, int i) {

        int btnID = pics[i];

        imageGalleryViewHolder._pics.setImageResource(btnID);


    }

    @Override
    public int getItemCount() {
        return pics.length;
    }


    public class ImageGalleryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView _pics;
        ImageView _imageFrame;
        public ImageGalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.findViewById(R.id.thirdActivityImage).setOnClickListener(this);
            _imageFrame = itemView.findViewById(R.id.imageFrame);





            _pics = itemView.findViewById(R.id.thirdActivityImage);

        }

        @Override
        public void onClick(View v) {
//           Intent intent = new Intent(context, ImageDisplayActivity.class);
            Toast.makeText(context,_pics.getDrawable().toString(),Toast.LENGTH_LONG).show();
//           context.startActivity(intent);

//            _imageFrame.setImageDrawable(_pics.getDrawable());

        }



    }

}
