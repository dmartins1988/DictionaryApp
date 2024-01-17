package com.example.dictionaryapp.di.koin

import androidx.room.Room
import com.example.dictionaryapp.data.local.Converters
import com.example.dictionaryapp.data.local.WordInfoDatabase
import com.example.dictionaryapp.data.remote.DictionaryApi
import com.example.dictionaryapp.data.repository.WordInfoRepositoryImpl
import com.example.dictionaryapp.data.util.GsonParser
import com.example.dictionaryapp.domain.repository.WordInfoRepository
import com.example.dictionaryapp.domain.usecase.GetWordInfo
import com.example.dictionaryapp.presentation.WordInfoViewModel
import com.google.gson.Gson
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val module = module {
    single { GetWordInfo(get()) }

    single<WordInfoRepository> { WordInfoRepositoryImpl(get(), get()) }

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = WordInfoDatabase::class.java,
            name = "word_db"
        ).addTypeConverter(Converters(GsonParser(Gson())))
            .build().dao
    }

    single<DictionaryApi> {
        Retrofit.Builder()
            .baseUrl(DictionaryApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(DictionaryApi::class.java)
    }

    viewModel { WordInfoViewModel(get()) }
}



