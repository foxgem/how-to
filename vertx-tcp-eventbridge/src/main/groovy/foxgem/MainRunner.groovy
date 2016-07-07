package foxgem

import io.vertx.groovy.core.Vertx

class MainRunner {

    public static void main(String[] args) {
        Vertx vertx1 = Vertx.vertx()
        vertx1.deployVerticle('groovy:foxgem.Server', [instances: 2])

        Vertx vertx2 = Vertx.vertx()
        vertx2.deployVerticle('groovy:foxgem.Client')
    }

}
