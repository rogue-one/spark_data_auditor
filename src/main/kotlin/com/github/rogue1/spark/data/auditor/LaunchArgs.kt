package com.github.rogue1.spark.data.auditor

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.*
import com.github.rogue1.spark.data.auditor.util.DateUtil
import java.io.File

/**
 * Launch arguments
 */
class LaunchArgs: CliktCommand("Data auditing application") {

    val conf: File by option(help="config file")
                            .convert { File(it) }
                            .default(File(System.getProperty("user.dir"), "input.conf"))

    val report: String by option(help="report name")
                            .required()

    val runDate by option(help="run date")
                            .convert { DateUtil.toDate(it) }
                            .default(DateUtil.date(2))

    /**
     * run
     */
    override fun run() {
        Runner(this).run()
    }

}