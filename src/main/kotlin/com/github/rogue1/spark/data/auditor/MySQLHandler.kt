package com.github.rogue1.spark.data.auditor

import com.github.rogue1.spark.data.auditor.config.DBConnection
import com.github.rogue1.spark.data.auditor.config.DBEndPoint
import com.github.rogue1.spark.data.auditor.config.TableConfig
import org.apache.spark.sql.Row
import org.apache.spark.sql.Dataset
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import java.util.*

class MySQLHandler(private val dbEndPoint: DBEndPoint,
                   private val tableConfig: TableConfig,
                   private val spark: SparkSession) : SecretManager {


    private val dbConnection: DBConnection
    get() = fetchConnection(dbEndPoint.name)

    private val property: Properties
    get() {
        val property = Properties()
        property.setProperty("user", dbConnection.username)
        property.setProperty("password", dbConnection.password)
        property.setProperty("driver", "com.mysql.cj.jdbc.Driver")
        property.setProperty("zeroDateTimeBehavior", "convertToNull")
        return property
    }

    private val escapedTableName: String = "`${tableConfig.schema}`.`${tableConfig.table}`"

    /**
     *
     */
    private val schema: StructType by lazy {
         spark.read().jdbc(dbConnection.jdbcUrl, escapedTableName, property).schema()
    }

    fun fetchSamples(primaryIds: List<Long>): Dataset<Row> {
        val dataSet = primaryIds
                .withIndex()
                .groupBy { (x,_) -> x % 10 }
                .values
                .map { it.map {  x -> x.value } }
                .map { fetchData(it) }
        return dataSet.drop(1).foldRight(dataSet.first()){ acc, x -> acc.union(x) }
    }

    private fun fetchData(list: List<Long>): Dataset<Row> {
       return spark.read().jdbc(dbConnection.jdbcUrl, "(${query(list)}) t0", property)
    }

    private fun query(list: List<Long>): String {
        return """
            SELECT 
             ${schema.fields().joinToString(","){ "`${it.name()}`" }}
             FROM $escapedTableName
             WHERE `${tableConfig.primary}` IN (${list.joinToString(","){ it.toString() }})   
        """.trimIndent()
    }

}