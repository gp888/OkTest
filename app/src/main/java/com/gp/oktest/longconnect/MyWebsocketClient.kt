package com.gp.oktest.longconnect

import android.util.Log
import org.java_websocket.client.WebSocketClient
import org.java_websocket.drafts.Draft_6455
import org.java_websocket.handshake.ServerHandshake
import java.net.URI



class MyWebsocketClient(serverUri : URI) : WebSocketClient(serverUri, Draft_6455()) {

    override fun onOpen(handshakedata: ServerHandshake?) {

    }

    override fun onClose(code: Int, reason: String?, remote: Boolean) {
    }

    override fun onMessage(message: String?) {
        Log.e("JWebSClientService", message)
    }

    override fun onError(ex: Exception?) {
    }
}
