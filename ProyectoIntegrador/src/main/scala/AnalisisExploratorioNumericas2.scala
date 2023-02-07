import java.io.File
import com.github.tototoshi.csv.CSVReader
import com.cibo.evilplot.plot.{BarChart, PieChart}
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}
import com.cibo.evilplot.plot.renderers.LegendRenderer

import scala.math._
import scala.math.BigDecimal.RoundingMode

object AnalisisExploratorioNumericas2 extends App {

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
    list.groupBy {
      x => x
    }.map {
      x => (x._1, x._2.size)
    }
      .toList
      .sortBy(_._1)
      .reverse
  }

  // ------------------------------------------------------------------------------------------------

  val isEmpty = (columna: String) => {

    val bool = data
      .flatMap(x => x.get(columna))
      .groupBy{
        x => x.isEmpty
      }.map{
      x => (columna, x._1, x._2.size)
    }

    bool.filter(_._2 == false).filter(_._3 != 4803)

  }

  val listEmpty = List(
    isEmpty("budget"),
    isEmpty("genres"),
    isEmpty("homepage"),
    isEmpty("keywords"),
    isEmpty("original_language"),
    isEmpty("overview"),
    isEmpty("popularity"),
    isEmpty("production_companies"),
    isEmpty("production_countries"),
    isEmpty("release_date"),
    isEmpty("revenue"),
    isEmpty("runtime"),
    isEmpty("spoken_languages"),
    isEmpty("spoken_languages"),
    isEmpty("status"),
    isEmpty("tagline"),
    isEmpty("vote_average"),
    isEmpty("vote_count"),
    isEmpty("vote_count"),
    isEmpty("cast"),
    isEmpty("crew"),
    isEmpty("director"),
  ).flatten

  listEmpty.foreach(println(_))

  val columnName = listEmpty.map(_._1)
  val amountNonEmpty = listEmpty.map(_._3.toDouble)

  val percentage = listEmpty
    .map(_._3 / 4803.0 * 100)
    .map(BigDecimal(_).setScale(2, RoundingMode.HALF_UP))

  //println(percentage)

  BarChart(amountNonEmpty)
    .title("Amount of inserted values in columns")
    .xAxis(columnName)
    .yAxis()
    .frame()
    .yLabel("Valor")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/isEmpty.png"))

  // ------------------------------------------------------------------------------------------------

  val budget = data
    .map(x => (x("title"), x("budget").toLong))
    .filter(_._1.nonEmpty)
    .sortBy(_._2)
    .reverse
    .take(10)

  BarChart(budget.map(_._2.toDouble))
    .title("Top 10 movies por mayor presupuesto")
    .xAxis(budget.map(_._1))
    .yAxis()
    .ybounds(200000000)
    .frame()
    .yLabel("Valor")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Top10BudgetMovies.png"))

  // ------------------------------------------------------------------------------------------------

  val popularity = data
    .map(x => (x("title"), x("popularity").toDouble))
    .sortBy(_._2)
    .reverse
    .take(10)

  BarChart(popularity.map(_._2))
    .title("Top 10 movies por mayor popularity")
    .xAxis(popularity.map(_._1))
    .yAxis()
    .ybounds(0)
    .frame()
    .yLabel("Valor")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Top10PopularityMovies.png"))

  // ------------------------------------------------------------------------------------------------

  val revenue = data
    .map(x => (x("title"), x("revenue").toLong))
    .sortBy(_._2)
    .reverse
    .take(10)

  BarChart(revenue.map(_._2.toDouble))
    .title("Top 10 movies por mayor revenue")
    .xAxis(revenue.map(_._1))
    .yAxis()
    .ybounds(0)
    .frame()
    .yLabel("Valor")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Top10RevenueMovies.png"))

  // ------------------------------------------------------------------------------------------------

  val runtime = data
    .map(x => (x("title"), x("runtime")))
    .filter(_._2.contains("."))
    .sortBy(_._2.toDouble)
    .reverse
    .take(10)

  BarChart(runtime.map(_._2.toDouble))
    .title("Top 10 movies por mayor runtime")
    .xAxis(runtime.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Top10RuntimeMovies.png"))

  // ------------------------------------------------------------------------------------------------

  val vote_average = data
    .map(x => (x("title"), x("vote_average")))
    .filter(_._2.contains("."))
    .sortBy(_._2.toDouble)
    .reverse
    .take(10)

  BarChart(vote_average.map(_._2.toDouble))
    .title("Top 10 movies por mayor vote_average")
    .xAxis(vote_average.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Top10Vote_averageMovies.png"))

  // ------------------------------------------------------------------------------------------------

  val vote_count = data
    .map(x => (x("title"), x("vote_count")))
    .filter(_._2.nonEmpty)
    .sortBy(_._2.toInt)
    .reverse
    .take(10)

  BarChart(vote_count.map(_._2.toDouble))
    .title("Top 10 movies por mayor vote_count")
    .xAxis(vote_count.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Top10Vote_countMovies.png"))

  // ------------------------------------------------------------------------------------------------

  val budgetIntervals = data
    .flatMap(x => x.get("budget"))
    .filter(_.nonEmpty)
    .map(_.toLong)
    .groupBy {
      case x if x >= 0 && x <= 100000000 => ("0 until 100000000", x)
      case x if x > 100000000 && x <= 200000000 => ("100000001 until 200000000", x)
      case x if x > 200000000 && x <= 300000000 => ("200000001 until 300000000", x)
      case x if x > 300000000 && x <= 400000000 => ("300000001 until 400000000", x)
    }.map(x => (x._1, x._2.size))

  // comprobacion

  val a = budgetIntervals
    .filter(_._1._1 == "0 until 100000000")
    .map(_._2)
    .sum

  val b = budgetIntervals
    .filter(_._1._1 == "100000001 until 200000000")
    .map(_._2)
    .sum

  val c = budgetIntervals
    .filter(_._1._1 == "200000001 until 300000000")
    .map(_._2)
    .sum

  val d = budgetIntervals
    .filter(_._1._1 == "300000001 until 400000000")
    .map(_._2)
    .sum

  // suma da 4803
  println(a + b + c + d)

  // individualmente
  println(a)
  println(b)
  println(c)
  println(d)

  //println(budgetIntervals)

  val budgetRango = budgetIntervals.map(_._1._1).toSet.toList
  // Las cantidades para un Barchar logartimico
  val budgetCantidades = List(cbrt(a), cbrt(b), cbrt(c), cbrt(d))

  BarChart(budgetCantidades.map(_.toDouble))
    .title("Amount of movies by budget intervals")
    .xAxis(budgetRango)
    .yAxis()
    .frame()
    .yLabel("Valor en Base 3")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/BudgetIntervals.png"))

  // ------------------------------------------------------------------------------------------------

  val popularityIntervals = data
    .flatMap(x => x.get("popularity"))
    .filter(_.nonEmpty)
    .map(_.toDouble)
    .groupBy {
      case x if x >= 0 && x <= 250 => ("0 until 250", x)
      case x if x > 250 && x <= 500 => ("251 until 500", x)
      case x if x > 500 && x <= 750 => ("501 until 750", x)
      case x if x > 750 && x <= 1000 => ("751 until 1000", x)
    }.map(x => (x._1, x._2.size))

  // comprobacion

  val a1 = popularityIntervals
    .filter(_._1._1 == "0 until 250")
    .map(_._2)
    .sum

  val b1 = popularityIntervals
    .filter(_._1._1 == "251 until 500")
    .map(_._2)
    .sum

  val c1 = popularityIntervals
    .filter(_._1._1 == "501 until 750")
    .map(_._2)
    .sum

  val d1 = popularityIntervals
    .filter(_._1._1 == "751 until 1000")
    .map(_._2)
    .sum

  // suma da 4803
  println(a1 + b1 + c1 + d1)

  // individualmente
  println(a1)
  println(b1)
  println(c1)
  println(d1)

  val popularityRango = popularityIntervals.map(_._1._1).toSet.toList
  val popularityCantidades = List(cbrt(a1), cbrt(b1), cbrt(c1), cbrt(d1))

  BarChart(popularityCantidades.map(_.toDouble))
    .title("Amount of movies by popularity intervals")
    .xAxis(popularityRango)
    .yAxis()
    .frame()
    .yLabel("Valor en Base 3")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/PopularityIntervals.png"))

  // ------------------------------------------------------------------------------------------------

  val revenueAverage = data
    .flatMap(x => x.get("revenue"))
    .map(_.toLong)
    .sum.toDouble / data.flatMap(x => x.get("revenue")).map(_.toLong).size

  val revenueIntervals = data
    .flatMap(x => x.get("revenue"))
    .filter(_.nonEmpty)
    .map(_.toLong)
    .groupBy {
      case x if x >= 0.0 && x <= revenueAverage => ("0 to average", x)
      case x if x > revenueAverage => ("Greater than average", x)
    }.map(x => (x._1, x._2.size))

  // comprobacion

  val a2 = revenueIntervals
    .filter(_._1._1 == "0 to average")
    .map(_._2)
    .sum

  val b2 = revenueIntervals
    .filter(_._1._1 == "Greater than average")
    .map(_._2)
    .sum

  // suma da 4803
  println(a2 + b2)

  // individualmente
  println(a2)
  println(b2)

  val revenueRango = revenueIntervals.map(_._1._1).toSet.toList
  val revenueCantidades = List(a2, b2)

  BarChart(revenueCantidades.map(_.toDouble))
    .title("Amount of movies by revenue intervals")
    .xAxis(revenueRango)
    .yAxis()
    .frame()
    .yLabel("Valor")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/RevenueIntervals.png"))

  // ------------------------------------------------------------------------------------------------

  val runtimeIntervals = data
    .flatMap(x => x.get("runtime"))
    .filter(_.contains("."))
    .map(_.toDouble)
    .groupBy {
      case x if x >= 0 && x <= 120 => ("0 until 120", x)
      case x if x > 120 && x <= 500 => ("Greater than 121", x)
    }.map(x => (x._1, x._2.size))

  // comprobacion

  val a3 = runtimeIntervals
    .filter(_._1._1 == "0 until 120")
    .map(_._2)
    .sum

  val b3 = runtimeIntervals
    .filter(_._1._1 == "Greater than 121")
    .map(_._2)
    .sum

  // suma da 4803
  println(a3 + b3)

  // individualmente
  println(a3)
  println(b3)

  val runtimeRango = runtimeIntervals.map(_._1._1).toSet.toList
  val runtimeCantidades = List(a3, b3)

  BarChart(runtimeCantidades.map(_.toDouble))
    .title("Amount of movies by runtime intervals")
    .xAxis(runtimeRango)
    .yAxis()
    .frame()
    .yLabel("Valor")
    .xLabel("Minutes")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/RuntimeIntervals.png"))

  // ------------------------------------------------------------------------------------------------

  val vote_averageIntervals = data
    .flatMap(x => x.get("vote_average"))
    .filter(_.contains("."))
    .map(_.toDouble)
    .groupBy {
      case x if x >= 0.0 && x <= 5.0 => ("0 until 5", x)
      case x if x > 5.0 && x <= 10.0 => ("5.1 until 10", x)
    }.map(x => (x._1, x._2.size))

  // comprobacion

  val a4 = vote_averageIntervals
    .filter(_._1._1 == "0 until 5")
    .map(_._2)
    .sum

  val b4 = vote_averageIntervals
    .filter(_._1._1 == "5.1 until 10")
    .map(_._2)
    .sum

  // suma da 4803
  println(a4 + b4)

  // individualmente
  println(a4)
  println(b4)

  val vote_averageRango = vote_averageIntervals.map(_._1._1).toSet.toList
  val vote_averageCantidades = List(a4, b4)

  BarChart(vote_averageCantidades.map(_.toDouble))
    .title("Amount of movies by vote_average intervals")
    .xAxis(vote_averageRango)
    .yAxis()
    .frame()
    .yLabel("Valor")
    .xLabel("Rating")
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Vote_countIntervals.png"))

  // ------------------------------------------------------------------------------------------------

  val isEmptyFalseTrue = (columna: String) => {

    val bool = data
      .flatMap(x => x.get(columna))
      .groupBy {
        x => x.isEmpty
      }.map {
      x => (columna, x._1, x._2.size)
    }

    bool.filter(_._3 != 4803)

  }

  // isEmpty

  val homepage = isEmptyFalseTrue("homepage").map(x => (x._2.toString, x._3.toDouble)).toList

  PieChart(homepage)
    .title("Is the column homepage empty? Amount of values")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/homepageIsEmpty.png"))

  // ------------------------------------------------------------------------------------------------

  val keywords = isEmptyFalseTrue("keywords").map(x => (x._2.toString, x._3.toDouble)).toList

  PieChart(keywords)
    .title("Is the column keywords empty? Amount of values")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/keywordsIsEmpty.png"))

  // ------------------------------------------------------------------------------------------------

  val tagline = isEmptyFalseTrue("tagline").map(x => (x._2.toString, x._3.toDouble)).toList

  PieChart(tagline)
    .title("Is the column tagline empty? Amount of values")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/taglineIsEmpty.png"))



}
