// Carlos Montero, Felipe Jimenez, Isaac Quezada
package Main

import com.github.tototoshi.csv.CSVReader

import java.io.File

object MainPrincipal2 extends App {

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  println("\nDISTRIBUCION DE FRECUENCIA")

  val isEmpty = (columna: String) => {

    val bool = data.flatMap(x => x.get(columna)).groupBy {
      case columna => columna.isEmpty
    }.map {
      case columna => (columna._1, columna._2.size)
    }

    println("\n" + columna + " -------------------------------------------------------------- ")

    println("\n\tisEmpty: " + bool)

    bool.get(false) match {
      case Some(value) => println("\t\tValores ingresados: " + value)
      case None => println("\t\tNo existen valores ingresados")
    }

    bool.get(true) match {
      case Some(value) => println("\t\tValores vacios: " + value)
      case None => println("\t\tNo existen valores vacios")
    }

  }

  // columna 3
  isEmpty("genres")

  val peliculasSinGenero = data.map(x => (x.get("original_title").get, x.get("genres").get)).filter(x => x._2.isEmpty).map(_._1)
  println("\nPeliculas sin genres: \n" + peliculasSinGenero)

  // columna 4
  isEmpty("homepage")

  // columna 6
  isEmpty("keywords")

  // columna 7
  isEmpty("original_language")

  // columna 8
  isEmpty("original_title")

  // columna 9
  isEmpty("overview")

  val peliculasSinOverview = data.map(x => (x.get("original_title").get, x.get("overview").get)).filter(x => x._2.isEmpty).map(_._1)
  println("\nPeliculas sin overview: \n" + peliculasSinOverview)

  // columna 13
  isEmpty("release_date")

  val peliculasSinReleaseDate = data.map(x => (x.get("original_title").get, x.get("release_date").get)).filter(x => x._2.isEmpty).map(_._1)
  println("\nPeliculas sin release_date: \n" + peliculasSinReleaseDate)

  // columna 17
  isEmpty("status")

  // columna 18
  isEmpty("tagline")

  // columna 19
  isEmpty("title")

  // columna 22
  isEmpty("cast")

  val peliculasSinCast= data.map(x => (x.get("original_title").get, x.get("cast").get)).filter(x => x._2.isEmpty).map(_._1)
  println("\nPeliculas sin cast: \n" + peliculasSinCast)

  // columna 24
  isEmpty("director")

  val peliculasSinDirector = data.map(x => (x.get("original_title").get, x.get("director").get)).filter(x => x._2.isEmpty).map(_._1)
  println("\nPeliculas sin cast: \n" + peliculasSinDirector)

}