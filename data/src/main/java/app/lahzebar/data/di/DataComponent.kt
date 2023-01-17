package app.lahzebar.data.di

import android.content.Context
import app.lahzebar.domain.api.CommentDataSource
import app.lahzebar.domain.api.PostDataSource
import app.lahzebar.domain.api.StoreDataSource
import app.lahzebar.domain.api.UserDataSource
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        RemoteModule::class,
        DataModule::class,
        NetworkModule::class
    ]
)
interface DataComponent {
    fun userDataSource(): UserDataSource
    fun postDataSource(): PostDataSource
    fun commentDataSource(): CommentDataSource
    fun storeDataSource(): StoreDataSource

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder
        fun build(): DataComponent
    }
}
