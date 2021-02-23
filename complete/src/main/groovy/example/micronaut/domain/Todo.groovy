package example.micronaut.domain

import grails.gorm.annotation.Entity

@Entity
class Todo {

    String title
    String deadline

    static constraints = {
        title blank: false, maxSize: 500
        deadline maxSize: 100
    }
}