package com.dharmesh.todo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dharmesh.todo.NoteAdapter;
import com.dharmesh.todo.R;
import com.dharmesh.todo.data.AppDatabase;
import com.dharmesh.todo.data.Note;

import java.lang.ref.WeakReference;
import java.util.List;

public class NotesActivity extends BaseActivity {

    RecyclerView notesRV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        notesRV = findViewById(R.id.notes);
        setTitle("Notes");
        new LoadDataAsync(db, this).execute();

    }

    private void renderRecyclerView(List<Note> notes) {
        notesRV.setLayoutManager(new LinearLayoutManager(this));
        NoteAdapter adapter = new NoteAdapter(notes, this);
        notesRV.setAdapter(adapter);
        notesRV.setItemAnimator(new DefaultItemAnimator());
    }


    public void createNote(View view) {
        startActivity(CreateNoteActivity.class);
    }

    static class LoadDataAsync extends AsyncTask<Void, Void, List<Note>> {

        private AppDatabase db;
        private WeakReference<NotesActivity> activityRef;

        LoadDataAsync(AppDatabase db, NotesActivity activity) {
            this.db = db;
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        protected List<Note> doInBackground(Void... voids) {
            return db.noteDao().getAll();
        }

        @Override
        protected void onPostExecute(List<Note> notes) {
            if (activityRef != null) {
                activityRef.get().renderRecyclerView(notes);
                activityRef.clear();
            }
        }
    }
}
