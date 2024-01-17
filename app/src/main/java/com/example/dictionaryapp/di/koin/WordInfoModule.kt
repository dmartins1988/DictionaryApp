package com.example.dictionaryapp.di.koin

import com.example.dictionaryapp.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryapp.domain.usecase.GetWordInfo
import org.koin.dsl.bind
import org.koin.dsl.module


val module = module {
    single { GetWordInfo(get()) }
    single { WordInfoRepositoryImpl(get(), get()) }
    single {  }
}



