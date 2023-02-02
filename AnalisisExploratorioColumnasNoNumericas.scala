import java.io.File
import com.github.tototoshi.csv.CSVReader
import com.cibo.evilplot.plot.{BarChart, Histogram}
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import play.api.libs.json._

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import scala.util.Try
import scala.util.matching.Regex


object AnalisisExploratorioColumnasNoNumericas extends App {

  // ------------------------------------------------------------------------------------------------

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  // ------------------------------------------------------------------------------------------------

  // diagrama
  implicit val theme = DefaultTheme.copy(
    elements = DefaultElements.copy(categoricalXAxisLabelOrientation = 45)
  )

  // Genres --------------------

  val genres = data
    .flatMap(x => x.get("genres"))
    .filter(_.nonEmpty)
    .map(x => x.replace("Science Fiction", "Science-Fiction"))
    .flatMap(x => x.split(" ").toList)
    .groupBy {
      case x => x
    }.map {
    case x => (x._1, x._2.size)
  }.toList.sortBy(_._2)

  //println(genres)

  // diagramas

  BarChart(genres.map(_._2))
    .title("Genres")
    .xAxis(genres.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Genres.png"))

  // Keywords --------------------

  val keywords = data
    .flatMap(x => x.get("keywords"))
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
    .groupBy {
      case x => x
    }.map {
    case x => (x._1, x._2.size)
  }.toList.sortBy(_._2).reverse

  val top5Keywords = keywords.filter(_._1.nonEmpty).take(5)

  println("\nTop 5 Keywords: ")
  top5Keywords.map(_._1).foreach(println(_))

  BarChart(top5Keywords.map(_._2))
    .title("Keywords")
    .xAxis(top5Keywords.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Keywords.png"))

  // Title --------------------

  val title = data
    .flatMap(x => x.get("title"))
    .groupBy {
      case x => x
    }.map {
    case x => (x._1, x._2.size)
  }.toList.sortBy(_._2).reverse

  val repeatedTitles = title.filter(_._2 > 1)

  println("\nRepeated Titles: ")
  repeatedTitles.map(_._1).foreach(println(_))

  BarChart(repeatedTitles.map(_._2))
    .title("Title")
    .xAxis(repeatedTitles.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Title.png"))

  // Production Companies --------------------

  val parse = (columna: String) => {
    data.flatMap(x => x.get(columna)).map(Json.parse)
  }

  val production_companies = parse("production_companies")

  val production_companies2 = production_companies.flatMap(_ \\ "name")

  val production_companiesGroupBy = production_companies2.groupBy {
    case x => x
  }.map {
    case x => (x._1, x._2.size)
  }.toList.sortBy(_._2).reverse

  val top10Production_companies = production_companiesGroupBy.take(10)

  println("\nTop 10 production companies: ")
  top10Production_companies.map(_._1).foreach(println(_))

  BarChart(top10Production_companies.map(_._2))
    .title("Top 10 Production_companies")
    .xAxis(top10Production_companies.map(_._1.toString()))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Production_companiesTop10.png"))

  // Production Countries --------------------

  val production_countries = parse("production_countries")

  val production_countries2 = production_countries.flatMap(_ \\ "name")

  val production_countriesGroupBy = production_countries2.groupBy {
    case x => x
  }.map {
    case x => (x._1, x._2.size)
  }.toList.sortBy(_._2).reverse

  val top10Production_countries = production_countriesGroupBy.take(10)

  println("Top 10 Production Countries")
  top10Production_countries.map(_._1).foreach(println(_))

  BarChart(top10Production_countries.map(_._2))
    .title("Top 10 Production_countries")
    .xAxis(top10Production_countries.map(_._1.toString()))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Production_countriesTop10.png"))

  // Date formatter --------------------

  val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

  val releaseDateList = data
    .map(row => row("release_date"))
    .filter(!_.equals(""))
    .map(text => LocalDate.parse(text, dateFormatter))

  //val yearReleaseList = releaseDateList.map(_.getYear)

  val yearReleaseList = releaseDateList
    .map(_.getYear)
    .map(_.toDouble)

  printf("\nAño menor: %f", yearReleaseList.min)
  printf("\nAño mayor: %f\n", yearReleaseList.max)

  Histogram(yearReleaseList)
    .title("Años de lanzamiento")
    .xAxis()
    .yAxis()
    .xbounds(1916.0, 2018.0)
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Year.png"))

  // Spoken Languages --------------------

  val spoken_languages = parse("spoken_languages")

  val spoken_languages2 = spoken_languages.flatMap(_ \\ "name")

  val spoken_languagesGroupBy = spoken_languages2.groupBy {
    case x => x
  }.map {
    case x => (x._1, x._2.size)
  }.toList.sortBy(_._2).reverse

  val top10spoken_languages = spoken_languagesGroupBy.take(10)

  println("\nTop 10 Spoken Languages: ")
  top10spoken_languages.map(_._1).foreach(println(_))

  BarChart(top10spoken_languages.map(_._2))
    .title("Top 10 spoken_languages")
    .xAxis(top10spoken_languages.map(_._1.toString()))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Spoken_languagesTop10.png"))

  // status --------------------

  val status = data
    .flatMap(x => x.get("status"))
    .groupBy {
      case x => x
    }.map {
    case x => (x._1, x._2.size)
  }.toList.sortBy(_._2)

  println("\nStatus: ")
  status.map(_._1).foreach(println(_))

  BarChart(status.map(_._2))
    .title("Genres")
    .xAxis(status.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Status.png"))

  // ------------------------------------------------------------------------------------------------

  // REPLACE PATTERN --------------------

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

  // A --------------------

  val crewDepartments = crew.map(x => x.get).flatMap(_ \\ "department")
  val crewDepartmentsDistinct = crew.map(x => x.get).flatMap(_ \\ "department").distinct
  val crewDepartmentsGroupBy = crewDepartments.groupBy {
    case department => department
  }.map {
    case department => (department._1, department._2.size)
  }.toList.sortBy(_._2).reverse

  println("\n3.A All departments: ")
  crewDepartmentsDistinct.foreach(println(_))

  //println("\n3.A All departments: \n" + crewDepartmentsGroupBy)

  // ------------------------------------------------------------------------------------------------

  // B --------------------

  val jobDepartments = crew.map(x => x.get).flatMap(_ \\ "job")
  val jobDepartmentsDistinct = crew.map(x => x.get).flatMap(_ \\ "job").distinct
  val jobDepartmentsGroupBy = crewDepartments.groupBy {
    case job => job
  }.map {
    case job => (job._1, job._2.size)
  }.toList.sortBy(_._2).reverse

  println("\n3.B All jobs: \n" + jobDepartmentsDistinct)

  //println("\n3.B All jobs: \n" + jobDepartmentsGroupBy)

  // -----------

  val topFiveDepartments = crewDepartmentsGroupBy.take(5)

  val topFiveName = topFiveDepartments.map(_._1.toString())
  val topFiveCount = topFiveDepartments.map(_._2.toDouble)

  BarChart(topFiveCount)
    .title("Compañías productoras")
    .xAxis(topFiveName)
    .yAxis()
    .frame()
    .yLabel("Productions")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNoNumericas/Top5Deparments.png"))

  val gender = crew.map(x => x.get).flatMap(_ \\ "gender")
  val genderDistinct = crew.map(x => x.get).flatMap(_ \\ "gender").distinct
  val genderGroupBy = gender.groupBy {
    case gender => gender
  }.map {
    case gender => (gender._1.toString(), gender._2.size.toDouble)
  }.toList.sortBy(_._2)

  println("\n3.A All genders: ")
  genderDistinct.foreach(println(_))

  println("\n3.A All genders: \n" + genderGroupBy)

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

}
