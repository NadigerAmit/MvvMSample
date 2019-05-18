package com.example.amitnadiger.mvvm.repository;
import android.app.Application;
import android.os.AsyncTask;

import com.example.amitnadiger.mvvm.room.NoteDao;
import com.example.amitnadiger.mvvm.room.NoteDatabase;
import com.example.amitnadiger.mvvm.room.Note;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NoteRepository {
    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();// Normally we cant call the abstract methods
        // Since in the below code NoteDatabase instance is created builder , room database generates all the necessary code.
        allNotes = noteDao.getAllNotes();
    }
    public void insert(Note note) {
        new InsertNoteAsyncTask(noteDao).execute(note);
    }
    public void update(Note note) {
        new updateNoteAsyncTask(noteDao).execute(note);
    }
    public void delete(Note note) {
        new deleteAsyncTask(noteDao).execute(note);
    }
    public void deleteAllNotes() {
        new deleteAllAsynctask(noteDao).execute();
    }
    public  LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteDao noteDao;

        private InsertNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }

    private static class updateNoteAsyncTask extends AsyncTask<Note,Void,Void> {
        private  NoteDao noteDao;

        private updateNoteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.update(notes[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Note,Void,Void> {
        private NoteDao noteDao;

        private deleteAsyncTask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Note... notes) {
            noteDao.delete(notes[0]);
            return null;
        }
    }

    private static class  deleteAllAsynctask extends AsyncTask<Void,Void,Void> {
        private NoteDao noteDao;

        deleteAllAsynctask(NoteDao noteDao) {
            this.noteDao = noteDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.deleteAllNotes();
            return null;
        }
    }
}
