package work.kickstand.urlshortener.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import work.kickstand.urlshortener.model.ShortUrl

@Repository
interface MongoDbRepository : ReactiveCrudRepository<ShortUrl, String> {

}
