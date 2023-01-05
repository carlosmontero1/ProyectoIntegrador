package Main

import com.github.tototoshi.csv.CSVReader
import play.api.libs.json._

import java.io.File

object MainJSON extends App {

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  println("\nJSON")

  val parse = (columna : String) => {
    data.flatMap(x => x.get(columna)).map(Json.parse)
  }

  // columna 11
  val col11 = parse("production_companies")

  val production_companies = {
    val name = col11.flatMap(_ \\ "name")
    val id = col11.flatMap(_ \\ "id")
    name.zip(id)
  }

  val production_companiesGroupBy = production_companies.groupBy{
    case company => company
  }.map{
    case company => (company._1, company._2.size)
  }.toList.sortBy(_._2)

  val maxPC = production_companiesGroupBy.maxBy(_._2)._1._1
  val maxPCId = production_companiesGroupBy.maxBy(_._2)._1._2
  val maxPCNum = production_companiesGroupBy.maxBy(_._2)._2

  val minPC = production_companiesGroupBy.minBy(_._2)._1._1
  val minPCId = production_companiesGroupBy.minBy(_._2)._1._2
  val minPCNum = production_companiesGroupBy.minBy(_._2)._2

  //println(production_companiesGroupBy)

  println("\nLa production_company con MAS participaciones es: \t" + maxPC
    + "\n\tcon el id: \t" + maxPCId
    + "\n\tcon la cantidad:  \t" + maxPCNum)

  println("\nLa production_company con MENOS participaciones es: \t" + minPC
    + "\n\tcon el id: \t" + minPCId
    + "\n\tcon la cantidad:  \t" + minPCNum)

  // columna 12

  val col12 = parse("production_countries")

  val production_countries = {
    val iso_3166_1 = col12.flatMap(_ \\ "iso_3166_1")
    val name = col12.flatMap(_ \\ "name")
    iso_3166_1.zip(name)
  }

  val production_countriesGroupBy = production_countries.groupBy {
    case country => country
  }.map {
    case country => (country._1, country._2.size)
  }.toList.sortBy(_._2)

  val maxPCountryIso = production_countriesGroupBy.maxBy(_._2)._1._1
  val maxPCountryName = production_countriesGroupBy.maxBy(_._2)._1._2
  val maxPCountryNum = production_countriesGroupBy.maxBy(_._2)._2

  val minPCountryIso = production_countriesGroupBy.minBy(_._2)._1._1
  val minPCountryName = production_countriesGroupBy.minBy(_._2)._1._2
  val minPCountryNum = production_countriesGroupBy.minBy(_._2)._2

  println("\nEl pais de production_countries con MAS participaciones es: \t" + maxPCountryName
    + "\n\tcon el iso: \t" + maxPCountryIso
    + "\n\tcon la cantidad:  \t" + maxPCountryNum)

  println("\nEl pais de production_countries con MENOS participaciones es: \t" + minPCountryName
    + "\n\tcon el iso: \t" + minPCountryIso
    + "\n\tcon la cantidad:  \t" + minPCountryNum)

  // columna 16

  val col16 = parse("spoken_languages")

  val spoken_languages = {
    val iso_639_1 = col16.flatMap(_ \\ "iso_639_1")
    val name = col16.flatMap(_ \\ "name")
    iso_639_1.zip(name)
  }

  val spoken_languagesGroupBy = spoken_languages.groupBy {
    case lang => lang
  }.map {
    case lang => (lang._1, lang._2.size)
  }.toList.sortBy(_._2)

  val maxSLIso = spoken_languagesGroupBy.maxBy(_._2)._1._1
  val maxSLName = spoken_languagesGroupBy.maxBy(_._2)._1._2
  val maxSLNum = spoken_languagesGroupBy.maxBy(_._2)._2

  val minSLIso = spoken_languagesGroupBy.minBy(_._2)._1._1
  val minSLName = spoken_languagesGroupBy.minBy(_._2)._1._2
  val minSLNum = spoken_languagesGroupBy.minBy(_._2)._2

  println("\nEl lenguage de spoken_languages con MAS participaciones es: \t" + maxSLName
    + "\n\tcon el iso: \t" + maxSLIso
    + "\n\tcon la cantidad:  \t" + maxSLNum)

  println("\nEl lenguaje de spoken_languages con MENOS participaciones es: \t" + minSLName
    + "\n\tcon el iso: \t" + minSLIso
    + "\n\tcon la cantidad:  \t" + minSLNum)

  // columna 23

  //val col23 = parse("crew")

  /*val col23 = data.flatMap(x => x.get("crew")).map(x => x.replaceAll("'", "\"")).map(Json.parse)

  val crew = {
    val name = col16.flatMap(_ \\ "name")
    val gender = col16.flatMap(_ \\ "gender")
    val department = col16.flatMap(_ \\ "department")
    val job = col16.flatMap(_ \\ "job")
    val credit_id = col16.flatMap(_ \\ "credit_id")
    val id = col16.flatMap(_ \\ "id")
    name.zip(gender).zip(department).zip(job).zip(credit_id).zip(id)
  }

  println(crew)*/


}