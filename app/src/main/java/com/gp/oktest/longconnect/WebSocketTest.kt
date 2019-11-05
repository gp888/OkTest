package com.gp.oktest.longconnect

import java.net.URI

class WebSocketTest {

    lateinit var client : MyWebsocketClient

    fun main(args: Array<String>) {

        client = MyWebsocketClient(URI("ws://echo.websocket.org"))


        try {
            client.connectBlocking()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }


        if (client != null && client.isOpen()) {
            client.send("你好")
        }

    }

    private fun closeConnect() {
        try {
            if (null != client) {
                client.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}