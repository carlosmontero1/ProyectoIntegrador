import play.api.libs.json.{JsArray, JsNull, JsNumber, JsObject, JsString, JsValue, Json}

object ExampleJSON {
  def main(args: Array[String]): Unit = {

    // Using string parsing

    val json: JsValue = Json.parse("""
   {
     "name" : "Watership Down",
     "location" : {
       "lat" : 51.235685,
       "long" : -1.309197
     },
     "residents" : [ {
       "name" : "Fiver",
       "age" : 4,
       "role" : null
     }, {
       "name" : "Bigwig",
       "age" : 6,
       "role" : "Owsla"
     } ]
   }
   """)

    println(json.toString())

    // Using class construction

    val json2: JsValue = JsObject(
      Seq(
        "name" -> JsString("Watership Down"),
        "location" -> JsObject(Seq("lat" -> JsNumber(51.235685), "long" -> JsNumber(-1.309197))),
        "residents" -> JsArray(
          IndexedSeq(
            JsObject(
              Seq(
                "name" -> JsString("Fiver"),
                "age" -> JsNumber(4),
                "role" -> JsNull
              )
            ),
            JsObject(
              Seq(
                "name" -> JsString("Bigwig"),
                "age" -> JsNumber(6),
                "role" -> JsString("Owsla")
              )
            )
          )
        )
      )
    )

    println(json2.toString())

    // Transversing

    // Simple path

    // Applying the \ operator to a JsValue will return the property corresponding to the field argument in a JsObject, or the item at that index in a JsArray

    val lat = (json \ "location" \ "lat").get
    println("\nlat: " + lat)

    val fiver = (json \ "residents" \ 0).get
    println("\nfiver: " + fiver)

    val bigwig = (json \ "residents" \ 1).get
    println("\nbigwig: " + bigwig)

    // Recursive Path

    // Applying the \\ operator will do a lookup for the field in the current object and all descendants.

    val name = json("name")
    println("\nname: " + name)

    val bigwig2 = json("residents")(1)
    println(bigwig2)

  }
}
