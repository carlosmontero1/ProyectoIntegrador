import com.github.tototoshi.csv.CSVReader

import play.api.libs.json.{JsValue, Json}
import play.api.libs.json._

import java.io.File

object Main3 extends App {

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  val xx = data.map(x => x.get("production_countries"))
  println(xx)

  val production_countries = data.flatMap(x => x.get("production_countries"))
  //println(production_countries)

  val json: JsValue = Json.parse(
    """
      |{"iso_3166_1": "US", "name": "United States of America"}
      |""".stripMargin
  )

  //println(json)

  val name = json \\ "name"

  //println("\nNombre: " + name.head)

  val production_countriesJSON = production_countries.map(Json.parse)

  val countries = production_countriesJSON.flatMap(_ \\ "name")

  val groupByCountry = countries.groupBy{
    case country => country
  }.map{
    case country => (country._1, country._2.size)
  }

  println(groupByCountry.toList.sortBy(x => x._2))

  //println(countries)


}
