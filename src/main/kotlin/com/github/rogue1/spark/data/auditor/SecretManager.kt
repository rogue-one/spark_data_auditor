package com.github.rogue1.spark.data.auditor

import com.amazonaws.services.secretsmanager.AWSSecretsManager
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder
import com.amazonaws.services.secretsmanager.model.GetSecretValueRequest
import com.github.rogue1.spark.data.auditor.config.DBConnection
import com.typesafe.config.ConfigFactory
import io.github.config4k.extract

/**
 * interface to resolve secret keys
 */
interface SecretManager {

    private val awsRegion: String
    get() = System.getenv("AWS_DEFAULT_REGION") ?: "us-west-2"


    private val secretsClient: AWSSecretsManager
    get() = AWSSecretsManagerClientBuilder.standard().withRegion("").build()

    fun fetchConnection(secret: String): DBConnection {
        val secretsValueRequest = GetSecretValueRequest().withSecretId(secret)
        val config =  ConfigFactory.parseString(secretsClient.getSecretValue(secretsValueRequest).secretString)
        return config.extract<DBConnection>()
    }

}