package work.kickstand.urlshortener

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import java.util.*

@Component
class UrlShortenerHandler(val repo: MongoDbRepository) {

    fun helloWorld(request: ServerRequest) : Mono<ServerResponse> {
        ShortUrlGenerator.generateShortUrl()
        return ok().body(repo.save(ShortUrl("test", "test")), ShortUrl::class.java)
    }
}
