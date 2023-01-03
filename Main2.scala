import com.github.tototoshi.csv.CSVReader

import java.io.File

object Main2 extends App {

  val reader = CSVReader.open(new File("/Users/carlosmontero/Desktop/movie_dataset.csv"))
  val data: List[Map[String, String]] = {
    reader.allWithHeaders()
  }
  reader.close()

  // Anáis de datos en columnas numéricas (estadísticas básicas: MmP)

  // index

  /*print("\nindex\n")

  val MIndice = data.flatMap(x => x.get("index")).maxBy(x => x.toInt)
  println("\nMayor index: " + MIndice)

  val mIndice = data.flatMap(x => x.get("index")).minBy(x => x.toInt)
  println("Menor index: " + mIndice)

  val PIndice = data.flatMap(x => x.get("index")).map(x => x.toInt).sum / data.flatMap(x => x.get("index")).size.toDouble
  println("Promedio index: " + PIndice)*/

  // budget

  //print("\nbudget\n")

  /*val MBudget = data.flatMap(x => x.get("budget")).maxBy(x => x.toInt)
  println("\nMayor budget: " + MBudget)

  val mBudget = data.flatMap(x => x.get("budget")).minBy(x => x.toInt)
  println("Menor budget: " + mBudget)

  val PBudget = data.flatMap(x => x.get("budget")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).size
  println("Promedio budget: " + PBudget)

  val PBudgetSinCero = data.flatMap(x => x.get("budget")).map(x => x.toDouble).sum / data.flatMap(x => x.get("budget")).map(x => x.toDouble).filter(x => x != 0.0).size
  println("Promedio budget (sin incluir 0): " + PBudgetSinCero)*/

  /*val budgetIsEmpty = data.flatMap(x => x.get("budget")).groupBy{
    case x => x.isEmpty
  }.map{
    case x => (x._1, x._2.size)
  }
  println(budgetIsEmpty)*/

  /*val homepageIsEmpty = data.flatMap(x => x.get("homepage")).groupBy {
    case x => x.isEmpty
  }.map {
    case x => (x._1, x._2.size)
  }
  println("\nHomepage isEmpty: \n" + homepageIsEmpty)*/

  // id

  /*print("\nid\n")

  val MId = data.flatMap(x => x.get("id")).maxBy(x => x.toInt)
  println("\nMayor id: " + MId)

  val mId = data.flatMap(x => x.get("id")).minBy(x => x.toInt)
  println("Menor id: " + mId)

  val PId = data.flatMap(x => x.get("id")).map(x => x.toDouble).sum / data.flatMap(x => x.get("id")).size
  println("Promedio id: " + PId)*/

  /*val idIsEmpty = data.flatMap(x => x.get("id")).groupBy {
    case x => x.isEmpty
  }.map {
    case x => (x._1, x._2.size)
  }
  println("\nHomepage isEmpty: \n" + idIsEmpty)*/

  // popularity

  /*print("\npopularity\n")

  val MPopularity = data.flatMap(x => x.get("popularity")).maxBy(x => x.toDouble)
  println("\nMayor popularity: " + MPopularity)

  val mPopularity = data.flatMap(x => x.get("popularity")).minBy(x => x.toDouble)
  println("Menor popularity: " + mPopularity)

  val PPopularity = data.flatMap(x => x.get("popularity")).map(x => x.toDouble).sum / data.flatMap(x => x.get("id")).size
  println("Promedio popularity: " + PPopularity)*/

  // OJO, ESTAN RESALTADOS LOS NUMEROS QUE SOLAMENTE TIENEN 3 DIGITOS DESPUES DE LA COMA, Y EL NUMERO 875 DEBERIA SER 87,5

  // revenue

  /*print("\nrevenue\n")

  val MRevenue = data.flatMap(x => x.get("revenue")).maxBy(x => x.toDouble)
  println("\nMayor revenue: " + MRevenue)

  val mRevenue = data.flatMap(x => x.get("revenue")).minBy(x => x.toDouble)
  println("Menor revenue: " + mRevenue)

  val PRevenue = data.flatMap(x => x.get("revenue")).map(x => x.toDouble).sum / data.flatMap(x => x.get("id")).size
  println("Promedio revenue: " + PRevenue)*/

  // runtime

  /*print("\nruntime\n")

  val lista = data.flatMap(x => x.get("runtime")).filter(x => x.contains("."))
  println(lista.sortBy(x => x))

  val MRuntime = data.flatMap(x => x.get("runtime")).filter(x => x.contains(".")).maxBy(x => x.toDouble)
  println("\nMayor runtime: " + MRuntime)

  val mRuntime = data.flatMap(x => x.get("runtime")).filter(x => x.contains(".")).minBy(x => x.toDouble)
  println("Menor runtime: " + mRuntime)

  val PRuntime = data.flatMap(x => x.get("runtime")).filter(x => x.contains(".")).map(x => x.toDouble).sum / data.flatMap(x => x.get("id")).size
  println("Promedio runtime: " + PRuntime)*/

