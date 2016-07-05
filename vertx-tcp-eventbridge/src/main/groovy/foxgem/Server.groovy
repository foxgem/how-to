package foxgem

import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.ext.groovy.eventbus.bridge.tcp.TcpEventBusBridge
import io.vertx.lang.groovy.GroovyVerticle

class Server extends GroovyVerticle {

    Logger logger = LoggerFactory.getLogger(Server)
    TcpEventBusBridge bridge

    void start() {
        vertx.eventBus().consumer('echo') { message ->
            logger.info("server received: ${message.body()}")

            message.reply(message.body())
        }

        vertx.eventBus().consumer('publish') { message ->
            logger.info("server received: ${message.body()}")
        }

        bridge = TcpEventBusBridge.create(vertx, [
                inboundPermitteds : [[address: 'echo'], [address: 'publish']],
                outboundPermitteds: [[address: 'echo'], [address: 'publish']]
        ])

        bridge.listen(7000) { result ->
            if (result.succeeded()) {
                logger.info('server is started at port 7000')
            }
        }
    }

    void stop() {
        bridge.close()
    }

}
