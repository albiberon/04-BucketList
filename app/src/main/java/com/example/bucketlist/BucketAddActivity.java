package com.example.bucketlist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class BucketAddActivity extends AppCompatActivity {

    EditText title;
    EditText description;
    Button create;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bucket_add);

        title = findViewById(R.id.titleEditText);
        description = findViewById(R.id.descriptionEditText);
        create = findViewById(R.id.button);

        create.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final Bucket bucket = new Bucket(title.getText().toString(), description.getText().toString());


                Intent intent = new Intent();
                intent.putExtra(MainActivity.ITEM, bucket);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });
    }
}
