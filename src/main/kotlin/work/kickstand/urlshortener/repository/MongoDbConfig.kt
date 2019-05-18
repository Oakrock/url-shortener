package work.kickstand.urlshortener.repository

import com.mongodb.reactivestreams.client.MongoClient
import com.mongodb.reactivestreams.client.MongoClients
import org.springframework.context.annotation.Bean
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories


@EnableReactiveMongoRepositories
class MongoDbConfig : AbstractReactiveMongoConfiguration() {

    @Bean
    override fun reactiveMongoClient(): MongoClient {
        return MongoClients.create()
    }

    override fun getDatabaseName(): String {
        return "reactive"
    }
}