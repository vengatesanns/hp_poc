package com.hackprotech

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{round, sum}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructType}

/**
 * Using DataSets
 */
object TotalAmountSpentByCustomerDataFrame extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  //Create Spark Session
  val spark = SparkSession.builder()
    .appName("ExpenseByCustomerUsingDataSets")
    .master("local[*]")
    .getOrCreate()


  import spark.implicits._

  val customerDF = spark.read
    .csv("src/test/resources/customer-orders.csv")

  customerDF.printSchema()

  val totalAmountSpent = customerDF.groupBy("_c0")
    .agg(round(sum("_c2"), 2).alias("total_spent"))

  val v = totalAmountSpent.select("total_spent").collect().foreach(va => {
    val s = va.get(0)

  })

  //val sortedResults = totalAmountSpent.sort("total_spent")

  //sortedResults.show(totalAmountSpent.count().toInt)

  spark.stop()

}
