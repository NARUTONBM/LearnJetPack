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

    public void deleteWord(Word word) {
        new DeleteWordAsyncTask(mWordDao).execute(word);
    }

    public void deleteAll() {
        new DeleteAllWordsAsyncTask(mWordDao).execute();
    }

    public void updateWord(Word word) {
        new UpdateWordAsyncTask(mWordDao).execute(word);
    }

    private static class InsertAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mInsertDao;

        InsertAsyncTask(WordDao insertDao) {
            mInsertDao = insertDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mInsertDao.insert(words[0]);
            return null;
        }
    }

    private static class DeleteWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mDeleteWordDao;

        DeleteWordAsyncTask(WordDao deleteWordDao) {
            mDeleteWordDao = deleteWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mDeleteWordDao.deleteWord(words[0]);
            return null;
        }
    }

    private static class DeleteAllWordsAsyncTask extends AsyncTask<Void, Void, Void> {
        private WordDao mDeleteAllWordsDao;

        DeleteAllWordsAsyncTask(WordDao deleteAllWordsDao) {
            mDeleteAllWordsDao = deleteAllWordsDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mDeleteAllWordsDao.deleteAll();
            return null;
        }
    }

    private static class UpdateWordAsyncTask extends AsyncTask<Word, Void, Void> {

        private WordDao mUpdateWordDao;

        UpdateWordAsyncTask(WordDao updateWordDao) {
            mUpdateWordDao = updateWordDao;
        }

        @Override
        protected Void doInBackground(Word... words) {
            mUpdateWordDao.updateWord(words[0]);
            return null;
        }
    }
}
