package by.dismess.android.impl

import by.dismess.core.outer.StorageInterface

class StorageInterfaceImpl : StorageInterface {
    override suspend fun loadRawData(key: String): ByteArray {
        TODO("Not yet implemented")
    }

    override suspend fun saveRawData(key: String, data: ByteArray) {
        TODO("Not yet implemented")
    }
}