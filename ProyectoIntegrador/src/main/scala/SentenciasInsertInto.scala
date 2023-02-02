import com.github.tototoshi.csv.CSVReader
import scalikejdbc._

import play.api.libs.json._
import requests.Response

import java.io.File
import scala.util.Try
import scala.util.matching.Regex

object SentenciasInsertInto extends App {

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

  val Movie = data
    .map(x =>
      (
        x("index").toInt,
        x("budget").toLong,
        x("homepage"),
        x("id").toInt,
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
      )).sortBy(_._4)

  /*val newRowMovie = Movie.map(x =>
    sql"""
         |INSERT INTO Movie(`index`, budget, homepage, idMovie, name_original_language, original_title, overview, popularity, release_date, revenue, runtime, nameStatus, tagline, title, vote_average, vote_count, nameDirector)
         |VALUES
         |(${x._1}, ${x._2}, ${x._3}, ${x._4}, ${x._5}, ${x._6}, ${x._7}, ${x._8}, ${x._9}, ${x._10}, ${x._11}, ${x._12}, ${x._13}, ${x._14}, ${x._15}, ${x._16}, ${x._17})
         """.stripMargin
      .update
      .apply())*/

  // ------------------------------------------------------------------------------------------------

  val Status = data
    .flatMap(x => x.get("status"))
    .distinct
    .sorted

  //println(Status)

  /*val newRowStatus = Status.map(x =>
    sql"""
         |INSERT INTO Status (nameStatus)
         |VALUES
         |(${x})
               """.stripMargin
      .update
      .apply())*/

  // ------------------------------------------------------------------------------------------------

  val Director = data
    .flatMap(x => x.get("director"))
    .filter(!_.equals(""))
    .map(StringContext.processEscapes)
    .distinct
    .sorted

  //println(Director)

  /*val newRow6 = Director.map(x =>
      sql"""
           |INSERT INTO Director (nameDirector)
           |VALUES
           |(${x})
               """.stripMargin
        .update
        .apply())*/

  // ------------------------------------------------------------------------------------------------

  val original_language = data
    .flatMap(x => x.get("original_language"))
    .distinct
    .sorted

  //println(original_language)

  /*val newRowOrigLang = original_language.map(x =>
      sql"""
           |INSERT INTO original_language (name_original_language)
           |VALUES
           |(${x})
               """.stripMargin
        .update
        .apply())*/

  // ------------------------------------------------------------------------------------------------

  val Genre = data.flatMap(x =>
    x.get("genres")
      .map(x => x.replace("Science Fiction", "Science-Fiction"))
  )
    .flatMap(x => x.split(" "))
    .filter(_.nonEmpty)
    .distinct
    .sorted

  //println(Genre)

  /*val newRowGenero = Genre.map(x =>
        sql"""
             |INSERT INTO Genre(nameGenre)
             |VALUES
             |(${x})
             """.stripMargin
          .update
          .apply())*/

  // ------------------------------------------------------------------------------------------------

  val Movie_Genres = data
    .map(x =>
      (x("id"), x("genres").replace("Science Fiction", "Science-Fiction")))
    .filter(_._2.nonEmpty)
    .map(x => (x._1, x._2.split(" ")))
    .flatMap(x => x._2.map((x._1, _)))
    .map(x => (x._1.toInt, x._2))
    .sortBy(_._1)

  //println(Movie_Genres)

  /*val newRowMovie_Genres = Movie_Genres.map(x =>
    sql"""
         |INSERT INTO Movies_Genres(id, nameGenre)
         |VALUES
         |(${x._1}, ${x._2})
             """.stripMargin
      .update
      .apply())*/

  // ------------------------------------------------------------------------------------------------

  val production_companies = data
    .flatMap(_.get("production_companies"))
    .map(Json.parse)
    .flatMap(_.as[List[JsValue]])
    .map(x => (x("name").as[String], x("id").as[Int]))
    .distinct
    .sortBy(_._2)

  //println(production_companies)

