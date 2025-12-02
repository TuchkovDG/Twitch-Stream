package com.example.twitchstreamer.data.local.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.example.twitchstreamer.data.local.AppDatabase
import com.example.twitchstreamer.data.local.USER_CONFIG_STORAGE
import com.example.twitchstreamer.data.local.dao.ViewerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Qualifier
import javax.inject.Singleton

private const val APP_NAME = "app.db"

@Qualifier
@Retention(AnnotationRetention.BINARY)
internal annotation class UserConfigDataStore

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Provides
    @Singleton
    @UserConfigDataStore
    fun provideUserConfigDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = {
                context.preferencesDataStoreFile(name = USER_CONFIG_STORAGE)
            }
        )

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = APP_NAME
        ).build()

    @Provides
    fun provideViewerDao(db: AppDatabase): ViewerDao = db.viewerDao()
}
