package app.lahzebar.data

import app.lahzebar.data.store.DataStoreManager
import app.lahzebar.domain.api.StoreDataSource
import com.google.gson.Gson
import dagger.Lazy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class StoreDataSourceImpl @Inject constructor(
    private val dataStoreManager: Lazy<DataStoreManager>,
    private val gson: Gson,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StoreDataSource {
    override fun getGeneratePrimaryData(): Flow<Boolean> =
        dataStoreManager.get().getGeneratePrimaryData().flowOn(ioDispatcher)

    override suspend fun saveGeneratePrimaryData(generateData: Boolean) = withContext(ioDispatcher) {
        dataStoreManager.get().saveGeneratePrimaryData(generateData)
    }
}