  /*val newRowProduction_Companies = production_companies.map(x =>
      sql"""
           |INSERT INTO production_companies(namePCompany, idPCompany)
           |VALUES
           |(${x._1}, ${x._2})
               """.stripMargin
        .update
        .apply())*/

  // ------------------------------------------------------------------------------------------------

  val Movie_production_companies = data
    .map(x =>
      (x("id"), Json.parse(x("production_companies"))))
    .map(x => (x._1, x._2\\"id"))
    .filter(_._2.nonEmpty)
    .flatMap(x => x._2.map((x._1, _)))
    .sortBy(_._1.toInt)

  //println(Movie_production_companies)

  /*val newRowMovie_production_companies = Movie_production_companies.map(x =>
      sql"""
           |INSERT INTO Movie_production_companies(idMovie, idPCompany)
           |VALUES
           |(${x._1}, ${x._2})
               """.stripMargin
        .update
        .apply())*/

  // ------------------------------------------------------------------------------------------------

  val production_countries = data
    .flatMap(_.get("production_countries"))
    .map(Json.parse)
    .flatMap(_.as[List[JsValue]])
    .map(x => (x("iso_3166_1").as[String], x("name").as[String]))
    .distinct
    .sorted

  //println(production_countries)

  /*val newRowProduction_Countries = production_countries.map(x =>
        sql"""
             |INSERT INTO production_countries(iso_3166_1, namePCountry)
             |VALUES
             |(${x._1}, ${x._2})
                 """.stripMargin
          .update
          .apply())*/

  // ------------------------------------------------------------------------------------------------

  val Movie_production_countries = data
    .map(x =>
      (x("id"), Json.parse(x("production_countries"))))
    .map(x => (x._1, x._2 \\ "iso_3166_1"))
    .filter(_._2.nonEmpty)
    .flatMap(x => x._2.map((x._1, _)))
    .sortBy(_._1.toInt)

  //println(Movie_production_countries)

  /*val newRowMovie_production_countries = Movie_production_countries.map(x =>
      sql"""
           |INSERT INTO Movie_production_countries(idMovie, iso_3166_1)
           |VALUES
           |(${x._1}, ${x._2})
               """.stripMargin
        .update
        .apply())*/

  // ------------------------------------------------------------------------------------------------

  val spoken_languages = data
    .flatMap(x => x.get("spoken_languages"))
    .map(Json.parse)
    .flatMap(_.as[List[JsValue]])
    .map(x => (x("iso_639_1").as[String], x("name").as[String]))
    .distinct
    .sorted

  //println(spoken_languages)

  /*val newRowSpoken_Languages = spoken_languages.map(x =>
        sql"""
             |INSERT INTO spoken_languages(iso_639_1, nameSLang)
             |VALUES
             |(${x._1}, ${x._2})
                 """.stripMargin
          .update
          .apply())*/

  // ------------------------------------------------------------------------------------------------

  val Movie_spoken_langauges = data
    .map(x =>
      (x("id"), Json.parse(x("spoken_languages"))))
    .map(x => (x._1, x._2 \\ "iso_639_1"))
    .filter(_._2.nonEmpty)
    .flatMap(x => x._2.map((x._1, _)))
    .sortBy(_._1.toInt)

  //println(Movie_spoken_langauges)

  /*val newRowMovie_spoken_langauges = Movie_spoken_langauges.map(x =>
      sql"""
           |INSERT INTO Movie_spoken_langauges(idMovie, iso_639_1)
           |VALUES
           |(${x._1}, ${x._2})
               """.stripMargin
        .update
        .apply())*/

  // ------------------------------------------------------------------------------------------------

  def actorsNames(dataRaw: String): Option[String] = {
    val response: Response = requests
      .post("http://api.meaningcloud.com/topics-2.0",
        data = Map("key" -> "1f53fad65cf65815811ac6ff5bdc8c49",
          "lang" -> "en",
          "txt" -> dataRaw,
          "tt" -> "e"),
        headers = Map("content-type" -> "application/x-www-form-urlencoded"))
    Thread.sleep(500)
    if (response.statusCode == 200) {
      Option(response.text)
    } else
      Option.empty
  }

