package com.example.bucketlist;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BucketDao {

    @Insert
    void insert(Bucket bucket);

    @Delete
    void delete(Bucket bucket);

    @Delete
    void delete(List<Bucket> buckets);

    @Query("SELECT * from bucket_table")
    List<Bucket> getAllBuckets();
}
