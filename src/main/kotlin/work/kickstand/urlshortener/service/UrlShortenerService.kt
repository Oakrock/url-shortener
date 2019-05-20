package work.kickstand.urlshortener.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.core.publisher.MonoSink
import work.kickstand.urlshortener.model.ShortUrl
import work.kickstand.urlshortener.repository.UrlRepository
import java.lang.RuntimeException

@Service
class UrlShortenerService(private val repo : UrlRepository) {

    fun getShortUrl(shortCode : String) = repo.getShortUrl(shortCode)

    fun createShortUrl(url : Mono<String>) : Mono<ShortUrl> {

        return Mono.create(::checkShortUrl)
            .retry(3, {it is CollisionException})
            .zipWith(url, ::ShortUrl)
            .map{ repo.saveUrl(it) }
            .flatMap{it}
    }

    private fun checkShortUrl(emitter : MonoSink<String>) {
        val shortUrl = ShortUrlGenerator.generateShortUrl()
        repo.checkIfShortUrlExists(shortUrl)
                .subscribe{ if (it) emitter.error(CollisionException())
                            else emitter.success(shortUrl) }
    }

    class CollisionException : RuntimeException("Insertion collision exception")
}
