package com.hackprotech

import org.apache.spark.sql.SparkSession

object SparkWithMySqlDB {

  def main(args: Array[String]): Unit = {
    val sparkSession = SparkSession.builder().appName("Spark With MySQL DB").master("local[*]").getOrCreate()
    sparkSession.sparkContext.setLogLevel("ERROR")
    val url = "jdbc:mysql://localhost:3306/employee"
    val driver = "com.mysql.cj.jdbc.Driver"
    val userName = "root"
    val password = "12345"
    val query = "(select * from user_info where active = 1) as users"

    val df = sparkSession.read
      .format("jdbc")
      .option("url", url)
      .option("driver", driver)
      .option("dbtable", query)
      .option("user", userName)
      .option("password", password)
      .load()

    df.show()

    sparkSession.stop()

  }

}
