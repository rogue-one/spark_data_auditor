package com.github.rogue1.spark.data.auditor.config

import com.typesafe.config.Config

sealed class EndPoint {

    companion object {

        fun make(config: Config): EndPoint {
            return when(val type = config.getString("type").toLowerCase()) {
                "mysql" -> DBEndPoint(config.getString("value"))
                "hive" -> HiveEndPoint(config.getString("value"))
                else -> throw Exception("endpoint type $type is not supported")
            }
        }

    }

}

data class DBEndPoint(val name: String) : EndPoint()

data class HiveEndPoint(val dbName: String) : EndPoint()