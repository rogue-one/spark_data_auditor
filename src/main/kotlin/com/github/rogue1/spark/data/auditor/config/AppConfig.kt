package com.github.rogue1.spark.data.auditor.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigValue
import com.typesafe.config.ConfigValueType
import io.github.config4k.extract


/**
 * app config
 */
data class AppConfig(
        val srcEndPoint: EndPoint,
        val tables: List<TableConfig>,
        val tgtEndPoint: EndPoint
) {

    companion object {


        /**
         *
         */
        fun make(config: Config,
                 defaultConfig: Config): AppConfig {
            return AppConfig(
                    EndPoint.make(config.getConfig("src-end-point")),
                    config.extract<List<ConfigValue>>("tables").map {
                        when (it.valueType()) {
                            ConfigValueType.STRING -> defaultConfig.withValue("table-name", it)
                            ConfigValueType.OBJECT -> it.atPath("").withFallback(defaultConfig)
                            else -> throw Exception("table-name field should be a string or a config object")
                        }
                    }.map {
                        TableConfig.make(it)
                    },
                    EndPoint.make(config.getConfig("tgt-end-point"))
            )
        }
    }
}

