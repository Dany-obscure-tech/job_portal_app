package com.daniyalfarid.jobportal;

import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.daniyalfarid.jobportal.SpinnerPopulation.SpinnerPopulation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.os.Build.ID;
import static android.provider.Contacts.SettingsColumns.KEY;

public class post_activity extends AppCompatActivity {

    String title1,description1,category1 = null;

    EditText title,description,full_description,name,email,contactNo;
    ImageButton submit;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    DatabaseReference spinnerDataRef;
    private FirebaseStorage mStorageReference;
    Spinner categorySelection;
    String selectedCategory;

    TextView addedFileName;
    ImageButton add_file_button;

    private Uri singleUri = null;
    private Uri[] arrayUri = null;

    String ID = null;
    private static final int READ_REQUEST_CODE = 42;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_activity);

        mStorageReference = FirebaseStorage.getInstance();
        final StorageReference docRef = mStorageReference.getReference("Add Job Requests docs");

        categorySelection = (Spinner)findViewById(R.id.categorySelection);

        Intent oldIntent = getIntent();


        /*************************/

        ID = oldIntent.getStringExtra("ID");



        /*************************/
        addedFileName=(TextView)findViewById(R.id.addedFileName);
        add_file_button=(ImageButton)findViewById(R.id.addJobFormAttachFile);

        title=(EditText)findViewById(R.id.title);
        description=(EditText)findViewById(R.id.description);
        full_description=(EditText)findViewById(R.id.full_description);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        contactNo=(EditText)findViewById(R.id.contactNo);



        submit=(ImageButton)findViewById(R.id.submit);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference().child(ID);

        spinner();
        categorySelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),categorySelection.getSelectedItem().toString(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(getApplicationContext(),"Nothing Selected!",Toast.LENGTH_SHORT).show();
            }
        });

        add_file_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performFileSearch();
            }
        });

        submit(docRef);

    }

    public void performFileSearch() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setType("*/*");
        startActivityForResult(intent, 1001);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1001:

                    // Checking whether data is null or not
                    if (data != null) {

                        // Checking for selection multiple files or single.
                        if (data.getClipData() != null){
                            arrayUri = new Uri[data.getClipData().getItemCount()];

                            // Getting the length of data and logging up the logs using index
                            for (int index = 0; index < data.getClipData().getItemCount(); index++) {


                                // Getting the URIs of the selected files and logging them into logcat at debug level
                                Uri uri = data.getClipData().getItemAt(index).getUri();
                                arrayUri[index]=uri;
                                Log.d("filesUri [" + uri + "] : ", String.valueOf(uri) );
                                singleUri = null;

                            }
                            afterSelection(arrayUri,singleUri);
//                            Toast.makeText(getApplicationContext(),arrayUri[3].toString(),Toast.LENGTH_SHORT).show();
                        }else{

                            // Getting the URI of the selected file and logging into logcat at debug level
                            Uri uri = data.getData();
                            singleUri = uri;
                            Log.d("fileUri: ", String.valueOf(uri));
                            arrayUri = null;
                            afterSelection(arrayUri,singleUri);
                        }
                    }
                    break;
            }
        }
    }

    private void afterSelection(Uri[] arrayUri,Uri singleUri) {
//        Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_SHORT).show();
        Uri _singleUri = singleUri;
        if (singleUri!=null){
            addedFileName.setText(getFileNameByUri(this,singleUri));

        }
        else {
            String name = "";
            Uri uri = null;

            for (int i = 0;i<=arrayUri.length-1;i++){
                uri = arrayUri[i];
                if (name==""){
                    name = getFileNameByUri(this,uri);

                }else {
                    name = name+"\n"+getFileNameByUri(this,uri);

                }


            }

            Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
            addedFileName.setText(name);
        }

    }


    public static String getFileNameByUri(Context context, Uri uri)
    {
        String fileName="unknown";//default fileName
        Uri filePathUri = uri;
        if (uri.getScheme().toString().compareTo("content")==0)
        {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst())
            {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                filePathUri = Uri.parse(cursor.getString(column_index));
                fileName = filePathUri.getLastPathSegment().toString();
            }
        }
        else if (uri.getScheme().compareTo("file")==0)
        {
            fileName = filePathUri.getLastPathSegment().toString();
        }
        else
        {
            fileName = fileName+"_"+filePathUri.getLastPathSegment();
        }
        return fileName;
    }



    private void spinner() {

        spinnerDataRef = database.getReference("jobCategoryIcons");
        spinnerDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<SpinnerPopulation> items = new ArrayList<>();
                if (ID.isEmpty()){
                    items.add(new SpinnerPopulation("-Select Category-"));
                }

                Long itemCount = dataSnapshot.getChildrenCount();

                for (DataSnapshot snapshot:dataSnapshot.getChildren()){

                    Map<String,Object>data = (Map<String, Object>)snapshot.getValue();
                    System.out.println(data.get("title"));
                    SpinnerPopulation data_ = new SpinnerPopulation(data.get("title").toString());
                    items.add(data_);
                }

//                populating spinner
                ArrayAdapter<SpinnerPopulation> adapter = new ArrayAdapter<SpinnerPopulation>(post_activity.this,
                        android.R.layout.simple_spinner_item,items);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                categorySelection.setAdapter(adapter);
//                categorySelection.setSelection(2);
                int _itemCount = itemCount.intValue();
                String[] toSelectList = new String[_itemCount];
                String s = null;

                for (int i = 0; i<_itemCount;i++){
                    s = String.valueOf(items.get(i));
                    toSelectList[i]=s;
                    if (s.equals(ID)){
                        categorySelection.setSelection(i);

                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }



///////////////////////////

    private void submit(final StorageReference docRef) {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String _title = title.getText().toString();
                String _description = description.getText().toString();
                String _full_description = full_description.getText().toString();
                String _name = name.getText().toString();
                String _email = email.getText().toString();
                String _contactNo = contactNo.getText().toString();

                String _category = categorySelection.getSelectedItem().toString();
                String _selectedCategory = categorySelection.getSelectedItem().toString();

                boolean valid = validate(_title,_description,_category);

                if (valid==true){
                    final DatabaseReference ref = database.getReference("Job Categories/"+_selectedCategory);
                    final String key = ref.push().getKey();

                    ref.child(key).child("title").setValue(_title);
                    ref.child(key).child("description").setValue(_description);
                    ref.child(key).child("full_description").setValue(_full_description);
                    ref.child(key).child("name").setValue(_name);
                    ref.child(key).child("email").setValue(_email);
                    ref.child(key).child("contact_no").setValue(_contactNo);
                    ref.child(key).child("active").setValue(true);

                    if (arrayUri!=null||singleUri!=null){
                        if (arrayUri==null){
                            final StorageReference _docRef = docRef.child(key+"/"+singleUri.getLastPathSegment());

                            UploadTask uploadTask = _docRef.putFile(singleUri);
                            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                                Toast.makeText(getApplicationContext(),"Uploaded Successful.",Toast.LENGTH_SHORT).show();

                                    _docRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            Log.e("Tuts+", "uri: " + uri.toString());
                                            //Handle whatever you're going to do with the URL here
                                            Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_SHORT).show();
                                            ref.child(key).child("images/1").setValue(uri.toString());
                                        }
                                    });

                                    finish();

                                }
                            });


                        }else {
                            UploadTask uploadTask=null;
                            StorageReference _docRef = null;
                            for (int i = 0;i<=arrayUri.length-1;i++){
                                _docRef=docRef.child(key+"/"+arrayUri[i].getLastPathSegment());
                                uploadTask=_docRef.putFile(arrayUri[i]);

                                final StorageReference final_docRef = _docRef;
                                final int finalI = i;
                                uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Toast.makeText(getApplicationContext(),"Uploaded Successful.",Toast.LENGTH_SHORT).show();

                                        final_docRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                Log.e("Tuts+", "uri: " + uri.toString());
                                                //Handle whatever you're going to do with the URL here
                                                Toast.makeText(getApplicationContext(),uri.toString(),Toast.LENGTH_SHORT).show();
                                                ref.child(key).child("images/"+ (finalI+1)).setValue(uri.toString());
                                            }
                                        });

                                    }
                                });
                            }
                            finish();

                        }
                    }


                    Toast.makeText(getApplicationContext(),"Submitted",Toast.LENGTH_SHORT).show();

                    finish();
                }




            }
        });
    }

    private boolean validate(String _title, String _description,String _category) {
        title1 = _title;
        description1 = _description;
        category1 = _category;
        boolean valid =true;
        if (_title.isEmpty()){
            title.setError("Please enter title");
            valid = false;
        }
        if(_description.isEmpty()){
            description.setError("Please enter description");
            valid = false;
        }
        if (category1 =="-Select Category-"){
            ((TextView)categorySelection.getSelectedView()).setError("Please select a category");
            valid = false;
        }

        return valid;
    }

}
