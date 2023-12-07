package com.example.dictionaryapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.dictionaryapp.domain.model.Meaning

@Entity
data class WordInfoEntity(
    val word: String,
    val phonetic: String,
    val origin: String,
    val meanings: List<Meaning>,
    @PrimaryKey val id: Int? = null
)
