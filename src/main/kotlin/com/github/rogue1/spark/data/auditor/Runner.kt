package com.github.rogue1.spark.data.auditor

import com.github.rogue1.spark.data.auditor.config.AppConfig
import org.apache.spark.sql.SparkSession
import com.typesafe.config.ConfigFactory
import kotlin.properties.Delegates

class Runner(private val args: LaunchArgs) {


    private val appConfig: AppConfig
    get() {
        val conf = ConfigFactory.parseFile(args.conf)
        return AppConfig.make(conf.getConfig(args.report), conf.getConfig("default"))
    }



    private val spark: SparkSession by lazy {
        SparkSession
                .builder()
                .appName("app-name")
                .enableHiveSupport()
                .orCreate
    }

    fun run(): Unit {
        for (table in appConfig.tables) {
        }
    }

}