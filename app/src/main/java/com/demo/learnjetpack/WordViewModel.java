package com.demo.learnjetpack;

import android.app.Application;

import com.demo.learnjetpack.room.Word;
import com.demo.learnjetpack.room.WordRepository;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 23:43.
 */
public class WordViewModel extends AndroidViewModel {

    private WordRepository mRepository;
    private LiveData<List<Word>> mAllWords;

    public WordViewModel(@NonNull Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWord();
    }

    public void insertWord(Word word) {
        mRepository.insert(word);
    }

    public void deleteWord(Word word) {
        mRepository.deleteWord(word);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }

    public void updateWord(Word word) {
        mRepository.updateWord(word);
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }
}
