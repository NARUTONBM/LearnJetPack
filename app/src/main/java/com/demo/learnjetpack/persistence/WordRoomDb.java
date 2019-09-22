package com.demo.learnjetpack.persistence;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import io.reactivex.disposables.Disposable;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 0:24.
 */
@Database(entities = {Word.class}, version = 2, exportSchema = false)
public abstract class WordRoomDb extends RoomDatabase {

    private static final String TAG = WordRoomDb.class.getSimpleName();
    private static WordRoomDb INSTANCE;

    public abstract WordDao wordDao();

    static WordRoomDb getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), WordRoomDb.class, "word_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            String[] words = {"a", "b", "c"};
            WordDao wordDao = INSTANCE.wordDao();
            Disposable subscribe = wordDao.getAnyWord()
                    .filter(wordList -> wordList.size() == 0)
                    .map(wordList -> {
                        for (String word : words) {
                            Word newWord = new Word(word);
                            wordDao.insert(newWord)
                                    .subscribe(aLong -> Log.d(TAG, String.format("在第%d行插入一条新数据", aLong)));
                        }

                        return words.length;
                    })
                    .subscribe(integer -> Log.d(TAG, "初始化数据库，新增了 " + integer + " 条数据。"));
        }
    };
}
