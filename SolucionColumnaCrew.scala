import java.io.File
import com.github.tototoshi.csv.CSVReader
import play.api.libs.json._
import requests.Response

import scala.util.Try
import scala.util.matching.Regex
import scalikejdbc._

import scalikejdbc.{AutoSession, ConnectionPool, DBSession}


object SolucionColumnaCrew extends App {

  // ------------------------------------------------------------------------------------------------

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  // ------------------------------------------------------------------------------------------------

  // REPLACE PATTERN

  def replacePattern(original: String): String = {
    var txtOr = original
    val pattern: Regex = "(\\s\"(.*?)\",)".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("'", "-u0027")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

  def replacePattern2(original: String): String = {
    var txtOr = original
    val pattern: Regex = "([a-z]\\s\"(.*?)\"\\s*[A-Z])".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("\"", "-u0022")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

  def replacePattern3(original: String): String = {
    var txtOr = original
    val pattern: Regex = "(:\\s'\"(.*?)',)".r
    for (m <- pattern.findAllIn(original)) {
      val textOriginal = m
      val replacementText = m.replace("\"", "-u0022")
      txtOr = txtOr.replace(textOriginal, replacementText)
    }
    txtOr
  }

  // ------------------------------------------------------------------------------------------------

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
    .size

  println(crew)

  // ------------------------------------------------------------------------------------------------

  Class.forName("com.mysql.cj.jdbc.Driver")
  ConnectionPool.singleton("jdbc:mysql://localhost:3306/MoviesTaller", "root", "charliecharlie")
  implicit val session: DBSession = AutoSession

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

  // ------------------------------------------------------------------------------------------------

  // ------------------------------------------------------------------------------------------------

  // Recuerde que el código anterior, puede contener nombres repetidos,
  // así que debe trabajar para evitar que en su base de datos existan nombres duplicados.

  /*val cast = data
    .map(row => row("cast"))
    .filter(_.nonEmpty)
    .map(StringContext.processEscapes)
    //.take(1000) //Use un número limitado para hacer sus pruebas, pero, al final debe analizar todos los datos.
    .map(actorsNames)
    .map(json => Try(Json.parse(json.get)))
    .filter(_.isSuccess)
    .map(_.get)
    .flatMap(json => json("entity_list").as[JsArray].value)
    .map(_("form"))
    .map(data => data.as[String])
    .toSet // Para elimnar duplicados

  println(cast)*/

  // ------------------------------------------------------------------------------------------------

  /*val newRow = cast.map(person =>
    sql"""
    INSERT INTO `Cast`(nombreCast)
    VALUES
    (${person})
    """.stripMargin
      .update
      .apply())*/

  // ------------------------------------------------------------------------------------------------

  // consulta 1

  /*val entities: List[Map[String, Any]] = sql"SELECT * FROM Cast WHERE nombreCast LIKE '%Chris%'"
    .map(_.toMap)
    .list.apply()

  // consulta 2

  val entities2: List[Map[String, Any]] = sql"SELECT COUNT(*) FROM Cast"
    .map(_.toMap)
    .list.apply()

  // consulta 3

  val entities3: List[Map[String, Any]] = sql"SELECT * FROM Cast WHERE nombreCast LIKE '%jr%'"
    .map(_.toMap)
    .list.apply()

  // consulta 4

  val entities4: List[Map[String, Any]] = sql"SELECT * FROM Cast"
    .map(_.toMap)
    .list.apply()*/

  // ------------------------------------------------------------------------------------------------

}
