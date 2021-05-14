package by.dismess.android

import by.dismess.core.network.RUDPNetworkInterfaceImpl
import java.net.InetSocketAddress

class App {
    lateinit var network: RUDPNetworkInterfaceImpl
    suspend fun start(address: InetSocketAddress) {
        network = RUDPNetworkInterfaceImpl()
        network.start(address)
    }
}
