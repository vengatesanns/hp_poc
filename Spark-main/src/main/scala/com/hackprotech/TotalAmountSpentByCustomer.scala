package com.hackprotech

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

/**
 * Total Amount Spend by the Customer
 */
object TotalAmountSpentByCustomer {


  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    // Spark Context
    val sc = new SparkContext("local[*]", "Total_Amount_SpentBy_Customer")

    // Read the Csv File
    val customerDetails = sc.textFile("src/test/resources/customer-orders.csv")


    val splitCustomerDetails = (line: String) => {
      val rowData = line.split(",")
      (rowData(0).toInt, rowData(2).toFloat)
    }


    val extractedCustomerDetails = customerDetails.map(splitCustomerDetails)


    // Reduce
    val totalSpentCostByCustomer = extractedCustomerDetails.reduceByKey((x, y) => x + y)

    // Swap Key and Values to do sorting
    val results = totalSpentCostByCustomer.map(x => (x._2, x._1)).sortByKey(ascending = false).collect()


    results.foreach(println)
  }


}
