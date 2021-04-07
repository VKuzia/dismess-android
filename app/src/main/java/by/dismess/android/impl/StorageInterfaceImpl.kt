package by.dismess.android.impl

import by.dismess.core.outer.StorageInterface
import com.snappydb.DB

class StorageInterfaceImpl(
    private val snappyDB : DB
) : StorageInterface {
    override suspend fun loadRawData(key: String): ByteArray = snappyDB.getBytes(key)

    override suspend fun saveRawData(key: String, data: ByteArray) {
        snappyDB.put(key, data)
    }
}