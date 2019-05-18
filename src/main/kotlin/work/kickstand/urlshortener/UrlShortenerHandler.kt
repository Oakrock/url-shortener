package work.kickstand.urlshortener

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import java.net.URI

@Component
class UrlShortenerHandler(val repo: MongoDbRepository) {

    fun helloWorld(request: ServerRequest) : Mono<ServerResponse> {
        return ServerResponse
                .status(HttpStatus.PERMANENT_REDIRECT)
                .location(URI.create("https://www.google.com")).build()
    }
}
