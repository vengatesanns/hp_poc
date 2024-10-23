package com.hackprotech.movie

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.desc
import org.apache.spark.sql.types.{IntegerType, LongType, StructType}

object PopularMoviesDataSet extends App {

  case class Movie(movieId: Int)

  Logger.getLogger("org").setLevel(Level.ERROR)

  val movieSchema = new StructType()
    .add("userId", IntegerType, nullable = false)
    .add("movieId", IntegerType, nullable = false)
    .add("ratings", IntegerType, nullable = false)
    .add("timestamp", LongType, nullable = false)

  val sparkSession = SparkSession.builder()
    .master("local[*]")
    .appName("Popular Movies").getOrCreate()

  import sparkSession.implicits._

  val movieDS = sparkSession.read
    .option("sep", "\t")
    .schema(movieSchema)
    .csv("src/test/resources/u.data")
    .as[Movie]

  val popularMovies = movieDS.groupBy("movieId").count().orderBy(desc("count"))
  popularMovies.show(5)
  sparkSession.stop()
}
