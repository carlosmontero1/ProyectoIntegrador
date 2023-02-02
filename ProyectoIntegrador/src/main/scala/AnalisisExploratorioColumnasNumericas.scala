import java.io.File

import com.github.tototoshi.csv.CSVReader

import com.cibo.evilplot.plot.BarChart
import com.cibo.evilplot.plot.aesthetics.DefaultTheme.{DefaultElements, DefaultTheme}

object AnalisisExploratorioColumnasNumericas extends App {

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

  // Budget --------------------

  //¿Cuál es el Budget más alto que se ha invertido en una película?
  val highestBudget = data.flatMap(x => x.get("budget")).maxBy(x => x.toInt)
  println("\nBudget mas alto: " + highestBudget)

  //¿Cuál es el Budget más bajo que se ha invertido en una película?
  val lowestBudget = data.flatMap(x => x.get("budget")).minBy(x => x.toInt)
  println("\nBudget mas bajo: " + lowestBudget)

  //¿Cuál es el Budget más bajo que se ha invertido en una película? (Sin incluir 0)
  val lowestBudgetSin0 = data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).minBy(x => x.toInt)
  println("\nBudget mas bajo (sin incluir 0): " + lowestBudgetSin0)

  //¿Cuánto Budget se invierte en promedio en las películas?
  val promedioConCero = data.flatMap(x => x.get("budget")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
  println("\nPromedio de Budget (incluyendo 0): " + promedioConCero)

  val promedioSinCero = data.flatMap(x => x.get("budget")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
  println("\nPresupuesto de Budget (sin incluir 0): " + promedioSinCero)

  val budget = List(
    ("Budget mas alto", highestBudget.toDouble),
    ("Budget mas bajo", lowestBudget.toDouble),
    ("Budget mas bajo (sin incluir 0)", lowestBudgetSin0.toDouble),
    ("Promedio de Budget (incluyendo 0)", promedioConCero),
    ("Presupuesto de Budget (sin incluir 0)", promedioSinCero)
  )

  // diagrama

  BarChart(budget.map(_._2))
    .title("Budget")
    .xAxis(budget.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Budget.png"))

  // Popularity --------------------

  //¿Cuál es el popularity más alto que se ha invertido en una película?
  val highestPopularity = data.flatMap(x => x.get("popularity")).maxBy(x => x.toDouble)
  println("\nPopularity mas alto: " + highestPopularity)

  //¿Cuál es el popularity más bajo que se ha invertido en una película?
  val lowestPopularity = data.flatMap(x => x.get("popularity")).minBy(x => x.toDouble)
  println("\nPopularity mas bajo: " + lowestPopularity)

  //¿Cuál es el popularity más bajo que se ha invertido en una película? (Sin incluir 0)
  val lowestPopularitySin0 = data.flatMap(x => x.get("popularity")).filter(x => x.toDouble != 0).minBy(x => x.toDouble)
  println("\nPopularity mas bajo (sin incluir 0): " + lowestPopularitySin0)

  //¿Cuánto popularity se invierte en promedio en las películas?
  val promedioPopularityConCero = data.flatMap(x => x.get("popularity")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
  println("\nPromedio de Popularity (incluyendo 0): " + promedioPopularityConCero)

  val promedioPopularitySinCero = data.flatMap(x => x.get("popularity")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
  println("\nPresupuesto de Popularity (sin incluir 0): " + promedioPopularitySinCero)

  val popularity = List(
    ("Popularity mas alto", highestPopularity.toDouble),
    ("Popularity mas bajo", lowestPopularity.toDouble),
    ("Popularity mas bajo (sin incluir 0)", lowestPopularitySin0.toDouble),
    ("Promedio de Popularity (incluyendo 0)", promedioPopularityConCero),
    ("Popularity de Popularity (sin incluir 0)", promedioPopularitySinCero)
  )

  // diagrama

  BarChart(popularity.map(_._2))
    .title("Popularity")
    .xAxis(popularity.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Popularity.png"))

  // Revenue --------------------

  //¿Cuál es el presupuesto más alto que se ha invertido en una película?
  val highestRevenue = data.flatMap(x => x.get("revenue")).maxBy(x => x.toLong)
  println("\nRevenue mas alto: " + highestRevenue)

  //¿Cuál es el presupuesto más bajo que se ha invertido en una película?
  val lowestRevenue = data.flatMap(x => x.get("revenue")).minBy(x => x.toLong)
  println("\nRevenue mas bajo: " + lowestRevenue)

  //¿Cuál es el presupuesto más bajo que se ha invertido en una película? (Sin incluir 0)
  val lowestRevenueSin0 = data.flatMap(x => x.get("revenue")).filter(x => x.toLong != 0).minBy(x => x.toLong)
  println("\nRevenue mas bajo (sin incluir 0): " + lowestRevenueSin0)

  //¿Cuánto presupuesto se invierte en promedio en las películas?
  val promedioRevenueConCero = data.flatMap(x => x.get("revenue")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
  println("\nPromedio de Revenue (incluyendo 0): " + promedioRevenueConCero)

  val promedioRevenueSinCero = data.flatMap(x => x.get("revenue")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
  println("\nPresupuesto de Revenue (sin incluir 0): " + promedioRevenueSinCero)

  val revenue = List(
    ("Revenue mas alto", highestRevenue.toDouble),
    ("Revenue mas bajo", lowestRevenue.toDouble),
    ("Revenue mas bajo (sin incluir 0)", lowestRevenueSin0.toDouble),
    ("Promedio de Revenue (incluyendo 0)", promedioRevenueConCero),
    ("Presupuesto de Revenue (sin incluir 0)", promedioRevenueSinCero)
  )

  // diagrama

  BarChart(revenue.map(_._2))
    .title("Revenue")
    .xAxis(revenue.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Revenue.png"))

  // Runtime --------------------

  //¿Cuál es el runtime más alto que se ha invertido en una película?
  val highestRuntime = data.flatMap(x => x.get("runtime")).filter(_.contains(".")).maxBy(x => x.toDouble)
  println("\nRuntime mas alto: " + highestRuntime)

  //¿Cuál es el runtime más bajo que se ha invertido en una película?
  val lowestRuntime = data.flatMap(x => x.get("runtime")).filter(_.contains(".")).minBy(x => x.toDouble)
  println("\nRuntime mas bajo: " + lowestRuntime)

  //¿Cuál es el runtime más bajo que se ha invertido en una película? (Sin incluir 0)
  val lowestRuntimeSin0 = data.flatMap(x => x.get("runtime")).filter(_.contains(".")).filter(x => x.toDouble != 0).minBy(x => x.toDouble)
  println("\nRuntime mas bajo (sin incluir 0): " + lowestRuntimeSin0)

  //¿Cuánto runtime se invierte en promedio en las películas?
  val promedioRuntimeConCero = data.flatMap(x => x.get("runtime")).filter(_.contains(".")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
  println("\nPromedio de Runtime (incluyendo 0): " + promedioRuntimeConCero)

  val promedioRuntimeSinCero = data.flatMap(x => x.get("runtime")).filter(_.contains(".")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
  println("\nPresupuesto de Runtime (sin incluir 0): " + promedioRuntimeSinCero)

  val runtime = List(
    ("Runtime mas alto", highestRuntime.toDouble),
    ("Runtime mas bajo", lowestRuntime.toDouble),
    ("Runtime mas bajo (sin incluir 0)", lowestRuntimeSin0.toDouble),
    ("Promedio de Runtime (incluyendo 0)", promedioRuntimeConCero),
    ("Presupuesto de Runtime (sin incluir 0)", promedioRuntimeSinCero)
  )

  // diagrama

  BarChart(runtime.map(_._2))
    .title("Runtime")
    .xAxis(runtime.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Runtime.png"))

  // Vote_average --------------------

  //¿Cuál es el highestVote_average más alto que se ha invertido en una película?

  val highestVote_average = data.flatMap(x => x.get("vote_average")).filter(_.contains(".")).maxBy(x => x.toDouble)
  println("\nVote_average mas alto: " + highestVote_average)

  //¿Cuál es el highestVote_average más bajo que se ha invertido en una película?

  val lowestVote_average = data.flatMap(x => x.get("vote_average")).filter(_.contains(".")).minBy(x => x.toDouble)
  println("\nVote_average mas bajo: " + lowestVote_average)

  //¿Cuál es el highestVote_average más bajo que se ha invertido en una película? (Sin incluir 0)

  val lowestVote_averageSin0 = data.flatMap(x => x.get("vote_average")).filter(_.contains(".")).filter(x => x.toDouble != 0).minBy(x => x.toDouble)
  println("\nVote_average mas bajo (sin incluir 0): " + lowestVote_averageSin0)

  //¿Cuánto highestVote_average se invierte en promedio en las películas?

  val promedioVote_averageConCero = data.flatMap(x => x.get("vote_average")).filter(_.contains(".")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
  println("\nPromedio de Vote_average (incluyendo 0): " + promedioVote_averageConCero)

  val promedioVote_averageSinCero = data.flatMap(x => x.get("vote_average")).filter(_.contains(".")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
  println("\nPresupuesto de Vote_average (sin incluir 0): " + promedioVote_averageSinCero)

  val vote_average = List(
    ("Vote_average mas alto", highestVote_average.toDouble),
    ("Vote_average mas bajo", lowestVote_average.toDouble),
    ("Vote_average mas bajo (sin incluir 0)", lowestVote_averageSin0.toDouble),
    ("Promedio de Vote_average (incluyendo 0)", promedioVote_averageConCero),
    ("Presupuesto de Vote_average (sin incluir 0)", promedioVote_averageSinCero)
  )

  // diagramas

  BarChart(vote_average.map(_._2))
    .title("Vote_average")
    .xAxis(vote_average.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Vote_average.png"))

  // Vote_count --------------------

  //¿Cuál es el vote_count más alto que se ha invertido en una película?
  val highestVote_count = data.flatMap(x => x.get("vote_count")).maxBy(x => x.toInt)
  println("\nVote_count mas alto: " + highestVote_count)

  //¿Cuál es el vote_count más bajo que se ha invertido en una película?
  val lowestVote_count = data.flatMap(x => x.get("vote_count")).minBy(x => x.toInt)
  println("\nVote_count mas bajo: " + lowestVote_count)

  //¿Cuál es el vote_count más bajo que se ha invertido en una película? (Sin incluir 0)
  val lowestVote_countSin0 = data.flatMap(x => x.get("vote_count")).filter(x => x.toInt != 0).minBy(x => x.toInt)
  println("\nVote_count mas bajo (sin incluir 0): " + lowestVote_countSin0)

  //¿Cuánto vote_count se invierte en promedio en las películas?
  val promedioVote_countConCero = data.flatMap(x => x.get("vote_count")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
  println("\nPromedio de Vote_count (incluyendo 0): " + promedioVote_countConCero)

  val promedioVote_countSinCero = data.flatMap(x => x.get("vote_count")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
  println("\nPresupuesto de Vote_count (sin incluir 0): " + promedioVote_countSinCero)

  val vote_count = List(
    ("Vote_count mas alto", highestVote_count.toDouble),
    ("Vote_count mas bajo", lowestVote_count.toDouble),
    ("Vote_count mas bajo (sin incluir 0)", lowestVote_countSin0.toDouble),
    ("Promedio de Vote_count (incluyendo 0)", promedioVote_countConCero),
    ("Presupuesto de Vote_count (sin incluir 0)", promedioVote_countSinCero)
  )

  // diagrama

  BarChart(vote_count.map(_._2))
    .title("Vote_count")
    .xAxis(vote_count.map(_._1))
    .yAxis()
    .frame()
    .yLabel("Valor")
    .bottomLegend()
    .render()
    .write(new File("/Users/carlosmontero/Desktop/ColumnasNumericas/Vote_count.png"))


}
