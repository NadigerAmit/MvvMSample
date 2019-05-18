package com.example.amitnadiger.mvvm;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Iterator;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.amitnadiger.mvvm.room.Note;
import com.example.amitnadiger.mvvm.viewModel.NoteViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // Attache the view model to activity
    private static String TAG = "MAINACTIVITY";
    private NoteViewModel noteViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);


        // we should not create the NoteViewModel instance with new , as it will create new view model each time activity is re-created.
        // instead we should get it from android system i.e viewModel providers , so that viewModel knows to which activity or view it is attached to
        // when activity or fragment it is attached to is destroyed , viewModel can remove itself to avoid memory leaks.
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        // since we pass this , view model know which activity or fragment it is scoped to.
        // we passed instance of NoteViewModel.class to tell , which view model it should be attached to .

        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable  List<Note> notes) {
                // update the RecyclerView
                Toast.makeText(MainActivity.this,"OmShreeGaneshayaNamah Onchanged",Toast.LENGTH_SHORT).show();
                displayAllNotes(notes);
                adapter.setNotes(notes);
            }
        });
        // The above live data should be attached to activity , so that live data knows which activity observing
        // and live data will only update if the attached view (activity or fragment is in the foreground) and will not update if the view if in background or destroyed

    }
    void displayAllNotes(List<Note> notes) {
        Iterator<Note> itr = notes.iterator();
        while(itr.hasNext()) {
            Note note = itr.next();
            Log.i(TAG,"ID ="+note.getId());
            Log.i(TAG,"Title ="+note.getTitle());
            Log.i(TAG,"Description ="+note.getDescription());
            Log.i(TAG,"Priority ="+note.getPriority());
            Toast.makeText(MainActivity.this,""+note.getId()+" "+note.getTitle()+" "+note.getDescription()+" "+note.getPriority(),Toast.LENGTH_SHORT).show();
        }
    }
}
