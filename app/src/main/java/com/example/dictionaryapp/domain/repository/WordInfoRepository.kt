package com.example.dictionaryapp.domain.repository

import com.example.dictionaryapp.core.util.Resource
import com.example.dictionaryapp.domain.model.WordInfo
import kotlinx.coroutines.flow.Flow

interface WordInfoRepository {
    fun getWordInfo(word: String): Flow<Resource<List<WordInfo>>>
}