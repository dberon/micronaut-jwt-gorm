package example.micronaut.controller

import example.micronaut.domain.Todo
import example.micronaut.service.TodoService
import grails.gorm.transactions.Transactional
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Put
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule

import javax.inject.Inject

@Transactional
@Controller("/todo")
@Secured(SecurityRule.IS_AUTHENTICATED)
class TodoController {

    @Inject TodoService todoService

    @Get
    List<Todo> index() {
        Todo.list()
    }

    @Secured("ROLE_DETECTIVE")
    @Get("/{id}")
    Todo show(Long id) {
        todoService.find(id)
    }

    @Post
    HttpResponse<Todo> save(@Body Todo todo) {
        if (!todo.save(flush: true, failOnError: false)) {
            return HttpResponse.unprocessableEntity().body(todo) as HttpResponse<Todo>
        }
        HttpResponse.created(todo)
    }

    @Put("/{id}")
    HttpResponse<Todo> update(Serializable id, @Body Todo updatedTodo) {
        def todo = Todo.get(id)
        if (!todo) {
            return HttpResponse.notFound()
        }

        todo.title = updatedTodo.title
        todo.deadline = updatedTodo.deadline
        if (!todo.save(flush: true, failOnError: false)) {
            return HttpResponse.unprocessableEntity().body(todo) as HttpResponse<Todo>
        }
        HttpResponse.ok(todo)
    }

    @Delete("/{id}")
    void delete(Serializable id) {
        Todo.get(id)?.delete(flush: true)
    }
}