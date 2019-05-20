package work.kickstand.urlshortener.repository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.util.StringUtils
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClient
import software.amazon.awssdk.services.dynamodb.DynamoDbAsyncClientBuilder
import software.amazon.awssdk.services.dynamodb.model.*
import java.net.URI

@Configuration
class DynamoConfig {

    @Bean
    fun dynamoDbAsyncClient(dynamoProperties: DynamoProperites) : DynamoDbAsyncClient {

        val builder: DynamoDbAsyncClientBuilder = DynamoDbAsyncClient.builder()
                .region(Region.of(Region.US_WEST_2.toString()))
                .endpointOverride(URI.create("http://localhost:8000/"))
                .credentialsProvider(DefaultCredentialsProvider.builder().build())

        val keySchema = KeySchemaElement.builder().attributeName("shortUrl").keyType(KeyType.HASH).build()
        val attribute = AttributeDefinition.builder().attributeName("shortUrl").attributeType(ScalarAttributeType.S).build()
        val throughput = ProvisionedThroughput.builder().readCapacityUnits(10L).writeCapacityUnits(10L).build()
        val request = CreateTableRequest.builder().tableName("Url").keySchema(mutableListOf(keySchema))
                .provisionedThroughput(throughput).attributeDefinitions(attribute).build()

        if (!StringUtils.isEmpty(dynamoProperties.endpoint)) {
            builder.endpointOverride(URI.create(dynamoProperties.endpoint))
        }

        val client = builder.build()

        client.createTable(request)

        return client
    }

}