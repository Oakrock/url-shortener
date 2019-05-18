package work.kickstand.urlshortener

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository


@Repository
@Document(collection = "test")
interface MongoDbRepository : ReactiveCrudRepository<ShortUrl, String> {

}
