package com.github.rogue1.spark.data.auditor

import org.apache.spark.sql.SparkSession
import com.typesafe.config.ConfigFactory
import io.github.config4k.*

class Runner(private val args: LaunchArgs) {


    private val appConfig: AppConfig = AppConfig.make(ConfigFactory.parseFile(args.conf).getConfig(args.report))


    private val spark: SparkSession by lazy {
        SparkSession
                .builder()
                .appName("app-name")
                .enableHiveSupport()
                .orCreate
    }

    fun run(): Unit {
        println("source-connection ${appConfig.srcEndPoint}")
        println("table list ${appConfig.tables.foldRight(""){ acc, x -> "$acc, $x" }}")
        println("target-connection ${appConfig.tgtEndPoint}")
    }

}