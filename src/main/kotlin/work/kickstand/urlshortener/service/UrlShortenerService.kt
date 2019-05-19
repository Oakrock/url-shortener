package work.kickstand.urlshortener.service

import reactor.core.publisher.Mono
import reactor.core.publisher.MonoSink
import work.kickstand.urlshortener.model.ShortUrl
import work.kickstand.urlshortener.repository.MongoDbRepository
import java.lang.Exception

class UrlShortenerService(private val repo : MongoDbRepository) {

    fun getShortUrl(shortCode : String) = repo.findById(shortCode)

    fun createShortUrl(url : String) : Mono<ShortUrl> {
        return Mono.create(::checkShortUrl)
            .retry(3, {it is Exception})
            .zipWith(Mono.just(url), ::ShortUrl)
            .map{ repo.save(it) }
            .flatMap{it}
    }

    private fun checkShortUrl(emitter : MonoSink<String>) {
        val shortUrl = ShortUrlGenerator.generateShortUrl()
        repo.existsById(shortUrl)
                .subscribe{ if (it) emitter.error(Exception("Collision"))
                            else emitter.success(shortUrl) }
    }
}
