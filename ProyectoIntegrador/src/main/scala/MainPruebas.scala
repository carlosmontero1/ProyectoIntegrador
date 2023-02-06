import com.github.tototoshi.csv.CSVReader
import scalikejdbc.{AutoSession, ConnectionPool, DBSession}
import scalikejdbc._
import play.api.libs.json._

import java.io.File
import scala.util.Try
import scala.util.matching.Regex

import com.cibo.evilplot._
import com.cibo.evilplot.plot._
import com.cibo.evilplot.plot.aesthetics.DefaultTheme._

object MainPruebas extends App {

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


  /*
  val data = Seq((1, 10), (2, 100), (3, 1000), (4, 10000))
  val plot = LinePlot(data, x = "X", y = "Y",
    xLabel = "Eje X", yLabel = "Eje Y (log)",
    yAxis = LogAxis()
  )

  display(plot, width = 800, height = 600)

 */

}