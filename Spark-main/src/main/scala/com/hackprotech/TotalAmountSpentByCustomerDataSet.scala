package com.hackprotech

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{round, sum}
import org.apache.spark.sql.types.{DoubleType, IntegerType, StructType}

/**
 * Using DataSets
 */
object TotalAmountSpentByCustomerDataSet extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  // Case Class (Similar to DTO)
  case class CustomerOrders(cust_id: Int, amount_spent: Double)

  //Create Spark Session
  val spark = SparkSession.builder()
    .appName("ExpenseByCustomerUsingDataSets")
    .master("local[*]")
    .getOrCreate()

  // Schema
  val customerOrderSchema = new StructType()
    .add("cust_id", IntegerType, nullable = true)
    //.add("item_id", IntegerType, nullable = true)
    .add("amount_spent", DoubleType, nullable = true)

  import spark.implicits._

  val customerDS = spark.read
    .schema(customerOrderSchema)
    .csv("src/test/resources/customer-orders.csv")
    .as[CustomerOrders]

  customerDS.printSchema()
  val totalAmountSpent = customerDS.groupBy("cust_id")
    .agg(round(sum("amount_spent"), 2).alias("total_spent"))

  val sortedResults = totalAmountSpent.sort("total_spent")

  sortedResults.show(totalAmountSpent.count().toInt)

  spark.stop()

}
