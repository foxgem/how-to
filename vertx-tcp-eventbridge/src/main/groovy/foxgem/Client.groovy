package foxgem

import io.vertx.core.json.JsonObject
import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.eventbus.bridge.tcp.impl.protocol.FrameHelper
import io.vertx.ext.eventbus.bridge.tcp.impl.protocol.FrameParser
import io.vertx.groovy.core.net.NetClient
import io.vertx.groovy.core.net.NetSocket
import io.vertx.lang.groovy.GroovyVerticle

class Client extends GroovyVerticle {

    Logger logger = LoggerFactory.getLogger(Client)

    NetSocket socket

    void start() {
        NetClient client = vertx.createNetClient()
        client.connect(7000, 'localhost') { result ->
            if (result.succeeded()) {
                socket = result.result()

                FrameHelper.sendFrame('register', 'publish', null, socket.delegate)

                vertx.setPeriodic(3000) {
                    FrameHelper.sendFrame('send', 'echo', 'echo', [message: 'hello'] as JsonObject, socket.delegate)
                }

                vertx.setPeriodic(2000) {
                    FrameHelper.sendFrame('publish', 'publish', [message: 'publish'] as JsonObject, socket.delegate)
                }

                socket.delegate.handler(new FrameParser({ parse ->
                    if (parse.succeeded()) {
                        logger.info("client received: ${parse.result()}")
                    }
                }))

            }
        }
    }

    void stop() {
        socket.close()
    }
}
