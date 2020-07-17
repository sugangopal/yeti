package com.example.test;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editImagename;
    Button btnAddData;
    Button btnview;
    Button btndel;
    Button btnupdate;
    ImageView imgview;
    ViewFlipper viewflips;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        editImagename = findViewById(R.id.image_name);
        btnAddData = findViewById(R.id.addbtn);
        btnview = findViewById(R.id.getbtn);
        viewflips = findViewById(R.id.viewflip);
        btndel = findViewById(R.id.deldata);
        btnupdate = findViewById(R.id.updatedata);
        viewAll();
        DeleteData();

        TextView txt = findViewById(R.id.text);
        txt.setSelected(true);

        Button buttonLoadImage = findViewById(R.id.btn1);
        buttonLoadImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent i = new Intent(Intent.ACTION_PICK,Uri.parse("content://media/internal/images/media"));
                        startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        myDb = new DatabaseHelper(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            final String x = getPath(uri);
            final String ImageName = editImagename.getText().toString();

            btnAddData.setOnClickListener(
                    new View.OnClickListener(){
                        public void onClick(View v){
                            boolean isInserted = myDb.insertData(ImageName, x);
                            if(isInserted == true)
                                Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                            else
                                Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                        }
                    }
            );

            btnupdate.setOnClickListener(
                    new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                boolean isUpdate = myDb.UpdateData(editImagename.getText().toString(), x);
                                if(isUpdate == true)
                                    Toast.makeText(MainActivity.this,"Data Update",Toast.LENGTH_LONG).show();
                                else
                                    Toast.makeText(MainActivity.this,"Data not Updated",Toast.LENGTH_LONG).show();
                            }
                        }
                );
            }

        }

    private String getPath(Uri uri) {
        if (uri==null) return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if (cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }

    public void viewAll() {
        btnview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getAllData();
                        if (res.getCount()!=0)
                            while (res.moveToNext()) {
                                Uri mUri = Uri.parse(res.getString(1));
                                imgview = new ImageView(getApplicationContext());
                                imgview.setImageURI(mUri);
                                viewflips.addView(imgview);
                            }
                    }
                }
        );
    }

    public void DeleteData() {
        btndel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editImagename.getText().toString());
                        if(deletedRows > 0)
                            Toast.makeText(MainActivity.this,"Data Deleted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Deleted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
