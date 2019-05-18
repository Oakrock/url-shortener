package work.kickstand.urlshortener.router

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.router
import work.kickstand.urlshortener.handler.UrlShortenerHandler

@Configuration
class UrlShortenerRouter {

    @Bean
    fun router(handler: UrlShortenerHandler) = router {
            GET("/{shortUrl}", handler::redirect)
            POST("/", handler::createShortUrl)
    }
}