package PreviousMain

import com.github.tototoshi.csv.CSVReader
import play.api.libs.json.Json

import java.io.File

object Main3 extends App {

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  val xx = data.flatMap(x => x.get("production_countries"))
  //println(xx)

  //
  //
  // production_countries

  val production_countries = data.flatMap(x => x.get("production_countries")).map(Json.parse).flatMap(_ \\ "iso_3166_1")

  val production_countriesGroupBy = production_countries.groupBy{
    case country => country
  }.map{
    case country => (country._1, country._2.size)
  }

  println(production_countriesGroupBy.toList.sortBy(x => x._2))
/*
  // Example

  val json: JsValue = Json.parse(
    """
      |{"iso_3166_1": "US", "name": "United States of America"}
      |""".stripMargin
  )

  //println(json)

  val name = json \\ "name"

  //println("\nNombre: " + name.head)*/

  //
  //
  // production_companies

  val countries = data.flatMap(x => x.get("production_countries")).map(Json.parse).flatMap(_ \\ "name")
  //println(countries)

  val groupByCountry = countries.groupBy{
    case country => country
  }.map{
    case country => (country._1, country._2.size)
  }

  //println(groupByCountry.toList.sortBy(x => x._2))

  //
  //
  // spoken_languages

  val spoken_languagesJSON = data.flatMap(x => x.get("spoken_languages")).map(Json.parse).flatMap(_ \\ "name") //iso_639_1

  val groupBySpokenLanguages = spoken_languagesJSON.groupBy{
    case lang => lang
  }.map{
    case lang => (lang._1, lang._2.size)
  }

  println(groupBySpokenLanguages.toList.sortBy(x => x._2))

  //println("El spoken_language mas utilizado es: " + groupBySpokenLanguages.toList.sortBy(x => x._2).maxBy(x => x._2))

  //
  //
  // crew

  /*val crewJSON = data.flatMap(x => x.get("crew")).map(x => x.replace("'", "\"")).map(Json.parse).flatMap(_ \\ "name")

  val crewGroupBy = crewJSON.groupBy{
    case dept => dept
  }.map{
    case dept => (dept._1, dept._2.size)
  }

  println(crewGroupBy)*/

}