  /*val Cast = data
      .map(row => row("cast"))
      .filter(_.nonEmpty)
      .map(StringContext.processEscapes)
      .take(10) //Use un número limitado para hacer sus pruebas, pero, al final debe analizar todos los datos.
      .map(actorsNames)
      .map(json => Try(Json.parse(json.get)))
      .filter(_.isSuccess)
      .map(_.get)
      .flatMap(json => json("entity_list").as[JsArray].value)
      .map(_("form"))
      .map(data => data.as[String])
      .toSet // Para elimnar duplicados*/

  //println(Cast)

  /*val newRowCast = Cast.map(x =>
        sql"""
             |INSERT INTO `Cast`(nameCast)
             |VALUES
             |(${x})
                 """.stripMargin
          .update
          .apply())*/

  // ------------------------------------------------------------------------------------------------

  /*val Movie_Cast = data
    .take(10)
    .map(x =>
      (x("id").toInt, actorsNames(x("cast")))
    )
    .map(x => (x._1, Try(Json.parse(x._2.get))))
    .filter(_._2.isSuccess)
    .map(x => (x._1, x._2.get))
    .flatMap(x => x._2("entity_list").as[JsArray].value.map(
      (x._1, _)
    ))
    .map(x => (x._1, x._2("form")))
    .map(x => (x._1, x._2.as[String]))
    .sortBy(_._1)*/

  //println(Movie_Cast)

  /*val newRowMovie_Cast = Movie_Cast.map(x =>
          sql"""
               |INSERT INTO Movie_Cast(idMovie, nameCast)
               |VALUES
               |(${x._1}, ${x._2})
                   """.stripMargin
            .update
            .apply())*/

  // ------------------------------------------------------------------------------------------------

  val pattern: Regex = "(\\s\"(.*?)\",)".r
  val pattern2: Regex = "([a-z]\\s\"(.*?)\"\\s*[A-Z])".r
  val pattern3: Regex = "(:\\s'\"(.*?)',)".r

  val patternsTR = List(
    (pattern2, "\"", "-u0022"),
    (pattern, "'", "-u0027"),
    (pattern3, "\"", "-u0022")
  )

  def replacePatterns(original: String, patternsTR: List[(Regex, String, String)]) = {

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

  val Crew = data
    .map(row => row("crew"))
    .map(replacePatterns(_, patternsTR))
    .map(text => text.replace("'", "\""))
    .map(text => text.replace("-u0027", "'"))
    .map(text => text.replace("-u0022", "\\\""))
    .map(text => Try(Json.parse(text)))
    .filter(_.isSuccess)
    .map(_.get)
    .flatMap(_.as[List[JsValue]])
    .map(x => (x("name").as[String], x("gender").as[Int], x("department").as[String], x("job").as[String], x("credit_id").as[String], x("id").as[Int]))
    .distinct
    .sortBy(_._6)

  //println(Crew)

  /*val newRowCrew = Crew.map(x =>
            sql"""
                 |INSERT INTO Crew(nameCrew, gender, department, job, credit_id, idCrew)
                 |VALUES
                 |(${x._1}, ${x._2}, ${x._3}, ${x._4}, ${x._5}, ${x._6})
                     """.stripMargin
              .update
              .apply())*/

  // FALTA MOVIE CREW



  /*val newTable = sql"""
                   |CREATE TABLE SUPERTABLE (
                   |  nameCrew varchar(255),
                   |  gender varchar(255),
                   |  department varchar(255),
                   |  job varchar(255),
                   |  credit_id varchar(255),
                   |  idCrew int PRIMARY KEY
                   |);
                   |""".stripMargin
                .update
                .apply()*/

  /*val newTable =
    sql"""
         |DROP TABLE SUPERTABLE;
         |""".stripMargin
      .update
      .apply()*/

}
