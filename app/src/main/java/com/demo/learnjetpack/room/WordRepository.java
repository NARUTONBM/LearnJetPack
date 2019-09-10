package com.demo.learnjetpack.room;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import androidx.lifecycle.LiveData;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 0:36.
 */
public class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWord;

    public WordRepository(Application application) {
        WordRoomDb db = WordRoomDb.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWord = mWordDao.getAllWord();
    }

    public LiveData<List<Word>> getAllWord() {
        return mAllWord;
    }

    public void insert(Word word) {
        new InsertAsyncTask(mWordDao).execute(word);
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mAsyncTaskDao;

        InsertAsyncTask(WordDao asyncTaskDao) {
            mAsyncTaskDao = asyncTaskDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mAsyncTaskDao.insert(words[0]);
            return null;
        }
    }
}
