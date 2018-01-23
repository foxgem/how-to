package domain

import java.time.LocalDateTime
import grails.gorm.transactions.Rollback
import org.grails.orm.hibernate.HibernateDatastore
import spock.lang.AutoCleanup
import spock.lang.Shared
import spock.lang.Specification

class PersonSpec extends Specification {
    @Shared
    @AutoCleanup
    HibernateDatastore datastore = new HibernateDatastore(getClass().getPackage())

    @Rollback
    def "person can be safed with firstname and lastname"() {
        when:
        def p = new Person(firstName: 'Sergio', lastName: 'del Amo', createTime: LocalDateTime.now())
        p.save(flush: true)

        then:
        Person.count()
    }
}
