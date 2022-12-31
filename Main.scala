import com.github.tototoshi.csv.CSVReader

import java.io.File

object Main extends App {

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  //¿Cuál es el presupuesto más alto que se ha invertido en una película?

  val highestBudget = data.flatMap(x => x.get("budget")).maxBy(x => x.toInt)
  println("\nPresupuesto mas alto: " + highestBudget)

  //¿Cuál es el presupuesto más bajo que se ha invertido en una película? (Sin incluir 0)

  val lowestBudget = data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).minBy(x => x.toInt)
  println("\nPresupuesto mas bajo (sin incluir 0): " + lowestBudget)

  //¿Cuánto presupuesto se invierte en promedio en las películas?

  val promedioConCero = data.flatMap(x => x.get("budget")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
  println("\nPromedio de presuuesto mas bajo (sin incluir 0): " + promedioConCero)

  val promedioSinCero = data.flatMap(x => x.get("budget")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
  println("\nPresupuesto mas bajo (incluyendo 0): " + promedioSinCero)

  //¿Cuál es el nombre de la película que ha necesitado el presupuesto más alto?

  val nombrePeliculaMasPresupuesto = {
    val highestBudget = data.flatMap(x => x.get("budget")).maxBy(x => x.toInt)
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("budget") match {
        case Some(x) => x.toInt
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == highestBudget.toInt).unzip._1
    pelicula.head
  }
  println("\nPelicula con presupuesto mas alto: " + nombrePeliculaMasPresupuesto)

  // ---------------------------------------------------------------------------------------------//

  //¿Cuál es el nombre de la película que ha necesitado el presupuesto más bajo (Sin incluir 0)?

  val nombrePeliculaMenosPresupuesto = {
    val lowestBudget = data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).minBy(x => x.toInt)
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("budget") match {
        case Some(x) => x.toInt
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == lowestBudget.toInt).unzip._1
    pelicula.head
  }
  println("\nPelicula con presupuesto mas bajo: " + nombrePeliculaMenosPresupuesto)

  //¿Cuales son los nombres de peliculas que tuvieron un presupuesto menor a 100000?

  val PresupestoMenor100000 = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("budget") match {
        case Some(x) => x.toInt
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 < 100000).unzip._1
    pelicula
  }
  println("\nPeliculas con presupuesto menor a 100000: " + PresupestoMenor100000)

  //¿Numero de peliculas que tuvieron un presupuesto menor a 100000?

  val numeroPresupestoMenor100000 = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("budget") match {
        case Some(x) => x.toInt
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 < 100000).unzip._1
    pelicula.count(x => x.nonEmpty)
  }
  println("\nNumero de peliculas con presupuesto menor a 100000: " + numeroPresupestoMenor100000)

  //¿Cuales son los nombres de peliculas que tuvieron un presupuesto mayor a 2000000?

  val PresupestoMayor2000000 = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("budget") match {
        case Some(x) => x.toInt
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 > 2000000).unzip._1
    pelicula
  }
  println("\nPeliculas con presupuesto mayor a 2000000: " + PresupestoMayor2000000)

  //¿Numero de peliculas que tuvieron un presupuesto mayor a 2000000?

  val numeroPresupestoMayor2000000 = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("budget") match {
        case Some(x) => x.toInt
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 > 2000000).unzip._1
    pelicula.count(x => x.nonEmpty)
  }
  println("\nNumero de peliculas con presupuesto mayor a 2000000: " + numeroPresupestoMayor2000000)

  //¿Numero total de peliculas?

  val numeroTotal = data.count(x => x.nonEmpty)
  println("\nNumero total de peliculas: " + numeroTotal)

  //¿Numero de peliculas del genero Comedia?

  val numeroComedia = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("genres") match {
        case Some(x) => x
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == "Comedy").map(_._1)
    pelicula.count(x => x.nonEmpty)
  }
  println("\nNumero de peliculas de Comedia: " + numeroComedia)

  //¿Numero de peliculas del genero Drama?

  val numeroDrama = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("genres") match {
        case Some(x) => x
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == "Drama").map(_._1)
    pelicula.count(x => x.nonEmpty)
  }
  println("\nNumero de peliculas de Drama: " + numeroDrama)

  //¿Numero de peliculas del genero Documentary?

  val numeroDocumentarios = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("genres") match {
        case Some(x) => x
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == "Documentary").map(_._1)
    pelicula.count(x => x.nonEmpty)
  }
  println("\nNumero de peliculas de Documentarios: " + numeroDocumentarios)

  // ¿Cuanto dinero en total se ha invertido en todas las peliculas

  val totalBudget = data.flatMap(x => x.get("budget")).map(_.toDouble).sum
  println("\nPresupuesto total de todas las peliculas: " + totalBudget)

  //¿Peliculas con idioma original Frances?

  val lenguajeFrances = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("original_language") match {
        case Some(x) => x
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == "fr").map(_._1)
    pelicula
  }
  println("\nPeliculas de Documentarios: " + lenguajeFrances)

  //¿Numero de peliculas con idioma original Frances?

  val numLenguajeFrances = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("original_language") match {
        case Some(x) => x
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == "fr").map(_._1)
    pelicula.count(x => x.nonEmpty)
  }
  println("\nPeliculas de Documentarios: " + numLenguajeFrances)

  //¿Peliculas con idioma original Frances?

  val lenguajeItaliano = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("original_language") match {
        case Some(x) => x
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == "it").map(_._1)
    pelicula
  }
  println("\nPeliculas de Documentarios: " + lenguajeItaliano)

  //¿Numero de peliculas con idioma original Frances?

  val numLenguajeItaliano = {
    val movieBudget = data.map(x => (
      x.get("original_title") match {
        case Some(x) => x
      },
      x.get("original_language") match {
        case Some(x) => x
      }
    ))
    val pelicula = movieBudget.filter(x => x._2 == "it").map(_._1)
    pelicula.count(x => x.nonEmpty)
  }
  println("\nPeliculas de Documentarios: " + numLenguajeItaliano)

  //¿Nombre de la pelicula mas popular?

  val masPopular = data.flatMap(x => x.get("popularity")).maxBy(x => x.nonEmpty)
  println("\nPelicula mas popular: " + masPopular)


}
