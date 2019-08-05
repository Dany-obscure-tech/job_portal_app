package com.daniyalfarid.jobportal.AlertDialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.daniyalfarid.jobportal.Adapter.ImageGalleryAdapter;
import com.daniyalfarid.jobportal.R;

public class ImageDisplayAlert extends AppCompatDialogFragment {
    private ImageView imageFrame;
    //ImageGalleryAdapter adapter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_imagealert,null);

        builder.setView(view).setTitle("Login")
            .setNegativeButton("cancle", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        imageFrame = view.findViewById(R.id.imageFrame);
        //adapter.getImageFrameID(imageFrame);

        return builder.create();


//        return super.onCreateDialog(savedInstanceState);
    }

}
