import com.github.tototoshi.csv.CSVReader
import scalikejdbc._

import java.io.File

object SentenciasCreateTable extends App {

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

  // CREATE TABLE

  // MOVIE

  /*val newTableMovie =
    sql"""
         |CREATE TABLE Movie (
         |    `index` int NOT NULL,
         |    budget bigint NOT NULL,
         |    homepage varchar(255),
         |    idMovie int NOT NULL PRIMARY KEY,
         |    keywords varchar(255),
         |    name_original_language varchar(255) NOT NULL,
         |    original_title varchar(255) NOT NULL,
         |    overview TEXT,
         |    popularity double NOT NULL,
         |    release_date varchar(255),
         |    revenue bigint NOT NULL,
         |    runtime double,
         |    nameStatus varchar(255) NOT NULL,
         |    tagline varchar(255),
         |    title varchar(255) NOT NULL,
         |    vote_average double NOT NULL,
         |    vote_count int NOT NULL,
         |    nameDirector varchar(255)
         |);""".stripMargin
      .update
      .apply()*/

  // UNO A MUCHOS

  /*val newTableStatus =
    sql"""
         |CREATE TABLE `Status` (
         |    nameStatus varchar(100) PRIMARY KEY NOT NULL
         |);""".stripMargin
      .update
      .apply()

  val newTableDirector =
    sql"""
         |CREATE TABLE Director (
         |    nameDirector varchar(100) PRIMARY KEY
         |);""".stripMargin
      .update
      .apply()

  val newTableOriginalLanguage =
    sql"""
         |CREATE TABLE original_language (
         |    name_original_language varchar(100) PRIMARY KEY NOT NULL
         |);""".stripMargin
      .update
      .apply()

  // TABLAS APARTE

  val newTableGenre =
    sql"""
         |CREATE TABLE Genre (
         |	nameGenre varchar(100) PRIMARY KEY
         |);""".stripMargin
      .update
      .apply()
  

  val newTableProductionCompanies =
    sql"""
         |CREATE TABLE production_companies (
         |	namePCompany varchar(255),
         |  idPCompany int PRIMARY KEY
         |);""".stripMargin
      .update
      .apply()


  val newTableProductionCountries =
    sql"""
         |CREATE TABLE production_countries (
         |	iso_3166_1 varchar(10) PRIMARY KEY,
         |  namePCountry varchar(255)
         |);""".stripMargin
      .update
      .apply()

  val newTableSpokenLanguages =
    sql"""
         |CREATE TABLE spoken_languages (
         |	iso_639_1 varchar(2) PRIMARY KEY,
         |  nameSLang varchar(255)
         |);""".stripMargin
      .update
      .apply()


  /*val newTableCast =
    sql"""
         |CREATE TABLE `Cast` (
         |    nameCast varchar(255) PRIMARY KEY
         |);""".stripMargin
      .update
      .apply()*/

  val newTableCrew =
    sql"""
         |CREATE TABLE Crew (
         |    nameCrew varchar (255),
         |    gender varchar (255),
         |    department varchar (255),
         |    job varchar (255),
         |    credit_id varchar (255),
         |    idCrew int,
         |    PRIMARY KEY (credit_id)
         |);""".stripMargin
      .update
      .apply()*/

  // MUCHOS A MUCHOS

  val newTableMovieGenres =
    sql"""
         |CREATE TABLE Movie_Genres (
         |	idMovie int,
         |	nameGenre varchar(100),
         |  PRIMARY KEY (idMovie, nameGenre),
         |  FOREIGN KEY (idMovie) REFERENCES Movie(idMovie),
         |  FOREIGN KEY (nameGenre) REFERENCES Genre(nameGenre)
         |);""".stripMargin
      .update
      .apply()

  val newTableMovieProductionCompanies =
    sql"""
         |CREATE TABLE Movie_production_companies (
         |	idMovie int,
         |	idPCompany int,
         |  PRIMARY KEY(idMovie, idPCompany),
         |    FOREIGN KEY (idMovie) REFERENCES Movie(idMovie),
         |    FOREIGN KEY (idPCompany) REFERENCES production_companies(idPCompany)
         |);""".stripMargin
      .update
      .apply()

  val newTableMovieProductionCountries =
    sql"""
         |CREATE TABLE Movie_production_countries (
         |	idMovie int,
         |	iso_3166_1 varchar(10),
         |	PRIMARY KEY(idMovie, iso_3166_1),
         |    FOREIGN KEY (idMovie) REFERENCES Movie(idMovie),
         |    FOREIGN KEY (iso_3166_1) REFERENCES production_countries(iso_3166_1)
         |);""".stripMargin
      .update
      .apply()

  val newTableMovieSpokenLanguages =
    sql"""
         |CREATE TABLE Movie_spoken_langauges (
         |	idMovie int,
         |	iso_639_1 varchar(2),
         |	PRIMARY KEY(idMovie, iso_639_1),
         |    FOREIGN KEY (idMovie) REFERENCES Movie(idMovie),
         |    FOREIGN KEY (iso_639_1) REFERENCES spoken_languages(iso_639_1)
         |);""".stripMargin
      .update
      .apply()

  val newTableMovieCast =
    sql"""
         |CREATE TABLE Movie_Cast (
         |	idMovie int,
         |	nameCast varchar(100),
         |	PRIMARY KEY (idMovie, nameCast),
         |    FOREIGN KEY (idMovie) REFERENCES Movie(idMovie),
         |    FOREIGN KEY (nameCast) REFERENCES `Cast`(nameCast)
         |);""".stripMargin
      .update
      .apply()

  val newTableMovieCrew =
    sql"""
         |CREATE TABLE Movie_Crew (
         |	idMovie int,
         |  credit_id varchar (255),
         |  PRIMARY KEY (idMovie, credit_id),
         |    FOREIGN KEY (idMovie) REFERENCES Movie(idMovie),
         |    FOREIGN KEY (credit_id) REFERENCES Crew(credit_id)
         |);""".stripMargin
      .update
      .apply()


}
