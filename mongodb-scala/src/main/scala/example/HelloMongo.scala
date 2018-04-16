package example

import scala.concurrent.Await
import scala.concurrent.duration._

import org.mongodb.scala._

object HelloMongo extends App {
//  System.setProperty("org.mongodb.async.type", "netty")
  val dbConnectionString = sys.env("MONGODB_CONNECTION_STRING")
  val databaseName = sys.env("MONGODB_DATABASE_NAME")

  val mongoClient: MongoClient = MongoClient(dbConnectionString)


  println("Starting connection...")

  val database: MongoDatabase = mongoClient.getDatabase(databaseName)

  val collection: MongoCollection[Document] = database.getCollection("test")

  println("Fetched collection....")

  val collCount = Await.result(collection.count().toFuture(), 1.minute)

  println(s"Found collection: $collCount")

  val doc: Document = Document(
    "_id" -> java.util.UUID.randomUUID().toString,
    "name" -> "MongoDB",
    "type" -> "database",
    "count" -> 1,
    "info" -> Document(
      "x" -> 203,
      "y" -> 102)
  )

  println(s"About to start insertion for $doc")

  val observable = collection.insertOne(doc)

  println(s"Insert result: $observable")

  observable.subscribe(new Observer[Completed] {

    override def onNext(result: Completed): Unit = println("Inserted")

    override def onError(e: Throwable): Unit = println("Failed")

    override def onComplete(): Unit = println("Completed")
  })
}
