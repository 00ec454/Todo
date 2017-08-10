package com.dharmesh.todo;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dharmesh.todo.data.Note;
import com.dharmesh.todo.ui.CreateNoteActivity;

import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private static final String TAG = NoteAdapter.class.getSimpleName();
    private List<Note> notes;
    private Context cnxt;
    private SparseIntArray colorMap = new SparseIntArray();

    public NoteAdapter(List<Note> notes, Context cnxt) {
        this.notes = notes;
        this.cnxt = cnxt;
        colorMap.put(0, Color.argb(255, 255, 127, 80));
        colorMap.put(1, Color.YELLOW);
        colorMap.put(2, Color.GREEN);
    }

    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NoteAdapter.ViewHolder holder, int i) {
        Note note = notes.get(i);
        holder.tvContent.setText(note.body);
        holder.tvTitle.setText(note.title);
        holder.tvPriory.setText(cnxt.getResources().getStringArray(R.array.priority)[note.priority]);
        holder.tvStatus.setText(cnxt.getResources().getStringArray(R.array.status)[note.status]);
        holder.itemView.setBackgroundColor(colorMap.get(note.priority));
        if (note.status == 1) {
            holder.tvTitle.setPaintFlags(holder.tvTitle.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public TextView tvTitle;
        public TextView tvContent;
        public TextView tvPriory;
        public TextView tvStatus;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mView.setOnClickListener(this);
            tvTitle = view.findViewById(R.id.tvTitle);
            tvContent = view.findViewById(R.id.tvContent);
            tvPriory = view.findViewById(R.id.tvPriory);
            tvStatus = view.findViewById(R.id.tvStatus);
        }

        @Override
        public void onClick(View v) {
            Note note = notes.get(getAdapterPosition());
            Intent intent = new Intent(cnxt, CreateNoteActivity.class);
            intent.putExtra("_ID", note.nid);
            cnxt.startActivity(intent);
        }
    }

}
