package by.dismess.android

import by.dismess.core.network.TCPNetworkInterfaceImpl
import java.net.InetSocketAddress

class App {
    lateinit var network: TCPNetworkInterfaceImpl
    suspend fun start(address: InetSocketAddress) {
        network = TCPNetworkInterfaceImpl()
        network.start(address)
    }
}
