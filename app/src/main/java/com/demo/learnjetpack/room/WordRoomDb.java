package com.demo.learnjetpack.room;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 0:24.
 */
@Database(entities = {Word.class}, version = 1, exportSchema = false)
public abstract class WordRoomDb extends RoomDatabase {

    public abstract WordDao wordDao();

    private static WordRoomDb INSTANCE;

    static WordRoomDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDb.class) {
                Room.databaseBuilder(context.getApplicationContext(), WordRoomDb.class, "word_database")
                        .fallbackToDestructiveMigration()
                        .addCallback(sCallback)
                        .build();
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopularDbAsync(INSTANCE).execute();
        }
    };

    private static class PopularDbAsync extends AsyncTask<Void, Void, Void> {
        private final WordDao mWordDao;
        String[] words = {"a", "b", "c"};

        public PopularDbAsync(WordRoomDb db) {
            mWordDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mWordDao.deleteAll();

            for (String s : words) {
                Word word = new Word(s);
                mWordDao.insert(word);
            }
            return null;
        }
    }
}
