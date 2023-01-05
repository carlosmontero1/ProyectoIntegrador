package Main

import com.github.tototoshi.csv.CSVReader

import java.io.File

object MainPrincipal extends App {

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  println("\nCALCULOS MmP")

  val calculosMmPInt = (columna: String) => {

    println("\n" + columna + " -------------------------------------------------------------- ")

    val mayor = data.flatMap(x => x.get(columna)).maxBy(x => x.toLong)
    println("\n\tEl mayor valor de -" + columna + "- es: " + mayor)

    val menor = data.flatMap(x => x.get(columna)).minBy(x => x.toLong)
    println("\tEl menor valor de -" + columna + "- es: " + menor)

    val promedioConCero = data.flatMap(x => x.get(columna)).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
    println("\tEl promedio (incluido el valor 0) de -" + columna + "- es: " + promedioConCero)

    val promedioSinCero = data.flatMap(x => x.get(columna)).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
    println("\tEl promedio (sin incluir el valor 0) de -" + columna + "- es: " + promedioSinCero)

  }

  val calculosMmPDouble = (columna: String) => {

    println("\n" + columna + " -------------------------------------------------------------- ")

    val mayor = data.flatMap(x => x.get(columna)).filter(x => x.contains(".")).maxBy(x => x.toDouble)
    println("\n\tEl mayor valor de -" + columna + "- es: " + mayor)

    val menor = data.flatMap(x => x.get(columna)).filter(x => x.contains(".")).minBy(x => x.toDouble)
    println("\tEl menor valor de -" + columna + "- es: " + menor)

    val promedioConCero = data.flatMap(x => x.get(columna)).filter(x => x.contains(".")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).size
    println("\tEl promedio (incluido el valor 0) de -" + columna + "- es: " + promedioConCero)

    val promedioSinCero = data.flatMap(x => x.get(columna)).filter(x => x.contains(".")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).filter(x => x.toInt != 0).map(x => x.toDouble).size
    println("\tEl promedio (sin incluir el valor 0) de -" + columna + "- es: " + promedioSinCero)

  }

  // -------------------- Int --------------------

  // columna 1
  calculosMmPInt("index")

  // columna 2
  calculosMmPInt("budget")

  // columna 5
  calculosMmPInt("id")

  // columna 14
  calculosMmPInt("revenue")
  // *** no utilizamos .toInt debido a que el numero es muy grande para ser un int asi que utilizamos .toLong ***

  //columna 21
  calculosMmPInt("vote_count")

  // -------------------- Double --------------------

  // columna 10
  calculosMmPDouble("popularity")

  // columna 15
  calculosMmPDouble("runtime")
  // *** agregamos .filter(x => x.contains(".")) a los casos Double debido a que runtime tiene dos entradas vacias ***
  // *** el filter filtra a todos los strings que tienen . los cuales si pueden ser convertidos a .toDouble

  // columna 20
  calculosMmPDouble("vote_average")

}