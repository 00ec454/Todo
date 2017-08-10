package com.dharmesh.todo.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;


@Entity
public class Note {

    public Note(){

    }

    @PrimaryKey(autoGenerate = true)
    public long nid;

    @ColumnInfo
    public String title;

    @ColumnInfo
    public String body;

    @ColumnInfo
    public Integer status;

    @ColumnInfo
    public Integer priority;

    @ColumnInfo(name = "deleted_at")
    public Long deletedAt = null;

    @ColumnInfo(name = "created_at")
    public Long createAt = new Date().getTime();

    public Note(Builder builder) {
        nid = builder.nid;
        title = builder.title;
        body = builder.body;
        status = builder.status;
        priority = builder.priority;
        deletedAt = builder.deletedAt;
        createAt = builder.createAt;
    }

    @Ignore
    public static Builder newBuilder() {
        return new Builder();
    }

    @Override @Ignore
    public String toString() {
        return "Note{" +
                "nid=" + nid +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", deletedAt=" + deletedAt +
                ", createAt=" + createAt +
                '}';
    }

    public static final class Builder {
        private long nid;
        private String title;
        private String body;
        private Integer status;
        private Integer priority;
        private Long deletedAt;
        private Long createAt;

        private Builder() {
        }

        public Builder nid(long val) {
            nid = val;
            return this;
        }

        public Builder title(String val) {
            title = val;
            return this;
        }

        public Builder body(String val) {
            body = val;
            return this;
        }

        public Builder status(Integer val) {
            status = val;
            return this;
        }

        public Builder priority(Integer val) {
            priority = val;
            return this;
        }

        public Builder deletedAt(Long val) {
            deletedAt = val;
            return this;
        }

        public Builder createAt(Long val) {
            createAt = val;
            return this;
        }

        public Note build() {
            return new Note(this);
        }
    }
}
