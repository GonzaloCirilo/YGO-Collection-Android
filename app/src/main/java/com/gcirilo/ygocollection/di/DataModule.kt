package com.gcirilo.ygocollection.di

import android.content.Context
import androidx.room.Room
import com.gcirilo.ygocollection.BuildConfig
import com.gcirilo.ygocollection.data.local.YGOCollectionDatabase
import com.gcirilo.ygocollection.data.remote.YGOCardService
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val builder = Retrofit.Builder()
            .baseUrl("https://db.ygoprodeck.com/api/v7/")
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder()
                        .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                        .create()
                )
            )
            .apply {
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BASIC
                    }
                    val httpClient = OkHttpClient.Builder()
                        .addInterceptor(logging).build()
                    client(httpClient)
                }
            }
        return builder.build()
    }


    @Singleton
    @Provides
    fun provideYgoApi(retrofit: Retrofit): YGOCardService {
        return retrofit.create(YGOCardService::class.java)
    }

    @Singleton
    @Provides
    fun provideYGOCollectionDatabase(@ApplicationContext context: Context): YGOCollectionDatabase {
        return Room.databaseBuilder(
            context,
            YGOCollectionDatabase::class.java,
            "ygo_colletion_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }
}