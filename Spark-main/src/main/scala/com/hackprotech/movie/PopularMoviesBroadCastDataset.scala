package com.hackprotech.movie

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, desc, udf}
import org.apache.spark.sql.types.{IntegerType, LongType, StructType}

import scala.io.{Codec, Source}

object PopularMoviesBroadCastDataset extends App {

  // load u.item files for movie names
  def loadMovieNames(): Map[Int, String] = {
    implicit val codec: Codec = Codec("ISO-8859-1")
    var movieNames: Map[Int, String] = Map()
    val lines = Source.fromFile("src/test/resources/u.item")
    for (line <- lines.getLines()) {
      val fields = line.split('|')
      if (fields.length > 1) {
        movieNames += (fields(0).toInt -> fields(1))
      }
    }
    lines.close()
    movieNames
  }

  // Dataset Mapper and Schema
  case class Movies(userId: Int, movieId: Int, ratings: Int, timestamp: Long)

  val movies = new StructType()
    .add("userId", IntegerType, nullable = false)
    .add("movieId", IntegerType, nullable = false)
    .add("ratings", IntegerType, nullable = false)
    .add("timestamp", LongType, nullable = false)


  Logger.getLogger("org").setLevel(Level.ERROR)

  // create the spark session
  val spark = SparkSession.builder().appName("Detailed_Popular_Movies")
    .master("local[*]").getOrCreate()

  // Set the Broadcast Variables
  val movieDetailsBC = spark.sparkContext.broadcast(loadMovieNames)

  // Read the Movie Data Set

  import spark.implicits._;
  val moviesList = spark.read
    .option("sep", "\t")
    .schema(movies)
    .csv("src/test/resources/u.data")
    .as[Movies]

  // Movies Count
  val moviesCount = moviesList.groupBy("movieId").count()

  // UDF for movie name fetch based on movie id
  val movieName: Int => String = (movieId: Int) => {
    movieDetailsBC.value(movieId)
  }

  val movieLookUpUDF = udf(movieName)

  val movieBroadCast = movieDetailsBC.value;

  val lookUp = udf((movieId: Int) => {
    movieBroadCast.get(movieId)
  })

  // Map the movie Id with Movie Name in the Dataset
  val results = moviesCount.withColumn("movieName", lookUp(col("movieId")))

  val sortedResults = results.sort(desc("count"))

  sortedResults.show(sortedResults.count().toInt)

}
