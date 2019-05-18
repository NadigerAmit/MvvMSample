package com.example.amitnadiger.mvvm.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.amitnadiger.mvvm.room.Note;
import com.example.amitnadiger.mvvm.room.NoteDao;

@Database(entities = {Note.class},version = 1)
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase instance ;
    public  abstract NoteDao noteDao(); // Normally we cant call the abstract methods
    // Since in the below code NoteDatabase instance is created via builder , room database generates all the necessary code.

    public static synchronized NoteDatabase getInstance(Context context) {
            if(instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        NoteDatabase.class, "note_database.db")
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
            return instance;
    }
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new populateDbAsyncTask(instance).execute();
        }
    };
    private static class populateDbAsyncTask extends AsyncTask<Void,Void,Void> {
        private NoteDao noteDao;

        private  populateDbAsyncTask(NoteDatabase db) {
            this.noteDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            noteDao.insert(new Note("Title1","Description1",1));
            noteDao.insert(new Note("Title2","Description2",2));
            noteDao.insert(new Note("Title3","Description3",3));
            return null;
        }
    }

}
