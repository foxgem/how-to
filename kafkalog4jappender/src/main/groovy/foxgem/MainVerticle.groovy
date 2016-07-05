package foxgem

import io.vertx.core.logging.Logger
import io.vertx.core.logging.LoggerFactory
import io.vertx.lang.groovy.GroovyVerticle

class MainVerticle extends GroovyVerticle {

    private static Logger log = LoggerFactory.getLogger(MainVerticle.class)

    void start() {

        vertx.setPeriodic(1000) {
            log.info("I'm here: ${new Date().time}")
        }
    }

}
