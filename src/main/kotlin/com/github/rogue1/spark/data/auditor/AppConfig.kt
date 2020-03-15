package com.github.rogue1.spark.data.auditor

import com.typesafe.config.Config
import io.github.config4k.extract


/**
 * app config
 */
data class AppConfig(
        val srcEndPoint: EndPoint,
        val tables: List<String>,
        val tgtEndPoint: EndPoint
) {

    companion object {
        /**
         *
         */
        fun make(config: Config): AppConfig {
            return AppConfig(
                    EndPoint.make(config.getConfig("src-end-point")),
                    config.extract<List<String>>("tables"),
                    EndPoint.make(config.getConfig("tgt-end-point"))
            )
        }
    }
}

