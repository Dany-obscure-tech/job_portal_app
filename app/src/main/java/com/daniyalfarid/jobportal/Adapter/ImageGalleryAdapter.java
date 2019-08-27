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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ImageGalleryAdapter extends RecyclerView.Adapter<ImageGalleryAdapter.ImageGalleryViewHolder>{

    ArrayList<String> pics=null;
    ImageView imageFrame;
    ThirdActivity thirdActObj;
    FragmentManager fragmentManager;
    Dialog myDialog;



    Context context;

    public ImageGalleryAdapter(Context context, ArrayList<String> btns, FragmentManager fragmentManager){
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


        if (pics!=null){
            String btnID = pics.get(i);

//        imageGalleryViewHolder._pics.setImageResource(btnID);
            Picasso.get().load(btnID).into(imageGalleryViewHolder._pics);

            // Dialog
            myDialog = new Dialog(context);
            myDialog.setContentView(R.layout.layout_imagealert);

            imageGalleryViewHolder.parentID.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    ImageView imageFrame = (ImageView) myDialog.findViewById(R.id.imageFrame);
//                imageFrame.setImageResource(pics[imageGalleryViewHolder.getAdapterPosition()]);
                    Picasso.get().load(pics.get(imageGalleryViewHolder.getAdapterPosition())).into(imageFrame);

//                AlertDialog.Builder imageAlert = new AlertDialog.Builder(context);
//                imageAlert.show();
//                thirdActObj.openDialog();
                    myDialog.show();

                }
            });

        }


    }

    @Override
    public int getItemCount() {
        return pics.size();
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
