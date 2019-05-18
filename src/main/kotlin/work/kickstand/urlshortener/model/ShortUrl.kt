package work.kickstand.urlshortener.model

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("urls")
data class ShortUrl(@Id val shortUrl : String, val url : String)