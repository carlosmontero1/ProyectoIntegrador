// Carlos Montero, Felipe Jimenez, Isaac Quezada
package Main

import com.github.tototoshi.csv.CSVReader

import java.io.File

object MainPrincipal3 extends App {

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  println("\nDISTRIBUCION DE FRECUENCIA")

  val seperateWord = (columna : String) => {

    val types = data.flatMap(x => x.get(columna))
      /*.map(x => x.replace("Science Fiction", "Science-Fiction"))
      .map(x => x.replace("woman director", "woman-director"))
      .map(x => x.replace("independant film", "independant-film"))
      .map(x => x.replace("based on comic book", "based-on-comic-book"))
      .map(x => x.replace("based on novel", "based-on-novel"))
      .map(x => x.replace("difficult childhood", "difficult-childhood"))
      .map(x => x.replace("new york", "new-york"))
      .map(x => x.replace("dying and death", "dying-and-death"))
      .map(x => x.replace("life and death", "life-and-death"))
      .map(x => x.replace("male friendship", "male-friendship"))
      .map(x => x.replace("female friendship", "female-friendship"))
      .map(x => x.replace("secret organization", "secret-organization"))*/
      .flatMap(x => x.split(" ").toList)

    //val typesSet = types.toSet

    println("\n---------------------------------------------------------------------------")
    //println("\nTipos de " + columna + ": \n\n" + typesSet.toList.sorted)

    val group = types.groupBy{
      case x => x
    }.map{
      case x => (x._1, x._2.size)
    }.toList.sortBy(_._2)

    println("\n" + group)

  }

  val seperateLine = (columna: String) => {

    val types = data.flatMap(x => x.get(columna))

    //val typesSet = types.toSet

    println("\n---------------------------------------------------------------------------")
    //println("\nTipos de " + columna + ": \n\n" + typesSet.toList.sorted)

    val group = types.groupBy {
      case x => x
    }.map {
      case x => (x._1, x._2.size)
    }.toList.sortBy(_._2)

    println("\n" + group)

  }

  val columnaMaxMin = (columna : String) => {
    val columnaGroupBy = data.flatMap(x => x.get(columna)).filter(_.nonEmpty).groupBy {
      case x => x
    }.map {
      case x => (x._1, x._2.size)
    }

    println("\n" + columna + " que -MAS- aparece: \t" + columnaGroupBy.maxBy(_._2)._1 +
      "\n\tcon la cantidad: \t" + columnaGroupBy.maxBy(_._2)._2)

    println("\n" + columna + " que -MENOS- aparece: \t" + columnaGroupBy.minBy(_._2)._1 +
      "\n\tcon la cantidad: \t" + columnaGroupBy.minBy(_._2)._2)
  }

  // columna 3
  seperateWord("genres")
  // *** Science Fiction esta separado pero debe ir junto, se utilizo un .replace para que se convierta en Science-Fiction ***

  columnaMaxMin("genres")

  // columna 6
  seperateWord("keywords")

  // columna 7
  seperateLine("original_language")

  columnaMaxMin("original_language")

  // columna 8
  //seperateWord("original_title")

  println("\n---------------------------------------------------------------------------\n")
  columnaMaxMin("original_title")

  // columna 9
  //seperateWord("overview")

  // columna 17
  seperateLine("status")

  columnaMaxMin("status")

  // columna 18
  //seperateWord("tagline")

  // columna 19
  //seperateLine("title")

  val duplicateTitle = data.flatMap(x => x.get("title")).groupBy{
    case title => title
  }.map{
    title => (title._1, title._2.size)
  }.filter(_._2 > 1)

  println("\n---------------------------------------------------------------------------")
  println("\nTitulos duplicados: \t" + duplicateTitle)

  // columna 22
  //seperateWord("cast")

  // columna 24
  //seperateLine("director")

  val directorGroupBy = data.flatMap(x => x.get("director")).groupBy {
    case director => director
  }.map {
    director => (director._1, director._2.size)
  }.toList.filter(_._2 > 1).sortBy(_._2)

  println("\n---------------------------------------------------------------------------")
  println("\nDirectores con mas de 1 pelicula: \n" + directorGroupBy)

  columnaMaxMin("director")

}