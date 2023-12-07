package com.example.dictionaryapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.dictionaryapp.data.local.entity.WordInfoEntity

@Dao
interface WordInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWordInfo(infos: List<WordInfoEntity>)

    @Query("DELETE FROM wordinfoentity  WHERE word IN(:words)")
    suspend fun deleteWordInfo(words: List<String>)

    @Query("SELECT * FROM wordinfoentity WHERE word LIKE '%' || :word || '%'")
    suspend fun getWordInfo(word: String): List<WordInfoEntity>
}