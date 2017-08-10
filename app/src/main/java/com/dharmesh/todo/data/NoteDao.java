package com.dharmesh.todo.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Query("SELECT * FROM note WHERE deleted_at IS NULL ORDER BY nid DESC")
    List<Note> getAll();

    @Query("SELECT * FROM note WHERE nid = :nid AND deleted_at IS NULL")
    Note loadNote(long nid);

    @Insert
    void insertAll(Note... notes);

    @Delete
    void delete(Note note);

    @Update
    void update(Note note);
}
