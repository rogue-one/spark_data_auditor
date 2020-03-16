package com.github.rogue1.spark.data.auditor

import com.github.rogue1.spark.data.auditor.config.DBEndPoint
import com.github.rogue1.spark.data.auditor.config.HiveEndPoint
import com.github.rogue1.spark.data.auditor.config.TableConfig
import org.apache.spark.sql.SparkSession

class Auditor(srcEndPoint: DBEndPoint,
              tgtEndPoint: HiveEndPoint,
              spark: SparkSession) {


    fun process(tableConfig: TableConfig) {

    }


}