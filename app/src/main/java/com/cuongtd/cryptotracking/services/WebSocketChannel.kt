package com.cuongtd.cryptotracking

import com.cuongtd.cryptotracking.models.RawData
import com.cuongtd.cryptotracking.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString

interface IWebSocketChannel {
    fun getIncoming(): Flow<RawData>
    fun isClosed(): Boolean
    fun close(
        code: Int = 1000,
        reason: String? = null
    )

    fun send(data: RawData)
}

class WebSocketChannel(
    private val scope: CoroutineScope
) : IWebSocketChannel {

    private val socket: WebSocket
    private val incoming = Channel<RawData>()
    private val outgoing = Channel<RawData>()
    private val incomingFlow: Flow<RawData> = incoming.consumeAsFlow()

    init {
        val okHttpClient = OkHttpClient.Builder()
            .build()

        val request = Request.Builder()
            .url(Constants.WS_URL)
            .build()

        socket =
            okHttpClient.newWebSocket(request, WebSocketChannelListener(incoming, outgoing))

        // Trigger shutdown of the dispatcher's executor so this process can exit cleanly.
        okHttpClient.dispatcher.executorService.shutdown()

        // Everything that goes into the outgoing channel is sent to the web socket.
        // Everything that gets into the incoming channel is sent to incomingFlow.
        scope.launch(Dispatchers.IO) {
            try {
                outgoing.consumeEach {
                    socket.send(it.json)
                }
            } finally {
                close()
            }
        }
    }

    override fun getIncoming(): Flow<RawData> {
        return incomingFlow
    }

    override fun isClosed(): Boolean {
        return incoming.isClosedForReceive || outgoing.isClosedForSend
    }

    override fun close(
        code: Int,
        reason: String?
    ) {
        scope.launch(Dispatchers.IO) {
            socket.close(code, reason) // note: Channels will close in WebSocket.onClosed
        }
    }

    override fun send(data: RawData) {
        scope.launch(Dispatchers.IO) {
            outgoing.send(data)
        }
    }

    inner class WebSocketChannelListener(
        private val incoming: Channel<RawData>,
        private val outgoing: Channel<RawData>
    ) : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {}

        override fun onMessage(webSocket: WebSocket, text: String) {
            scope.launch(Dispatchers.IO) {
                incoming.send(RawData(text))
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            scope.launch(Dispatchers.IO) {
                incoming.send(RawData(bytes.toString()))
            }
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {}

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            incoming.close()
            outgoing.close()
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            incoming.close(t)
            outgoing.close(t)
        }
    }
}
