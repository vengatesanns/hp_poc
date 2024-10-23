package com.hackprotech

import org.apache.log4j.{Level, Logger}
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD

// Movies ratings Count
object MovieRatings extends App {

  // LOG Level set at Error
  Logger.getLogger("org").setLevel(Level.ERROR)

  // 1) Create a SparkContext
  val sc = new SparkContext("local[2]", "MovieRatingsCount")

  // 2) Read the text file from local
  val lines: RDD[String] = sc.textFile("/home/vengat/Projects/Big-Data/Data Sets/movie-lens-100k/u.data")

  // 3) load ech line from the file (File Format - "User_ID, Movie_ID, Ratings, Timestamp(ephoc))
  val ratings: RDD[String] = lines.map(line => line.split("\t")(2))

  // 4) CountBy Values to check how many times each ratings occur
  val results = ratings.countByValue()

  // 5) To sort the counts for ratings
  val ratingsResult = results.toSeq.sortBy(_._1)

  // 6) Print in the console
  ratingsResult.foreach(println)

}
