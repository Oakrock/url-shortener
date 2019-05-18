package work.kickstand.urlshortener.repository

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import work.kickstand.urlshortener.model.ShortUrl


@Repository
@Document(collection = "test")
interface MongoDbRepository : ReactiveCrudRepository<ShortUrl, String> {

}
