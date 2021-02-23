package example.micronaut.service

import example.micronaut.domain.Todo
import grails.gorm.services.Service

@Service
class TodoService {
    Todo find(Long id){
        return Todo.findById(id)
    }
}
