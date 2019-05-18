package work.kickstand.urlshortener

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.router

@Configuration
class UrlShortenerRouter {

    @Bean
    fun router(handler: UrlShortenerHandler) = router {
            GET("/", handler::helloWorld)
            GET("/{test}") { request -> ServerResponse.ok().body(BodyInserters.fromObject(request.pathVariable("test"))) }
        }
}