  // En el runtime, se esta leyendo el double con la coma a dos digitos desde la iquierda a derecha, lee 338 como 33,8
  // En case de ser serie, el runtime suma el tiempo de cada capitulo de la serie
  // Cuando se utiliza maxBy(x => x) linea 94, el resultado es 99.0
  // Cuando se utiliza maxBy(x => x.toDouble) linea 94, el resultado es 338.0

  // vote_average

  /*print("\nvote_average\n")

  val MVoteAverage = data.flatMap(x => x.get("vote_average")).maxBy(x => x.toDouble)
  println("\nMayor vote_average: " + MVoteAverage)

  val mVoteAverage = data.flatMap(x => x.get("vote_average")).minBy(x => x.toDouble)
  println("Menor vote_average: " + mVoteAverage)

  val PVoteAverage = data.flatMap(x => x.get("vote_average")).map(x => x.toDouble).sum / data.flatMap(x => x.get("id")).size
  println("Promedio vote_average: " + PVoteAverage)*/

  // vote_count

  /*print("\nvote_count\n")

  val MVoteCount = data.flatMap(x => x.get("vote_count")).maxBy(x => x.toInt)
  println("\nMayor vote_average: " + MVoteCount)

  val mVoteCount = data.flatMap(x => x.get("vote_count")).minBy(x => x.toInt)
  println("Menor vote_average: " + mVoteCount)

  val PVoteCount = data.flatMap(x => x.get("vote_count")).map(x => x.toDouble).sum / data.flatMap(x => x.get("id")).size
  println("Promedio vote_average: " + PVoteCount)*/

  println("------------------------------------------------------------------------------------------")

  // Análisis de datos en columnas tipo texto (algunas col. - distribución de frecuencia).
  // OJO: no considerara columnas en formato JSON.

  // ADD significa Analisis de Datos

  // genres

  /*val genresTypes = data.flatMap(x => x.get("genres")).flatMap(x => x.split(" ").toList).toSet
  println("\nTipos de generos: \n" + genresTypes.toList.sorted)

  val genresADDCount = {
    val genres = data.flatMap(x => x.get("genres")).flatMap(x => x.split(" ").toList)
    genres.groupBy{
      case genres => genres
    }.map{
      case genres => (genres._1, genres._2.size)
    }
  }

  println("\nCantidad de peliculas por genero: \n" + genresADDCount)*/

  // Science fiction deberia estar junto, un humano sabe que van juntos pero la computadora no, asi es que se debe realizar
  // los pasos necesarios para unirlos o no separarlos desde un inicio.


  /*val genresADDIsEmpty = {
    val genres = data.flatMap(x => x.get("genres"))
    genres.groupBy {
      case homepage => homepage.isEmpty
    }.map {
      case homepage => (homepage._1, homepage._2.size)
    }
  }

  println("\nGenero isEmpty: \n" + genresADDIsEmpty)

  println("\n\tPeliculas que -SI- tienen genero: " + genresADDIsEmpty.filter(x => !x._1).head._2)
  println("\tPeliculas que -NO- tienen genero: " + genresADDIsEmpty.filter(x => x._1).head._2)*/

  // homepage

  //val homepageTypes = data.flatMap(x => x.get("homepage")).toSet
  //println("\nPaginas Web: " + homepageTypes.toList.sorted)

  /*val homepageADDIsEmpty = {
    val homepage = data.flatMap(x => x.get("homepage"))
    homepage.groupBy {
      case homepage => homepage.isEmpty
    }.map {
      case homepage => (homepage._1, homepage._2.size)
    }
  }

  println("\nPeliculas que tienen pagina web: \n" + homepageADDIsEmpty)

  println("\n\tPeliculas que -SI- tienen pagina web: " + homepageADDIsEmpty.filter(x => !x._1).head._2)
  println("\tPeliculas que -NO- tienen pagina web: " + homepageADDIsEmpty.filter(x => x._1).head._2)*/

  // keywords

  /*val keywordsTypes = data.flatMap(x => x.get("keywords")).flatMap(x => x.split(" ").toList).toSet
  println("\nNumero de keywords: \n" + keywordsTypes.size)

  val keywordsADDCountSeperateKeywords = {
    val keywords = data.flatMap(x => x.get("keywords")).flatMap(x => x.split(" ").toList)
    keywords.groupBy {
      case keywords => keywords
    }.map {
      case keywords => (keywords._1, keywords._2.size)
    }
  }

  println("\nNumero de peliculas por keywords (separadas): \n" + keywordsADDCountSeperateKeywords.toList.sortBy(x => x._2))*/

