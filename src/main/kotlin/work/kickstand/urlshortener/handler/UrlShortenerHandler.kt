package work.kickstand.urlshortener.handler

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import work.kickstand.urlshortener.model.ShortUrl
import work.kickstand.urlshortener.service.UrlShortenerService
import java.net.URI

@Component
class UrlShortenerHandler(val service: UrlShortenerService) {

    fun createShortUrl(request: ServerRequest) : Mono<ServerResponse> {
        ServerResponse.created(URI("getcall for shorturl")).body(service.createShortUrl("testing"), ShortUrl::class.java)
        return ServerResponse.ok().build()
    }

    fun redirect(request: ServerRequest) = service.getShortUrl(request.pathVariable("shortUrl"))
                                                    .switchIfEmpty(Mono.just(defaultUrl))
                                                    .map(Companion::redirectToUrl).flatMap{it}

    companion object {

        val defaultUrl = ShortUrl("default", "https://www.kickstand.work")
        private fun redirectToUrl(shortUrl : ShortUrl) = ServerResponse.status(HttpStatus.PERMANENT_REDIRECT)
                                                            .location(URI.create(shortUrl.url))
                                                            .build()
    }
}
