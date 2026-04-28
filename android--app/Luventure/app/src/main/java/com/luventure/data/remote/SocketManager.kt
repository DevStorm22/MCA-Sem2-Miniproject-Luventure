package com.luventure.data.remote

import io.socket.client.IO
import io.socket.client.Socket

object SocketManager {

    private var socket: Socket? = null

    fun getSocket(): Socket {

        if (socket == null) {

            socket = IO.socket(
                "http://10.0.2.2:5000"
            )
        }

        return socket!!
    }

    fun connect() {
        if (!getSocket().connected()) {
            getSocket().connect()
        }
    }

    fun disconnect() {
        socket?.disconnect()
    }
}