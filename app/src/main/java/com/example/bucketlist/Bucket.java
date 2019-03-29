package com.example.bucketlist;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "bucket_table")
public class Bucket implements Parcelable {

        @PrimaryKey(autoGenerate = true)
        private Long id;

        @ColumnInfo(name = "bucket")
        private String title;
        private String description;

    public Bucket(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeString(this.title);
        dest.writeString(this.description);
    }


    protected Bucket(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.title = in.readString();
        this.description = in.readString();
    }

    public static final Creator<Bucket> CREATOR = new Creator<Bucket>() {
        @Override
        public Bucket createFromParcel(Parcel source) {
            return new Bucket(source);
        }

        @Override
        public Bucket[] newArray(int size) {
            return new Bucket[size];
        }
    };
}
