package app.lahzebar.domain.api

import kotlinx.coroutines.flow.Flow

interface StoreDataSource {
    fun getGeneratePrimaryData(): Flow<Boolean>
    suspend fun saveGeneratePrimaryData(generateData: Boolean)
}
