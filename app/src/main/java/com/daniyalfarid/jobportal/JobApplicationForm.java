package com.daniyalfarid.jobportal;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class JobApplicationForm extends AppCompatActivity {
    String name=null;
    String contactNo = null;
    String email = null;
    String description = null;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private DatabaseReference fDescRef;
    private DatabaseReference titleRef;
    private TextView title,applicationFormButtonText;

    EditText ApplicationFormName,ApplicationFormcontactNo,ApplicationEmail,ApplicationFormDescription;
    ImageButton applicationFormSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_application_form);

        mDatabase=FirebaseDatabase.getInstance();


        titleRef = mDatabase.getReference("Project Text Titles");
        fDescRef = mDatabase.getReference("Job Categories");


        ApplicationFormName=(EditText)findViewById(R.id.ApplicationFormName);
        ApplicationFormcontactNo=(EditText)findViewById(R.id.ApplicationFormcontactNo);
        ApplicationEmail=(EditText)findViewById(R.id.ApplicationEmail);
        ApplicationFormDescription=(EditText)findViewById(R.id.ApplicationFormDescription);

        applicationFormSubmit=(ImageButton)findViewById(R.id.applicationFormSubmit);
        title=(TextView)findViewById(R.id.applicationFormTitle);
        applicationFormButtonText=(TextView)findViewById(R.id.applicationFormButtonText);


        titleRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String data = dataSnapshot.child("Job Application Form Title").getValue(String.class);
                String data1 = dataSnapshot.child("Job Application Form Button Text").getValue(String.class);
                applicationFormButtonText.setText(data1);
                title.setText(data);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        Intent intent = getIntent();
        final String T = intent.getStringExtra("T");
        final String FULL_DESCRIPTION = intent.getStringExtra("FULL_DESCRIPTION");
        final String PUSHID = intent.getStringExtra("PUSHID");
        mRef=mDatabase.getReference("Job Applications/"+PUSHID);


        String pushID = null;

        final Query userQuery = FirebaseDatabase.getInstance().getReference().child("Job Categories").orderByChild("full_description");

///////////////////////



////////////////////////



        applicationFormSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = ApplicationFormName.getText().toString().trim();
                String contactNo = ApplicationFormcontactNo.getText().toString().trim();
                String email = ApplicationEmail.getText().toString().trim();
                String description = ApplicationFormDescription.getText().toString().trim();

                String _FULL_DESCRIPTION = FULL_DESCRIPTION;



                boolean VALIDATE = validate(name,contactNo,email,description);

                if (VALIDATE==true){
                    String key = mRef.push().getKey();


                    mRef.child(key).child("name").setValue(name);
                    mRef.child(key).child("contactNo").setValue(contactNo);
                    mRef.child(key).child("email").setValue(email);
                    mRef.child(key).child("description").setValue(description);
                    mRef.child(key).child("Applied for").setValue(T);

                    Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_SHORT).show();
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Please validate fields!!",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }


        private boolean validate(String name, String contactNo, String email, String description) {
            this.name = name;
            this.contactNo=contactNo;
            this.email=email;
            this.description=description;
            boolean valid = true;

            if (name.isEmpty()){
                ApplicationFormName.setError("Field must not be empty");
                valid=false;
            }

            if (contactNo.isEmpty()){
                ApplicationFormcontactNo.setError("Field must not be empty");
                valid=false;
            }
            if (contactNo.length()<11){
                ApplicationFormcontactNo.setError("Please enter valid contact no");
                valid = false;
            }

            if (email.isEmpty()){
                ApplicationEmail.setError("Field must not be empty");
                valid=false;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                ApplicationEmail.setError("Please enter a valid email");
                valid=false;
            }

            if (description.isEmpty()){
                ApplicationFormDescription.setError("Field must not be empty");
                valid=false;
            }
            return valid;

    }

}
