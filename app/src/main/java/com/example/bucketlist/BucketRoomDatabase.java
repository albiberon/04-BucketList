package com.example.bucketlist;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Bucket.class}, version = 1, exportSchema = false)
public abstract class BucketRoomDatabase extends RoomDatabase {

    private final static String NAME_DATABASE = "item_database";

    public abstract BucketDao bucketDao();

    private static volatile BucketRoomDatabase INSTANCE;

    static BucketRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (BucketRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            BucketRoomDatabase.class, NAME_DATABASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
