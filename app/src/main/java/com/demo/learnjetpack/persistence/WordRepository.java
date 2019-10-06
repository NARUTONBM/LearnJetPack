package com.demo.learnjetpack.persistence;

import android.app.Application;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 0:36.
 */
public class WordRepository {

    private WordDao mWordDao;

    public WordRepository(Application application) {
        WordRoomDb db = WordRoomDb.getDatabase(application);
        mWordDao = db.wordDao();
    }

    public Flowable<List<Word>> getAllWord() {
        return mWordDao.getAllWord()
                .map(words -> words);
    }

    public Single<Long> insert(Word word) {
        //new InsertAsyncTask(mWordDao).execute(word);
        return mWordDao.insert(word)
                .map(Long::longValue);
    }

    public Single<Integer> deleteWord(Word word) {
        //new DeleteWordAsyncTask(mWordDao).execute(word);
        return mWordDao.deleteWord(word)
                .map(integer -> integer);
    }

    public Single<Integer> deleteAll() {
        //new DeleteAllWordsAsyncTask(mWordDao).execute();
        return mWordDao.deleteAll()
                .map(integer -> integer);
    }

    public Single<Integer> updateWord(Word word) {
        //new UpdateWordAsyncTask(mWordDao).execute(word);
        return mWordDao.updateWord(word)
                .map(integer -> integer);
    }
}
