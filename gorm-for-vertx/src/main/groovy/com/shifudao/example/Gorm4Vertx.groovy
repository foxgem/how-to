package com.shifudao.example

import java.time.LocalDateTime
import domain.Person
import grails.gorm.transactions.Transactional
import io.vertx.core.AbstractVerticle
import org.grails.orm.hibernate.HibernateDatastore

@Transactional
class Gorm4Vertx extends AbstractVerticle {
    private static final HibernateDatastore datastore = new HibernateDatastore(Person)

    @Override
    void start() {
        Person person = new Person(firstName: "Feng", lastName: "Yu", createTime: LocalDateTime.now())
        person.save(flush: true)
        Person.getAll().each {
            println "Person firstName: ${it.firstName}, lastName: ${it.lastName}, createTime: ${it.createTime}"
        }
        vertx.close()
    }
}
