package com.demo.learnjetpack.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

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
                        .build();
            }
        }
        return INSTANCE;
    }
}
