import com.github.tototoshi.csv.CSVReader
import scalikejdbc._

import java.io.File

object SentenciasDropTable extends App {

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

  val dropTableMovies =
    sql"""
         |DROP TABLE IF EXISTS Movie;
         |""".stripMargin
      .update
      .apply()

  val dropTableStatus =
    sql"""
         |DROP TABLE IF EXISTS `Status`;
         |""".stripMargin
      .update
      .apply()

  val dropTableDirector =
    sql"""
         |DROP TABLE IF EXISTS Director;
         |""".stripMargin
      .update
      .apply()

  val dropTableOriginal_language =
    sql"""
         |DROP TABLE IF EXISTS original_language;
         |""".stripMargin
      .update
      .apply()

  val dropTableGenre =
    sql"""
         |DROP TABLE IF EXISTS Genre;
         |""".stripMargin
      .update
      .apply()

  val dropTableMovie_Genres =
    sql"""
         |DROP TABLE IF EXISTS Movie_Genres;
         |""".stripMargin
      .update
      .apply()

  val dropTableProduction_companies =
    sql"""
         |DROP TABLE IF EXISTS production_companies;
         |""".stripMargin
      .update
      .apply()

  val dropTableMovie_production_companies =
    sql"""
         |DROP TABLE IF EXISTS Movie_production_companies;
         |""".stripMargin
      .update
      .apply()

  val dropTableProduction_countries =
    sql"""
         |DROP TABLE IF EXISTS production_countries;
         |""".stripMargin
      .update
      .apply()

  val dropTableMovie_production_countries =
    sql"""
         |DROP TABLE IF EXISTS Movie_production_countries ;
         |""".stripMargin
      .update
      .apply()

  val dropTableSpoken_languages =
    sql"""
         |DROP TABLE IF EXISTS spoken_languages ;
         |""".stripMargin
      .update
      .apply()

  val dropTableMovie_spoken_langauges =
    sql"""
         |DROP TABLE IF EXISTS Movie_spoken_langauges ;
         |""".stripMargin
      .update
      .apply()

  /*val dropTableCast =
    sql"""
         |DROP TABLE IF EXISTS `Cast` ;
         |""".stripMargin
      .update
      .apply()*/

  val dropTableMovie_Cast =
    sql"""
         |DROP TABLE IF EXISTS Movie_Cast  ;
         |""".stripMargin
      .update
      .apply()

  val dropTableCrew =
    sql"""
         |DROP TABLE IF EXISTS Crew ;
         |""".stripMargin
      .update
      .apply()

  val dropTableMovie_Crew =
    sql"""
         |DROP TABLE IF EXISTS Movie_Crew  ;
         |""".stripMargin
      .update
      .apply()


}