  /*val keywordsADDCountEntireKeywords = {
    val keywords = data.flatMap(x => x.get("keywords"))
    keywords.groupBy {
      case keywords => keywords
    }.map {
      case keywords => (keywords._1, keywords._2.size)
    }
  }

  println("\n\n\n\n\n\nNumero de peliculas por keywords (no separadas): \n" + keywordsADDCountEntireKeywords.toList.sortBy(x => x._2))*/

  /*val keywordsADDIsEmpty = {
    val keywords = data.flatMap(x => x.get("keywords"))
    keywords.groupBy {
      case keywords => keywords.isEmpty
    }.map {
      case keywords => (keywords._1, keywords._2.size)
    }
  }

  println("\nPeliculas que tienen keywords: \n" + keywordsADDIsEmpty)
  println("\n\tPeliculas que -SI- tienen keywords: " + keywordsADDIsEmpty.filter(x => !x._1).head._2)
  println("\tPeliculas que -NO- tienen keywords: " + keywordsADDIsEmpty.filter(x => x._1).head._2)*/

  // original_language

  /*val originalLanguageTypes = data.flatMap(x => x.get("original_language")).toSet
  println("\nTipos de original_language: \n" + originalLanguageTypes.toList.sorted)

  val languageADDCount = {
    val original_language = data.flatMap(x => x.get("original_language"))
    original_language.groupBy {
      case original_language => original_language
    }.map {
      case original_language => (original_language._1, original_language._2.size)
    }.toList.sortBy(x => x._2)
  }

  println("\nNumero de peliculas segun su original_language: \n" + languageADDCount)*/

  /*val original_languageIsEmpty = data.flatMap(x => x.get("original_language")).groupBy{
    case x => x.isEmpty
  }.map{
    case x => (x._1, x._2.size)
  }

  println(original_languageIsEmpty)*/

  // original_title

  /* val originalTitleTypes = data.flatMap(x => x.get("original_title")).toSet
   println("\nTipos de original_title: " + originalTitleTypes.size)

   val originalTitleADDCount = {
     val original_title = data.flatMap(x => x.get("original_title"))
     original_title.groupBy {
       case original_title => original_title
     }.map {
       case original_title => (original_title._1, original_title._2.size)
     }
   }

   println("\n" + originalTitleADDCount.toList.sortBy(x => x._2))

   println("\n\tTitulos que se repiten: " + originalTitleADDCount.filter(x => x._2 == 2).map(x => x._1))
   println("\tTitulos que se repiten: " + originalTitleADDCount.filter(x => x._2 == 2))*/

  // overview

  /*val overviewTypes = data.flatMap(x => x.get("overview")).flatMap(x => x.split(" ").toList).toSet.size
  println("\nDifferent overview words: " + overviewTypes)

  val overviewADDCountSeperateKeywords = {
    val overview = data.flatMap(x => x.get("overview")).flatMap(x => x.split(" ").toList)
    overview.groupBy {
      case overview => overview
    }.map {
      case overview => (overview._1, overview._2.size)
    }
  }

  println("\n" + overviewADDCountSeperateKeywords.toList.sortBy(x => x._2))*/

  // overview agrupado por filas (ninguno se repite)
  //  val overviewADDCountEntireKeywords = {
  //    val overview = data.flatMap(x => x.get("overview"))
  //    overview.groupBy {
  //      case overview => overview
  //    }.map {
  //      case overview => (overview._1, overview._2.size)
  //    }
  //  }
  //
  //  println("\n" + overviewADDCountEntireKeywords)

  /*val overviewADDIsEmpty = {
    val overview = data.flatMap(x => x.get("overview"))
    overview.groupBy {
      case overview => overview.isEmpty
    }.map {
      case overview => (overview._1, overview._2.size)
    }
  }

  println("\nOverview isEmpty: \n" + overviewADDIsEmpty)
  println("\n\tPeliculas que -SI- tienen overview: " + overviewADDIsEmpty.filter(x => !x._1).head._2)
  println("\tPeliculas que -NO- tienen overview: " + overviewADDIsEmpty.filter(x => x._1).head._2)*/

  // El resultado dice que existen 3 peliculas sin overview, y desde el excel parecen ser 4
  // Solo que uno (The Helix... Loaded) tiene un espacio en overview lo cual hace que no sea vacio.

  /*

    // Agrupo todos los overviews con sus title

    val overviewADDIsEmpty2 = {
      val genres = data.map(x => (x.get("overview").get, x.get("title").get))
      genres
    }

    println(overviewADDIsEmpty2.sortBy(x => x._2))

  */

