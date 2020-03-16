package com.github.rogue1.spark.data.auditor

import com.github.rogue1.spark.data.auditor.config.TableConfig
import com.github.rogue1.spark.data.auditor.util.DateUtil
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.Row
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import java.util.*


class HiveHandler(private val hiveDb: String,
                  private val tableConfig: TableConfig,
                  private val runDate: Date,
                  private val spark: SparkSession) {


    private val schema: StructType by lazy {
        spark.read().table("${tableConfig.schema}.${tableConfig.table}").schema()
    }


    private val query: String by lazy {
        """
            SELECT 
            ${schema.fields().joinToString(",") { "`${it.name()}`" }}
            FROM `${hiveDb}`.`${tableConfig.schema}_${tableConfig.table}`
            WHERE CAST(`${tableConfig.timestamp}` as DATE) = DATE '${DateUtil.dateStr(runDate)}'
            LIMIT ${tableConfig.rowLimit}
        """.trimIndent()
    }


    val dataSet: Dataset<Row> by lazy {
        spark.sql(query).persist()
    }

    fun primaryIds(): List<Long> {
        return dataSet.select(tableConfig.primary).collect().map { it.getLong(0) }
    }



}