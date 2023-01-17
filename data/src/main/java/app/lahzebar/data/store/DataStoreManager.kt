package app.lahzebar.data.store

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class DataStoreManager @Inject constructor(
    private val context: Context,
) {

    private val Context.dataStore by preferencesDataStore(DATA_STORE_KEY)

    companion object ConstDataStore {
        private const val DATA_STORE_KEY = "data_store_key"
        private val MY_ACCOUNT_KEY = stringPreferencesKey("my_account_key")
        private val GENERATE_PRIMARY_DATA_KEY = booleanPreferencesKey("generate_primary_data_key")
    }

    fun getGeneratePrimaryData() = context.dataStore.data.map {
        it[GENERATE_PRIMARY_DATA_KEY] ?: false
    }

    suspend fun saveGeneratePrimaryData(generateData: Boolean): Unit = with(context) {
        dataStore.edit { it[GENERATE_PRIMARY_DATA_KEY] = generateData }
    }
}
