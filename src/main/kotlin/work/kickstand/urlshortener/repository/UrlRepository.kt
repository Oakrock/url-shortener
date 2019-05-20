package work.kickstand.urlshortener.repository

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.model.*
import work.kickstand.urlshortener.model.ShortUrl

@Repository
class UrlRepository(val dynamoClient: DynamoDbAsyncClient)  {
    fun saveUrl(url : ShortUrl): Mono<ShortUrl> {
        val putItemRequest = PutItemRequest.builder()
                .tableName("Url")
                .item(this.shortUrlMapper(url))
                .build()
        return Mono.fromCompletionStage(dynamoClient.putItem(putItemRequest))
                .flatMap {
                    Mono.just(url)
                }
    }

    fun getShortUrl(id: String): Mono<ShortUrl> {
        val getItemRequest: GetItemRequest = GetItemRequest.builder()
                .key(mapOf("shortUrl" to AttributeValue.builder().s(id).build()))
                .tableName("Url")
                .build()

        return Mono.fromCompletionStage(dynamoClient.getItem(getItemRequest))
                .filter{x -> x.item().isNotEmpty() }
                .map { resp ->
                    ShortUrl(resp.item()["shortUrl"]?.s(), resp.item()["url"]?.s())
                }
    }

    fun checkIfShortUrlExists(id : String) : Mono<Boolean> {
        return getShortUrl(id).map{true}.defaultIfEmpty(false)
    }

//    override fun deleteHotel(id: String): Mono<Boolean> {
//        val deleteItemRequest = DeleteItemRequest.builder()
//                .key(mapOf("shortUrl" to AttributeValue.builder().s(id).build()))
//                .tableName("Url")
//                .build()
//
//        return Mono.fromCompletionStage(dynamoClient.deleteItem(deleteItemRequest))
//                .map {
//                    true
//                }
//    }
//

//
//    override fun findHotelsByState(state: String): Flux<Hotel> {
//        val qSpec = QueryRequest
//                .builder()
//                .tableName(Constants.TABLE_NAME)
//                .indexName(Constants.HOTELS_BY_STATE_INDEX)
//                .keyConditionExpression("#st=:state")
//                .expressionAttributeNames(mapOf("#st" to "state"))
//                .expressionAttributeValues(mapOf(":state" to AttributeValue.builder().s(state).build()))
//                .build()
//
//        return Mono.from(dynamoClient.queryPaginator(qSpec)).flatMapIterable { resp ->
//            resp.items().stream().map { item ->
//                HotelMapper.fromMap(item[Constants.ID]!!.s(), item)
//            }.collect(Collectors.toList())
//        }
//
//    }

    fun shortUrlMapper(url : ShortUrl) : Map<String, AttributeValue> {
        return mapOf("shortUrl" to AttributeValue.builder().s(url.shortUrl).build(),
                    "url" to AttributeValue.builder().s(url.url).build())
    }

}
