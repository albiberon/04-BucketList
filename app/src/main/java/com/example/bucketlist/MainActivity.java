package com.example.bucketlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements RecyclerView.OnItemTouchListener{

    private RecyclerView recyclerView;
    private List<Bucket> bucketList;
    private BucketAdapter bucketAdapter;
    private Executor executor = Executors.newSingleThreadExecutor();
    private BucketRoomDatabase db;
    private GestureDetector mGestureDetector;

    public static final int REQUESTCODE = 1;
    public static final String ITEM = "Bucket";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bucketList = new ArrayList<>();
        db = BucketRoomDatabase.getDatabase(this);

        recyclerView = findViewById(R.id.rv_bucket_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(new BucketAdapter(bucketList));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BucketAddActivity.class);
                startActivityForResult(intent, REQUESTCODE);
            }
        });


        //Delete item with long click on the item
        mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null) {
                    int adapterPosition = recyclerView.getChildAdapterPosition(child);
                    deleteBucket(bucketList.get(adapterPosition));
                }
            }
        });

        recyclerView.addOnItemTouchListener(this);

        getAllBuckets();
    }

    private void getAllBuckets() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                bucketList = db.bucketDao().getAllBuckets();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        updateUI();
                    }
                });
            }
        });
    }

    private void deleteBucket(final Bucket bucket) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketDao().delete(bucket);
                getAllBuckets();
            }
        });
    }

    private void insertBucket(final Bucket bucket) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                db.bucketDao().insert(bucket);
                getAllBuckets();
            }
        });
    }


    private void updateUI() {

        if (bucketAdapter == null) {
                bucketAdapter = new BucketAdapter(bucketList);
                recyclerView.setAdapter(bucketAdapter);
        } else {
            bucketAdapter.swapList(bucketList);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUESTCODE) {
            if (resultCode == RESULT_OK) {
                Bucket addBucket = data.getParcelableExtra(MainActivity.ITEM);
                insertBucket(addBucket);
            }
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        mGestureDetector.onTouchEvent(motionEvent);
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
