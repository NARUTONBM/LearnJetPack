package com.demo.learnjetpack.room;

import org.jetbrains.annotations.NotNull;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 0:02.
 */
@Entity(tableName = "word_table")
public class Word {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long mId;

    @NonNull
    @ColumnInfo(name = "word")
    private String mWord;

    public Word(@NonNull String word) {
        mWord = word;
    }

    @Ignore
    public Word(long id, @NonNull String word) {
        mId = id;
        mWord = word;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getId() {
        return mId;
    }

    @NotNull
    public String getWord() {
        return mWord;
    }
}
