package com.daniyalfarid.jobportal;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.os.Build.ID;

public class post_activity extends AppCompatActivity {
    String title1 = null;
    String description1 = null;

    EditText title,description;
    ImageButton submit;
    FirebaseDatabase database;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_activity);

        Intent oldIntent = getIntent();
        String ID = oldIntent.getStringExtra("ID");

        title=(EditText)findViewById(R.id.title);
        description=(EditText)findViewById(R.id.description);



        submit=(ImageButton)findViewById(R.id.submit);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(ID);

        submit(ID);




    }

    private void submit(final String ID) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String _title = title.getText().toString();
                String _description = description.getText().toString();

                boolean valid = validate(_title,_description);

                if (valid==true){
                    DatabaseReference databaseReferenceTitle = database.getReference(ID+"/"+_title+"/title");
                    databaseReferenceTitle.setValue(_title);
                    DatabaseReference databaseReferenceDescription = database.getReference(ID+"/"+_title+"/description");
                    databaseReferenceDescription.setValue(_description);

                    Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_SHORT).show();

                    finish();
                }




            }
        });
    }

    private boolean validate(String _title, String _description) {
        title1 = _title;
        description1 = _description;

        boolean valid =true;
        if (_title.isEmpty()){
            title.setError("Please enter title");
            valid = false;
        }
        if(_description.isEmpty()){
            description.setError("Please enter description");
            valid = false;
        }

        return valid;
    }

}
