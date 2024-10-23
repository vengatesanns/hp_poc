package com.hackprotech

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext

object FriendsByAge extends App {

  // LOG Level set at Error
  Logger.getLogger("org").setLevel(Level.ERROR)

  // Spark Context
  val sc = new SparkContext("local[*]", "FriendsByAge")

  // Parse Each Line
  def parseLine(line: String) = {
    val fields = line.split(",")
    val age = fields(2).toInt
    val friends = fields(3).toInt
    (age, friends)
  }

  // Load csv files
  val lines = sc.textFile("/home/vengat/Projects/Big-Data/spark-basics/src/test/resources/friendsByAge.csv")

  // RDD
  val rdd = lines.map(parseLine)

  val totalsByAge = rdd.mapValues(x => (x, 1)).reduceByKey((x, y) => (x._1 + y._1, x._2 + y._2))

  val averagesByAge = totalsByAge.mapValues(x => x._1 / x._2)

  val results = averagesByAge.collect()
  results.foreach(println)

}
