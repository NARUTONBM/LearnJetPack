package com.demo.learnjetpack;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.demo.learnjetpack.persistence.Word;
import com.demo.learnjetpack.persistence.WordRepository;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 23:43.
 */
public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
    }

    Single<Long> insertWord(Word word) {
        return mRepository.insert(word);
    }

    Single<Integer> deleteWord(Word word) {
        return mRepository.deleteWord(word);
    }

    Single<Integer> deleteAll() {
        return mRepository.deleteAll();
    }

    Single<Integer> updateWord(Word word) {
        return mRepository.updateWord(word);
    }

    Flowable<List<Word>> getAllWords() {
        return mRepository.getAllWord();
    }
}
