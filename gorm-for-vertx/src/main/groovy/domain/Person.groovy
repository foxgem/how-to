package domain

import java.time.LocalDateTime
import grails.gorm.annotation.Entity
import org.grails.datastore.gorm.GormEntity

@Entity
class Person implements GormEntity<Person> {
    String firstName
    String lastName
    LocalDateTime createTime

    static mapping = {
        firstName blank: false
        lastName blank: false
        createTime blank: false
    }
}
