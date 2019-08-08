package com.daniyalfarid.jobportal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class AddJobActivity extends AppCompatActivity {
    String name=null;
    String contactNo = null;
    String email = null;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private DatabaseReference titleRef;
    private FirebaseStorage mStorageReference;


    EditText addJobFormName,addJobFormcontactNo,addJobEmail;
    ImageButton addFileBtn,applicationFormSubmit;
    Context context = this;
    TextView addedFileName;
    TextView addJobTitle;
    private static final int READ_REQUEST_CODE = 42;
    private Uri docUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job);

        //////////

        mDatabase=FirebaseDatabase.getInstance();

        mRef=mDatabase.getReference("Job Addition Request");

        mStorageReference = FirebaseStorage.getInstance();

        final StorageReference docRef = mStorageReference.getReference("Add Job Requests docs");

        /////////
        addJobTitle = (TextView)findViewById(R.id.addJobTitle);
        addJobFormName=(EditText)findViewById(R.id.addJobFormName);
        applicationFormSubmit = (ImageButton)findViewById(R.id.applicationFormSubmit);
        addJobFormcontactNo=(EditText)findViewById(R.id.addJobFormcontactNo);
        addJobEmail=(EditText)findViewById(R.id.addJobEmail);

        addFileBtn = (ImageButton)findViewById(R.id.addJobFormAttachFile);
        addedFileName = (TextView)findViewById(R.id.addedFileName);

        titleRef = FirebaseDatabase.getInstance().getReference().child("Project Text Titles");

        titleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.child("Add Job Title").getValue(String.class);
                addJobTitle.setText(data);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        addFileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();

            }
        });

        applicationFormSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = addJobFormName.getText().toString();
                String contactNo = addJobFormcontactNo.getText().toString();
                String email = addJobEmail.getText().toString();

                boolean VALIDATE = validate(name,contactNo,email,docUri);

                if (VALIDATE == true){
                    String key = mRef.push().getKey();

                    mRef.child(key).child("name").setValue(name);
                    mRef.child(key).child("contactNo").setValue(contactNo);
                    mRef.child(key).child("email").setValue(email);

                    Toast.makeText(getApplicationContext(),"Uploading",Toast.LENGTH_SHORT).show();

                    UploadTask uploadTask = docRef.child(key+"/"+docUri.getLastPathSegment()).putFile(docUri);
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getApplicationContext(),"Uploaded Successful.",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please validate fields!!",Toast.LENGTH_SHORT).show();
                }



            }
        });

    }

    private boolean validate(String name, String contactNo, String email, Uri docUri) {
        this.name = name;
        this.contactNo=contactNo;
        this.email=email;
        this.docUri=docUri;
        boolean valid = true;

        if (name.isEmpty()){
            addJobFormName.setError("Field must not be empty");
            valid=false;
        }

        if (contactNo.isEmpty()){
            addJobFormcontactNo.setError("Field must not be empty");
            valid=false;
        }
        if (contactNo.length()<11){
            addJobFormcontactNo.setError("Please enter valid contact no");
            valid = false;
        }

        if (email.isEmpty()){
            addJobEmail.setError("Field must not be empty");
            valid=false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            addJobEmail.setError("Please enter a valid email");
            valid=false;
        }

        if (docUri == null){
            Toast.makeText(getApplicationContext(),"Please upload a document",Toast.LENGTH_LONG).show();
            valid=false;
        }
        return valid;
    }

    public void performFileSearch() {

        // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
        // browser.
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

        // Filter to only show results that can be "opened", such as a
        // file (as opposed to a list of contacts or timezones)
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        // Filter to show only images, using the image MIME data type.
        // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
        // To search for all documents available via installed storage providers,
        // it would be "*/*".
        intent.setType("*/*");

        startActivityForResult(intent, READ_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {

        // The ACTION_OPEN_DOCUMENT intent was sent with the request code
        // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
        // response to some other intent, and the code below shouldn't run at all.

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // The document selected by the user won't be returned in the intent.
            // Instead, a URI to that document will be contained in the return intent
            // provided to this method as a parameter.
            // Pull that URI using resultData.getData().
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                docUri = uri;
                afterSelection(uri);
            }
        }
    }

    private void afterSelection(Uri uri) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                cursor.close();
            }
        }
        if (result == null) {
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if (cut != -1) {
                result = result.substring(cut + 1);
            }
        }


        addedFileName.setText(result);




    }



}
