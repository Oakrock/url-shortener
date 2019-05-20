package work.kickstand.urlshortener.repository

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "dynamo")
@Component
data class DynamoProperites (
        var endpoint: String = ""
)