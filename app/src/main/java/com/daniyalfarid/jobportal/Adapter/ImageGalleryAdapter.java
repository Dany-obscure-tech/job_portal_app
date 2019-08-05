package com.daniyalfarid.jobportal.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daniyalfarid.jobportal.R;
import com.daniyalfarid.jobportal.ThirdActivity;


public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryViewHolder>{

    private int[] pics;
    ImageView imageFrame;
    ThirdActivity thirdActObj;
    FragmentManager fragmentManager;
    Dialog myDialog;



    Context context;

    public ImageGalleryAdapter(Context context, int[] btns, FragmentManager fragmentManager){
        pics = btns;
        this.context = context;
        this.fragmentManager = fragmentManager;

    }



    @NonNull
    @Override
    public ImageGalleryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_imageitem,viewGroup,false);


        ImageGalleryViewHolder imageGalleryViewHolder = new ImageGalleryViewHolder(view);

        return imageGalleryViewHolder;
    }








    @Override
    public void onBindViewHolder(@NonNull final ImageGalleryViewHolder imageGalleryViewHolder, final int i) {

        int btnID = pics[i];

        imageGalleryViewHolder._pics.setImageResource(btnID);


        // Dialog
        myDialog = new Dialog(context);
        myDialog.setContentView(R.layout.layout_imagealert);

        imageGalleryViewHolder.parentID.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImageView imageFrame = (ImageView) myDialog.findViewById(R.id.imageFrame);
                imageFrame.setImageResource(pics[imageGalleryViewHolder.getAdapterPosition()]);

//                AlertDialog.Builder imageAlert = new AlertDialog.Builder(context);
//                imageAlert.show();
//                thirdActObj.openDialog();
                myDialog.show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return pics.length;
    }


    public class ImageGalleryViewHolder extends RecyclerView.ViewHolder{
        ImageView _pics;
        LinearLayout parentID;
        public ImageGalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            parentID = itemView.findViewById(R.id.parentID);

            _pics = itemView.findViewById(R.id.thirdActivityImage);


        }




    }

    public void getImageFrameID(ImageView imageFrame){
        this.imageFrame = imageFrame;
    }


}
