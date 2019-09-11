package com.demo.learnjetpack.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * @author narut.
 * @Date 2019-09-10.
 * @Time 0:20.
 */
@Dao
public interface WordDao {

    /**
     * 插入一个 word 数据
     *
     * @param word word 数据
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Word word);

    /**
     * 删除所有数据
     */
    @Query("DELETE FROM word_table")
    void deleteAll();

    /**
     * 查询返回表中所有 word 数据
     *
     * @return word 数据集合
     */
    @Query("SELECT * from word_table ORDER BY word ASC")
    LiveData<List<Word>> getAllWord();

    /**
     * 查询表中第2条开始所有数据
     *
     * @return 表中第2条开始所有 word 数据数组
     */
    @Query("SELECT * from word_table LIMIT 1")
    Word[] getAnyWord();
}
