package com.github.rogue1.spark.data.auditor.config

import com.typesafe.config.Config

data class TableConfig(val schema: String,
                       val table: String,
                       val primary: String,
                       val timestamp: String,
                       val rowLimit: Int) {

    companion object {

        /**
         *
         */
        fun make(config: Config): TableConfig {
            val str = config.getString("table-name").split("\\.")
            if(config.getString("table-name").split("\\.").size == 2) {
                val (schema, table) = str
                return TableConfig(
                        schema,
                        table,
                        config.getString("primary"),
                        config.getString("timestamp"),
                        config.getInt("row-limit")
                )
            } else {
             throw Exception("table-name $str must be in format <schema-name>.<table-name>")
            }
        }
    }
}