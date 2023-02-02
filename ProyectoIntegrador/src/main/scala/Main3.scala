import com.github.tototoshi.csv.CSVReader
import scalikejdbc.{AutoSession, ConnectionPool, DBSession}
import scalikejdbc._
import play.api.libs.json._

import java.io.File
import scala.util.Try
import scala.util.matching.Regex

object Main3 extends App {

  // ------------------------------------------------------------------------------------------------

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  // ------------------------------------------------------------------------------------------------

  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://localhost:3306/MoviesTaller", "root", "charliecharlie")
  implicit val session: DBSession = AutoSession

  // ------------------------------------------------------------------------------------------------

  val Movies = data
    .map(x =>
      (
        x("id").toInt,
        x("index").toInt,
        x("budget").toLong,
        x("homepage"),
        x("original_language"),
        x("original_title"),
        x("overview"),
        x("popularity").toDouble,
        x("release_date"),
        x("revenue").toLong,
        x("runtime") match {
          case valueOfRT if valueOfRT.isEmpty => 0.0
          case valueOfRT => valueOfRT.toDouble
        },
        x("status"),
        x("tagline"),
        x("title"),
        x("vote_average").toDouble,
        x("vote_count").toInt,
        x("director")
      ))

  Movies.take(5).foreach(println(_))

  /*val newRow3 = Movies.map(x =>
    sql"""
         |INSERT INTO Movies(id, `index`, budget, homepage, original_language, original_title, overview, popularity, release_date, revenue, runtime, status, tagline, title, vote_average, vote_count, director)
         |VALUES
         |(${x._1}, ${x._2}, ${x._3}, ${x._4}, ${x._5}, ${x._6}, ${x._7}, ${x._8}, ${x._9}, ${x._10}, ${x._11}, ${x._12}, ${x._13}, ${x._14}, ${x._15}, ${x._16}, ${x._17})
         """.stripMargin
      .update
      .apply())*/

  val Genero = data.flatMap(x =>
    x.get("genres")
      .map(x => x.replace("Science Fiction", "Science-Fiction"))
  )
    .flatMap(x => x.split(" "))
    .filter(_.nonEmpty)
    .distinct

  println(Genero)

  /*val newRow4 = Genero.map(x =>
      sql"""
           |INSERT INTO Genres(name)
           |VALUES
           |(${x})
           """.stripMargin
        .update
        .apply())*/

  val Movie_Genres = data
    .map(x =>
      (x("id"), x("genres").replace("Science Fiction", "Science-Fiction")))
    .map(x => (x._1, x._2.split(" ")))
    .flatMap(x => x._2.map((x._1, _)))
    .map(x => (x._1.toInt, x._2))

  // cambiar filter de newRow5 a Movie_Genres

  /*val newRow5 = Movie_Genres.filter(x => x._2.nonEmpty).map(x =>
    sql"""
         |INSERT INTO Movies_Genres(id, `name`)
         |VALUES
         |(${x._1}, ${x._2})
             """.stripMargin
      .update
      .apply())*/

  val Director = data.flatMap(x => x.get("director"))
    .filter(!_.equals(""))
    .map(StringContext.processEscapes)
    .distinct

  /*val newRow6 = Director.map(x =>
      sql"""
           |INSERT INTO Director (`name`)
           |VALUES
           |(${x})
               """.stripMargin
        .update
        .apply())*/

  val Status = data.flatMap(x => x.get("status")).distinct

  println(Status)

  /*val newRow7 = Status.map(x =>
    sql"""
         |INSERT INTO Status (`name`)
         |VALUES
         |(${x})
               """.stripMargin
      .update
      .apply())*/

  val Spoken_Languages = data
    .flatMap(x => x.get("spoken_languages"))
    .map(Json.parse)
    .flatMap(_.as[List[JsValue]])
    .map(x => (x("iso_639_1").as[String], x("name").as[String]))
    .distinct

  println(Spoken_Languages)

  val Production_Companies = data
    .flatMap(_.get("production_companies"))
    .map(Json.parse)
    .flatMap(_.as[List[JsValue]])
    .map(x => (x("name").as[String], x("id").as[Int]))
    .toSet

  //println(Production_Companies)

  val Production_Countries = data
    .flatMap(_.get("production_countries"))
    .map(Json.parse)
    .flatMap(_.as[List[JsValue]])
    .map(x => (x("iso_3166_1").as[String], x("name").as[String]))
    .toSet

  //println(Production_Countries)

  val pattern: Regex = "(\\s\"(.*?)\",)".r
  val pattern2: Regex = "([a-z]\\s\"(.*?)\"\\s*[A-Z])".r
  val pattern3: Regex = "(:\\s'\"(.*?)',)".r

  val patternsTR = List(
    (pattern2, "\"", "-u0022"),
    (pattern, "'", "-u0027"),
    (pattern3, "\"", "-u0022")
  )

  def funcion(original: String, patternsTR: List[(Regex, String, String)]) = {

    var txtOr = original
    for (p <- patternsTR) {
      for (m <- p._1.findAllIn(txtOr)) {
        val textOriginal = m
        val replacementText = m.replace(p._2, p._3)
        txtOr = txtOr.replace(textOriginal, replacementText)
      }
    }
    txtOr
  }

  val crew = data
    .map(row => row("crew"))
    .map(funcion(_, patternsTR))
    .map(text => text.replace("'", "\""))
    .map(text => text.replace("-u0027", "'"))
    .map(text => text.replace("-u0022", "\\\""))
    .map(text => Try(Json.parse(text)))
    .filter(_.isSuccess)
    .map(_.get)
    .flatMap(_.as[List[JsValue]])
    .map(x => (x("name").as[String], x("gender").as[Int], x("department").as[String], x("job").as[String], x("credit_id").as[String], x("id").as[Int]))
    .distinct.size

  println(crew)


}