  // release_date

  /*val release_dateADDCount = {
    val years = data.flatMap(x => x.get("release_date")).flatMap(x => x.split("-", 2)).filter(x => !x.contains('-'))
    years.groupBy{
      case year => year
    }.map {
      case year => (year._1, year._2.size)
    }.toList.sortBy(x => x._2)
  }

  println("\nNumero de peliculas segun el año de su release_date: \n" + release_dateADDCount)*/

  /*val release_dateIsEmpty = data.flatMap(x => x.get("release_date")).groupBy{
    case x => x.isEmpty
  }.map{
    case x => (x._1, x._2.size)
  }

  println(release_dateIsEmpty)*/

  /*val revenueIsEmpty = data.flatMap(x => x.get("revenue")).map(x => x.toDouble).filter(x => x % 1 == 0).size

  println(revenueIsEmpty)*/

  // status

  /*val statusType = data.flatMap(x => x.get("status")).toSet
  println("\nTipos de status: \n" + statusType)

  val statusADDCount = {
    val status = data.flatMap(x => x.get("status"))
    status.groupBy {
      case status => status
    }.map {
      case status => (status._1, status._2.size)
    }.toList.sortBy(x => x._2)
  }

  println("\nNumero de peliculas segun su status: \n" + statusADDCount)*/

  // tagline

  /*val taglineADDCountSeperateKeywords = {
    val title = data.flatMap(x => x.get("tagline")).flatMap(x => x.split(" ").toList)
    title.groupBy {
      case title => title
    }.map {
      case title => (title._1, title._2.size)
    }
  }

  println("\n" + taglineADDCountSeperateKeywords.toList.sortBy(x => x._2))*/

  // title

  /*val titleADDCountEntireKeywords = {
    val title = data.flatMap(x => x.get("title"))
    title.groupBy {
      case title => title
    }.map {
      case title => (title._1, title._2.size)
    }
  }

  println("\nPeliculas sin overview: " + titleADDCountEntireKeywords)*/

  /*val titleADDCountSeperateKeywords = {
    val title = data.flatMap(x => x.get("title")).flatMap(x => x.split(" ").toList)
    title.groupBy {
      case title => title
    }.map {
      case title => (title._1, title._2.size)
    }
  }

  println("\n" + titleADDCountSeperateKeywords.toList.sortBy(x => x._2))*/

  // cast

  /*val castADDCountSeperateNames = {
    val title = data.flatMap(x => x.get("cast")).flatMap(x => x.split(" ").toList)
    title.groupBy {
      case title => title
    }.map {
      case title => (title._1, title._2.size)
    }
  }

  println("\nNumero de veces que nombre de cast member sale en peliculas: \n" + castADDCountSeperateNames.toList.sortBy(x => x._2))
  println("\nNumero de veces que aparece el nombre DiCaprio: \n" + castADDCountSeperateNames.toList.filter(x => x._1 == "DiCaprio"))
  println("\nNumero de veces que aparece el nombre Will: \n" + castADDCountSeperateNames.toList.filter(x => x._1 == "Will"))
  println("\nNumero de veces que aparece el nombre Isaac: \n" + castADDCountSeperateNames.toList.filter(x => x._1 == "Isaac"))
  println("\nNumero de veces que aparece el nombre Felipe: \n" + castADDCountSeperateNames.toList.filter(x => x._1 == "Felipe"))
  println("\nNumero de veces que aparece el nombre Carlos: \n" + castADDCountSeperateNames.toList.filter(x => x._1 == "Carlos"))
  println("\nNumero de veces que aparece el nombre Lopez: \n" + castADDCountSeperateNames.toList.filter(x => x._1 == "Lopez"))*/

  // Existen nombres de actores que incluyen Jr. o segundos nombres o segundos apellidos y no se puede separar los nombres por
  // cada dos palabras.

  // director

  /*val directorADDCount = {
    val director = data.flatMap(x => x.get("director"))
    director.groupBy {
      case director => director
    }.map {
      case director => (director._1, director._2.size)
    }.toList.sortBy(x => x._2)
  }

  println("\nDirector y numero de peliculas: " + directorADDCount)

  val directorADDIsEmpty = {
    val director = data.flatMap(x => x.get("director"))
    director.groupBy {
      case director => director.isEmpty
    }.map {
      case director => (director._1, director._2.size)
    }
  }

  println("\nOverview isEmpty: \n" + directorADDIsEmpty)
  println("\n\tPeliculas que -SI- tienen overview: " + directorADDIsEmpty.filter(x => !x._1).head._2)
  println("\tPeliculas que -NO- tienen overview: " + directorADDIsEmpty.filter(x => x._1).head._2)*/


}
