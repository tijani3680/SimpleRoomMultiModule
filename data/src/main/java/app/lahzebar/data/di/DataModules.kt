package app.lahzebar.data.di

import android.content.Context
import androidx.room.Room
import app.lahzebar.data.CommentDataSourceImpl
import app.lahzebar.data.PostDataSourceImpl
import app.lahzebar.data.StoreDataSourceImpl
import app.lahzebar.data.UserDataSourceImpl
import app.lahzebar.data.local.RoomManager
import app.lahzebar.data.local.dao.CommentDao
import app.lahzebar.data.local.dao.PostDao
import app.lahzebar.data.local.dao.UserDao
import app.lahzebar.domain.api.CommentDataSource
import app.lahzebar.domain.api.PostDataSource
import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.api.UserDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
internal class DataModule {
    @Provides
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}

@Module
internal class RemoteModule {

    @Provides
    @Singleton
    fun gson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    fun roomDatabase(context: Context): RoomManager {
        return Room.databaseBuilder(context, RoomManager::class.java, "RubikDb")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun userDao(roomManager: RoomManager): UserDao {
        return roomManager.userDao
    }

    @Provides
    @Singleton
    fun postDao(roomManager: RoomManager): PostDao {
        return roomManager.postDao
    }

    @Provides
    @Singleton
    fun commentDao(roomManager: RoomManager): CommentDao {
        return roomManager.commentDao
    }
}

@Module
internal interface NetworkModule {

    @Binds
    @Singleton
    fun userDataSource(userDataSourceImpl: UserDataSourceImpl): UserDataSource

    @Binds
    @Singleton
    fun postDataSource(postDataSourceImpl: PostDataSourceImpl): PostDataSource

    @Binds
    @Singleton
    fun commentDataSource(commentDataSourceImpl: CommentDataSourceImpl): CommentDataSource

    @Binds
    @Singleton
    fun storeDataSource(storeDataSourceImpl: StoreDataSourceImpl): StoreDataSource
}
