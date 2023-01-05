package Main

import play.api.libs.json._

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

    println("\nJson to String\n\n" + json.toString())

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

    println("\nJson to String\n\n" + json2.toString())

    // Transversing

    // Simple path

    // Applying the \ operator to a JsValue will return the property corresponding to the field argument in a JsObject, or the item at that index in a JsArray

    val lat = (json \ "location" \ "lat").get
    println("\n\tlat: \t" + lat)

    val long = (json \ "location" \ "long").get
    println("\n\tlat: \t" + long)

    val fiver = (json \ "residents" \ 0).get
    println("\n\tfiver: \t" + fiver)

    val bigwig = (json \ "residents" \ 1).get
    println("\n\tbigwig: \t" + bigwig)

    // Recursive Path

    // Applying the \\ operator will do a lookup for the field in the current object and all descendants.

    val name = json("name")
    println("\n\tname: \t" + name)

    val bigwig2 = json("residents")(1)
    println("\n\tbigwig: \t" + bigwig2)

    println("\n------------------------------------------------------------------------------\n")

    // Ejemplo 2

    val JsonEx2 = Json.parse("""{"name": "Alice", "age": 25, "email": "alice@example.com", "phone": {"type": "mobile", "number": "123-456-7890"}, "friends": [{"name": "Bob", "age": 30}, {"name": "Charlie", "age": 35}]}""")
    println("\n\tJson: \t" + JsonEx2)

    val nameEx2 = JsonEx2 \\ "name"
    println("\n\tName: \t" + nameEx2)

    val numberEx2 = (JsonEx2 \ "phone" \ "number").get
    println("\n\tPhone: \t" + numberEx2)

    val ageEx2_1 = JsonEx2 \\ "age"
    println("\n\tAge: \t" + ageEx2_1)

    val ageEx2 = (JsonEx2 \ "age").get
    println("\n\tAge: \t" + ageEx2)

    val emailEx2 = (JsonEx2 \ "email").get
    println("\n\tEmail: \t" + emailEx2)

    val phoneTypeEx2 = (JsonEx2 \ "phone" \ "type").get
    println("\n\tPhone Type: \t" + phoneTypeEx2)

    val friendsEx2 = (JsonEx2 \\ "friends").flatMap(_ \\ "name")
    println("\n\tFriends: \t" + friendsEx2)



  }
}
