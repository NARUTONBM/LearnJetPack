package com.demo.learnjetpack.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;

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
     * @return 新增行的 id
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Single<Long> insert(Word word);

    /**
     * 根据 id 检索 word
     *
     * @param id 索引
     * @return 一条 word 数据
     */
    @Query("SELECT * FROM word_table WHERE id = :id")
    Flowable<Word> getWordById(long id);

    /**
     * 删除所有数据
     */
    @Query("DELETE FROM word_table")
    Single<Integer> deleteAll();

    /**
     * 删除单条数据
     *
     * @param word 待删除的 word 数据
     * @return 删除行的 id
     */
    @Delete
    Single<Integer> deleteWord(Word word);

    /**
     * 更新一条 word 数据
     *
     * @param word 待更新的 word 数据
     * @return 修改行的 id
     */
    @Update
    Single<Integer> updateWord(Word... word);

    /**
     * 查询返回表中所有 word 数据
     *
     * @return word 数据集合
     */
    @Query("SELECT * from word_table ORDER BY id ASC")
    Flowable<List<Word>> getAllWord();

    /**
     * 查询表中第2条开始所有数据
     *
     * @return 表中第2条开始所有 word 数据数组
     */
    @Query("SELECT * from word_table LIMIT 1")
    Flowable<List<Word>> getAnyWord();
}
