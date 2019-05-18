package com.example.amitnadiger.mvvm.viewModel;

import android.app.Application;

import com.example.amitnadiger.mvvm.repository.NoteRepository;
import com.example.amitnadiger.mvvm.room.Note;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;
    public NoteViewModel(@NonNull Application application) { // This is app context , we should never use activity context
        // as when the activity get destroyed , view model holds activity context , then it leads to memory leak and whole purpose of
        // making the view independent of data is defeated.
        // But we need context to instantiate the database instance , so we need application context here.
        super(application);
        noteRepository = new NoteRepository(application);
        allNotes = noteRepository.getAllNotes();
    }
    // Here View class dont call the repository directly , it calls via ViewModel ,
    // Hence it has to provide wrapper class for all database operations

    public void insert(Note note) {
        noteRepository.insert(note);
    }
    public void update(Note note) {
        noteRepository.update(note);
    }
    public void delete(Note note) {
        noteRepository.delete(note);
    }
    public void deleteAllNotes() {
        noteRepository.deleteAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }
}

