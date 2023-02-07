import java.io.File
import com.github.tototoshi.csv.CSVReader
import com.cibo.evilplot.plot.{BarChart, Histogram}
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import play.api.libs.json._

import scala.math._

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Try
import scala.util.matching.Regex


object AnalisisExploratorioColumnasNoNumericas extends App {

  // ------------------------------------------------------------------------------------------------

  // Lectura CSV

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  // ------------------------------------------------------------------------------------------------

  // Diagrama

  implicit val theme = DefaultTheme.copy(
    elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45)
  )

  // ------------------------------------------------------------------------------------------------

  val groupBy = (list: List[String]) => {
    list.groupBy{
      x => x
    }.map{
      x => (x._1, x._2.size)
    }
      .toList
      .sortBy(_._2)
      .reverse
  }

  // ------------------------------------------------------------------------------------------------

  // Genres

  val genres = data
    .flatMap(x => x.get("genres"))
    .filter(_.nonEmpty)
    .map(x => x.replace("Science Fiction", "Science-Fiction"))
    .flatMap(x => x.split(" ").toList)

  val genresGroupBy = groupBy(genres)


  println("Generos + Apariciones\n-------------------------")
  genresGroupBy.foreach(println(_))

  // Diagrama Genre

  BarChart(genresGroupBy.map(_._2))
    .title("Genres")
    .xAxis(genresGroupBy.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Genres.png"))

  // ------------------------------------------------------------------------------------------------

  // Keywords

  val keywords = data
    .flatMap(x => x.get("keywords"))
    .filter(_.nonEmpty)
    .map(x => x.replace(" and ", "-and-"))
    .map(x => x.replace(" or ", "-or-"))
    .map(x => x.replace(" on ", "-on-"))
    .map(x => x.replace(" of ", "-of-"))
    .map(x => x.replace(" friendship", "-friendship"))
    .map(x => x.replace("woman director", "woman-director"))
    .map(x => x.replace("independant film", "independant-film"))
    .map(x => x.replace("difficult childhood", "difficult-childhood"))
    .map(x => x.replace("new york", "new-york"))
    .map(x => x.replace("secret organization", "secret-organization"))
    .map(x => x.replace("brother sister relationship", "brother-sister-relationship"))
    .map(x => x.replace("sister sister relationship", "sister-sister-relationship"))
    .map(x => x.replace("brother brother relationship", "brother-brother-relationship"))
    .map(x => x.replace("father son relationship", "father-son-relationship"))
    .map(x => x.replace("family relationship", "family-relationship"))
    .map(x => x.replace("wife husband relationship", "wife-husband-relationship"))
    .map(x => x.replace("grandfather grandson relationship", "grandfather-grandson-relationship"))
    .map(x => x.replace("independent film", "independent-film"))
    .flatMap(x => x.split(" ").toList)

  val keywordsGroupBy = groupBy(keywords)

  val top5Keywords = keywordsGroupBy.take(5)

  println("\nTop 5 Keywords:\n-------------------------")
  top5Keywords.map(_._1).foreach(println(_))

// Diagrama Keywords

  BarChart(top5Keywords.map(_._2))
    .title("Keywords")
    .xAxis(top5Keywords.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Keywords.png"))

  // ------------------------------------------------------------------------------------------------

  // Title

  val title = data
    .flatMap(x => x.get("title"))

  val titleGroupBy = groupBy(title)
  val repeatedTitles = titleGroupBy.filter(_._2 > 1)

  println("\nRepeated Titles:\n-------------------------")
  repeatedTitles.map(_._1).foreach(println(_))

// Diagrama title

  BarChart(repeatedTitles.map(_._2))
    .title("Title")
    .xAxis(repeatedTitles.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Title.png"))

  // ------------------------------------------------------------------------------------------------

  // Funcion (Lista de Objetos Formato JSON)

  val parse = (columna: String) => {
    data.flatMap(x => x.get(columna)).filter(_.nonEmpty).map(Json.parse)
  }

  // ------------------------------------------------------------------------------------------------

  // production_companies

  val production_companies = parse("production_companies")

  val production_companiesName = production_companies.flatMap(_ \\ "name").map(_.toString)
  val production_companiesGroupBy = groupBy(production_companiesName)

  val top10Production_companies = production_companiesGroupBy.take(10)

  println("\nTop 10 production companies:\n-------------------------")
  top10Production_companies.map(_._1).foreach(println(_))

  // Diagrama Top 10 Production_companies

  BarChart(top10Production_companies.map(_._2))
    .title("Top 10 Production_companies")
    .xAxis(top10Production_companies.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Production_companiesTop10.png"))

  // ------------------------------------------------------------------------------------------------

  // production_countries

  val production_countries = parse("production_countries")

  val production_countriesName = production_countries.flatMap(_ \\ "name").map(_.toString)
  val production_countriesGroupBy = groupBy(production_countriesName)

  val top10Production_countries = production_countriesGroupBy.take(10)

  println("\nTop 10 Production Countries:\n-------------------------")
  top10Production_countries.map(_._1).foreach(println(_))

  // Diagrama Top 10 Production_countries

  BarChart(top10Production_countries.map(_._2))
    .title("Top 10 Production_countries")
    .xAxis(top10Production_countries.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Production_countriesTop10.png"))

  // ------------------------------------------------------------------------------------------------

  // Date formatter

  val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  val releaseDateList = data
    .map(row => row("release_date"))
    .filter(!_.equals(""))
    .map(text => LocalDate.parse(text, dateFormatter))

  val yearReleaseList = releaseDateList
    .map(_.getYear)
    .map(_.toDouble)

  val yearGroupBy = groupBy(yearReleaseList.map(_.toInt.toString))
  println(yearGroupBy)

  val top10Year = yearGroupBy.take(10)

  println("\nTop 10 Años + Apariciones\n-------------------------")
  top10Year.foreach(println(_))

  // Diagrama Top 10 Años en los que hubieron mas peliculas

  BarChart(top10Year.map(_._2))
    .title("Top 10 años")
    .xAxis(top10Year.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/TopAños.png"))

  printf("\nMayor Año: %d", yearReleaseList.min.toInt)
  printf("\nMenor Año: %d\n", yearReleaseList.max.toInt)

  // Histograma

  Histogram(yearReleaseList)
    .title("Años de lanzamiento")
    .xAxis()
    .yAxis()
    .xbounds(1916.0, 2018.0)
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Year.png"))

  // ------------------------------------------------------------------------------------------------

  // Spoken Languages

  val spoken_languages = parse("spoken_languages")

  val spoken_languagesName = spoken_languages.flatMap(_ \\ "name").map(_.toString)
  val spoken_languagesGroupBy = groupBy(spoken_languagesName)

  val top10spoken_languages = spoken_languagesGroupBy.take(10)

  println("\nTop 10 Spoken Languages:\n-------------------------")
  top10spoken_languages.map(_._1).foreach(println(_))

  BarChart(top10spoken_languages.map(_._2))
    .title("Top 10 spoken_languages")
    .xAxis(top10spoken_languages.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Spoken_languagesTop10.png"))

  // ------------------------------------------------------------------------------------------------

  // status
  /*

  */
  val status = data
    .flatMap(x => x.get("status"))

  val statusGroupBy = groupBy(status)

  println("\nStatus:\n-------------------------")
  statusGroupBy.map(_._1).foreach(println(_))

  println("\nStatus + Apariciones:\n-------------------------")
  statusGroupBy.foreach(println(_))

  BarChart(statusGroupBy.map(_._2).map(x => cbrt(x)))
    .title("Status")
    .xAxis(statusGroupBy.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Status.png"))

  // ------------------------------------------------------------------------------------------------

  // REPLACE PATTERN

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

  val pattern: Regex = "(\\s\"(.*?)\",)".r
  val pattern2: Regex = "([a-z]\\s\"(.*?)\"\\s*[A-Z])".r
  val pattern3: Regex = "(:\\s'\"(.*?)',)".r

  val patternsTR = List(
    (pattern2, "\"", "-u0022"),
    (pattern, "'", "-u0027"),
    (pattern3, "\"", "-u0022")
  )

  val crew = data
    .map(row => row("crew"))
    .map(funcion(_, patternsTR))
    .map(text => text.replace("'", "\""))
    .map(text => text.replace("-u0027", "'"))
    .map(text => text.replace("-u0022", "\\\""))
    .map(text => Try(Json.parse(text)))

  // ------------------------------------------------------------------------------------------------

  // Department

  val crewDepartments = crew.map(x => x.get).flatMap(_ \\ "department").map(_.toString)
  val crewDepartmentsDistinct = crew.map(x => x.get).flatMap(_ \\ "department").map(_.toString).distinct

  val crewDepartmentsGroupBy = groupBy(crewDepartments)

  println("\nDepartments:\n-------------------------")

  crewDepartmentsDistinct.foreach(println(_))

  // ------------------------------------------------------------------------------------------------

  // Job

  val crewJobs = crew.map(x => x.get).flatMap(_ \\ "job").map(_.toString)
  val crewJobsDistinct = crew.map(x => x.get).flatMap(_ \\ "job").map(_.toString).distinct
  val crewJobsGroupBy = groupBy(crewJobs)

  println("\nJobs:\n-------------------------\n" + crewJobsDistinct)


  val top5Jobs = crewJobsGroupBy.take(5)

  val topFiveJobName = top5Jobs.map(_._1)
  val topFiveJobCount = top5Jobs.map(_._2.toDouble)

  BarChart(topFiveJobCount)
    .title("Compañías productoras")
    .xAxis(topFiveJobName)
    .yAxis()
    .frame()
    .yLabel("Productions")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Top5Deparments.png"))

  // ------------------------------------------------------------------------------------------------

  val gender = crew.map(x => x.get).flatMap(_ \\ "gender").map(_.toString)
  val genderDistinct = crew.map(x => x.get).flatMap(_ \\ "gender").map(_.toString).distinct

  val genderGroupBy = groupBy(gender)

  println("\nGenders: ")
  genderDistinct.foreach(println(_))

  println("\nGenders + Apariciones:\n-------------------------")
  genderGroupBy.foreach(println(_))

  /*PieChart(genderGroupBy)
    .title("Genders")
    .rightLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Gender.png"))*/

  BarChart(genderGroupBy.map(_._2))
    .title("Genders")
    .xAxis(genderGroupBy.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Gender2.png"))

  // ------------------------------------------------------------------------------------------------

  // Verificar si el id le pertence a una unica persona o se repite

  /*val id = crew.map(x => x.get).flatMap(_ \\ "id")
  val idName = crew.map(x => x.get).flatMap(_ \\ "name")

  val idNameZip = id.zip(idName)

  val repeatedNamesId = idNameZip.groupBy {
    case x => x._1
  }.map {
    case x => (x._1, x._2.size)
  }

  println(repeatedNamesId.filter(_._2 > 1.0))*/

  // Consulta

  val bradPitt = data
    .map(x => (x("title"), x("budget"), x("cast")))
    .filter(x => x._3.contains("Brad Pitt"))
    .sortBy(_._2.toInt)
    .reverse
    .take(10)

  println("\nTop 10 peliculas con mayor budget en las que aparece el actor Brad Pitt:\n-------------------------")
  bradPitt.map(x => (x._1, x._2)).foreach(println(_))


}
