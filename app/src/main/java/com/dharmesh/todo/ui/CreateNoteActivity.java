package com.dharmesh.todo.ui;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.ArrayRes;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.dharmesh.todo.R;
import com.dharmesh.todo.data.AppDatabase;
import com.dharmesh.todo.data.Note;

import java.lang.ref.WeakReference;


public class CreateNoteActivity extends BaseActivity {

    public EditText tvTitle;
    public EditText tvContent;
    public Spinner sPriority;
    public Spinner sStatus;
    private int status;
    private int priority;
    private static final String TAG = CreateNoteActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        enableToolbarBackButton();

        tvTitle = findViewById(R.id.title);
        tvContent = findViewById(R.id.body);
        sPriority = findViewById(R.id.priority_spinner);
        sStatus = findViewById(R.id.status_spinner);

        sPriority.setAdapter(buildSpinnerAdapter(R.array.priority));
        sPriority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                priority = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        sStatus.setAdapter(buildSpinnerAdapter(R.array.status));
        sStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                status = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        long id = getIntent().getLongExtra("_ID", 0);
        if (id > 0) {
            new InsertAsync(this, db, "SHOW")
                    .execute(Note.newBuilder()
                            .nid(id)
                            .build());
        }
    }

    private void renderNote(Note note) {
        tvTitle.setText(note.title);
        tvContent.setText(note.body);
        sStatus.setSelection(note.status);
        sPriority.setSelection(note.priority);
        tvTitle.setTag(note.nid);
    }

    private SpinnerAdapter buildSpinnerAdapter(@ArrayRes int id) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                id, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    public void save(View view) {
        if ("".equals(getText(tvTitle))) {
            tvTitle.setError("Please Enter Title");
            return;
        } else if ("".equals(getText(tvContent))) {
            tvContent.setError("Please Enter Content");
            return;
        }
        Note note = Note.newBuilder()
                .nid(tvTitle.getTag() != null ? (Long) tvTitle.getTag() : 0)
                .title(getText(tvTitle))
                .body(getText(tvContent))
                .priority(priority)
                .status(status)
                .build();
        new InsertAsync(this, db, "INSERT").execute(note);

        toast("Note Saved...");
        finish();
        startActivity(NotesActivity.class);
    }


    @Override
    public int getMenuFile() {
        return R.menu.menu_create_note;
    }

    public void delete(MenuItem item) {
        Note note = Note.newBuilder()
                .nid((Long) tvTitle.getTag())
                .deletedAt(System.currentTimeMillis()).build();

        new InsertAsync(this, db, "DELETE").execute(note);
        toast("Note deleted");
        finish();
        startActivity(NotesActivity.class);
    }

    static class InsertAsync extends AsyncTask<Note, Void, Note> {

        private AppDatabase db;
        private String operationType;
        private WeakReference<CreateNoteActivity> activityRef;

        InsertAsync(CreateNoteActivity activity, AppDatabase db, String operationType) {
            this.db = db;
            this.operationType = operationType;
            this.activityRef = new WeakReference<>(activity);
        }

        @Override
        protected Note doInBackground(Note... notes) {
            if ("SHOW".equals(operationType)) {
                Log.i(TAG, "doInBackground: notes[0].nid :: " + notes[0].nid);
                return db.noteDao().loadNote(notes[0].nid);
            } else if (notes[0].nid > 0) {
                db.noteDao().update(notes[0]);
            } else if ("INSERT".equals(operationType)) {
                db.noteDao().insertAll(notes);
            }
            return null;
        }

        @Override
        protected void onPostExecute(Note note) {
            if (note != null && activityRef.get() != null) {
                activityRef.get().renderNote(note);
                activityRef.clear();
            }
        }
    }

}
