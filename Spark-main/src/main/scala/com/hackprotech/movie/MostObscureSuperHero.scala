package com.hackprotech.movie

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.{col, size, split, sum, min}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object MostObscureSuperHero extends App {

  Logger.getLogger("org").setLevel(Level.ERROR)

  // Schema for marvel movies
  case class MarvelNames(id: Int, heroName: String)

  val superHeroSchema = new StructType()
    .add("id", IntegerType, nullable = false)
    .add("heroName", StringType, nullable = false)


  case class MarvelGraph(values: String)

  val supperHeroConnections = new StructType()
    .add("values", StringType, nullable = false)

  // Create Spark Session
  val spark = SparkSession.builder().master("local[*]")
    .appName("MostPopularSuperHero").getOrCreate()

  // Read the Files

  import spark.implicits._;
  val superHeros = spark.read
    .option("sep", " ")
    .schema(superHeroSchema)
    .csv("src/test/resources/Marvel-names.txt").as[MarvelNames]

  val superHerosConnectionsGraph = spark.read
    .schema(supperHeroConnections)
    .csv("src/test/resources/Marvel-graph.txt").as[MarvelGraph]

  val connections = superHerosConnectionsGraph
    .withColumn("id", split(col("values"), " ")(0))
    .withColumn("connections", size(split(col("values"), " ")) - 1)
    .groupBy("id").agg(sum("connections").alias("connections"))

  val minConectionCount = connections.agg(min("connections")).first().getLong(0)

  val lessConnectionsSuperHeros = connections.filter($"connections" === minConectionCount)

  val mostObscureSuperHero = lessConnectionsSuperHeros
    .join(superHeros, lessConnectionsSuperHeros.col("id") === superHeros.col("id"), "inner")

  println(s"Marvel's most obscure superHero having only ${minConectionCount}  connections")

  mostObscureSuperHero.select("heroName").show(false)


}
