package example.micronaut


import groovy.transform.CompileStatic
import io.micronaut.runtime.Micronaut
import javax.inject.Singleton
import io.micronaut.context.event.ApplicationEventListener
import io.micronaut.runtime.server.event.ServerStartupEvent

import io.swagger.v3.oas.annotations.*
import io.swagger.v3.oas.annotations.info.*

@OpenAPIDefinition(
        info = @Info(
                title = "Hello World",
                version = "0.0",
                description = "My API",
                license = @License(name = "Apache 2.0", url = "https://foo.bar"),
                contact = @Contact(url = "https://gigantic-server.com", name = "Fred", email = "Fred@gigagantic-server.com")
        )
)
@CompileStatic
@Singleton
class Application implements ApplicationEventListener<ServerStartupEvent> {

    protected final RegisterService registerService

    Application(RegisterService registerService) {
        this.registerService = registerService
    }

    @Override
    void onApplicationEvent(ServerStartupEvent event) {
        registerService.register("sherlock@micronaut.example", "sherlock", 'elementary', ['ROLE_DETECTIVE'])
    }

    static void main(String[] args) {
        Micronaut.run(Application.class)
    }
}
