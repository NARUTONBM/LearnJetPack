package com.demo.learnjetpack.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

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
     * 删除单条数据
     *
     * @param word 待删除的 word 数据
     */
    @Delete
    void deleteWord(Word word);

    /**
     * 更新一条 word 数据
     *
     * @param word 待更新的 word 数据
     */
    @Update
    void updateWord(Word... word);

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
