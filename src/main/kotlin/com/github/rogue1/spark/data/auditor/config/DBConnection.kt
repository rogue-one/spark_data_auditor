package com.github.rogue1.spark.data.auditor.config


data class DBConnection(val host: String, val username: String, val password: String,
                        val dbname: String?, val port: Int) {
    init {
        Class.forName("com.mysql.cj.jdbc.Driver")
    }

    val jdbcUrl: String = "jdbc:mysql://$host:$port/${dbname ?: ""}"
}