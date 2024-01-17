package com.example.dictionaryapp.di.koin

import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dictionaryapp.data.local.Converters
import com.example.dictionaryapp.data.local.WordInfoDatabase
import com.example.dictionaryapp.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryapp.data.util.GsonParser
import com.example.dictionaryapp.domain.usecase.GetWordInfo
import com.example.dictionaryapp.presentation.WordInfoViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val module = module {
    single { GetWordInfo(get()) }
    single { WordInfoRepositoryImpl(get(), get()) }
    viewModel { WordInfoViewModel(get()) }

    single<RoomDatabase> { Room.databaseBuilder(
        context = androidContext(),
        klass = WordInfoDatabase::class.java,
        name = "word_db"
    ).addTypeConverter(Converters(GsonParser(Gson())))
        .build()  }
}



