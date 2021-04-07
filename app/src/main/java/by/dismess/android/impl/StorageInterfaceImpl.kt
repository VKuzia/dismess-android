package by.dismess.android.impl

import by.dismess.core.outer.StorageInterface
import com.snappydb.DB

class StorageInterfaceImpl(
    private val snappyDB : DB
) : StorageInterface {
    override suspend fun exists(key: String): Boolean = snappyDB.exists(key)

    override suspend fun forget(key: String): Unit = snappyDB.del(key)

    override suspend fun loadRawData(key: String): ByteArray = snappyDB.getBytes(key)

    override suspend fun saveRawData(key: String, data: ByteArray): Unit = snappyDB.put(key, data)
}
