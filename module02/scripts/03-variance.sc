abstract class Food {
  val name: String
}

abstract class Fruit extends Food
case class Banana(name: String) extends Fruit
case class Apple(name: String) extends Fruit

abstract class Cereal extends Food
case class Granola(name: String) extends Cereal
case class Muesli(name: String) extends Cereal

val fuji = Apple("Fuji")
val alpen = Muesli("Alpen")

case class FoodBowl[F <: Food](contents: F) {
  override def toString: String = s"A yummy bowl of ${contents.name}s"
}

// invariance

def serveToFruitEater(fruitBowl: FoodBowl[Fruit]) =
  s"mmmm, those ${fruitBowl.contents.name}s were very good"

val fruitBowl = FoodBowl[Fruit](fuji)
val cerealBowl = FoodBowl[Cereal](alpen)

serveToFruitEater(fruitBowl)

//serveToFruitEater(cerealBowl) // expecting FoodBowl[Fruit]

val NewAppleBowl1 = FoodBowl[Apple](fuji)
val NewAppleBowl2 = FoodBowl(fuji)

//serveToFruitEater(NewAppleBowl1)
//serveToFruitEater(NewAppleBowl2)
serveToFruitEater(FoodBowl(fuji))

def serveToFoodEater(foodBowl: FoodBowl[Food]) =
  s"mmmm, I really liked that ${foodBowl.contents.name}"

//serveToFoodEater(fruitBowl)

val foodBowl1 = FoodBowl[Food](fuji)
val foodBowl2 = FoodBowl[Food](alpen)

serveToFoodEater(foodBowl1)
serveToFoodEater(foodBowl2)

// serveToFoodEater(cerealBowl)

// covariance

case class FoodBowl2[+F <: Food](contents: F) {
  override def toString: String = s"A yummy bowl of ${contents.name}s"
}
// note the +F

class FoodBowl5 extends FoodBowl2[Fruit](fuji)

val appleBowl3 = FoodBowl2(fuji)
val muesliBowl3 = FoodBowl2(alpen)
val fruitBowl3 = FoodBowl2[Fruit](fuji)
val cerealBowl3 = FoodBowl2[Cereal](alpen)

def serveToFoodEater2(foodBowl: FoodBowl2[Food]) =
  s"mmmm, I really liked that ${foodBowl.contents.name}"

val appleBowl = FoodBowl2(fuji)  // FoodBowl2[Apple]
val fruitBowl1 = FoodBowl2[Fruit](fuji)  // FoodBowl2[Fruit]

val muesliBowl1 = FoodBowl2(alpen)  // FoodBowl2[Muesli]
val cerealBowl1 = FoodBowl2[Cereal](alpen)  // FoodBowl2[Cereal]

val newFoodBowl1 = FoodBowl2[Food](fuji)  // FoodBowl2[Food]
val newFoodBowl2 = FoodBowl2[Food](alpen)  // FoodBowl2[Food]

serveToFoodEater2(appleBowl)
serveToFoodEater2(fruitBowl1)
serveToFoodEater2(muesliBowl1)
serveToFoodEater2(cerealBowl1)
serveToFoodEater2(newFoodBowl1)
serveToFoodEater2(newFoodBowl2)

serveToFoodEater2(new FoodBowl5)

// here serveToFoodEater2 takes Food Type bcuz of invarience
// we can pass Fruit, Apple, Cereal, Muesli types to this method
// cuz Food is parent type for all so here
// case class FoodBowl2 we are giving +F it will allow
// even if the child type parameter passed to the method


def serveToFruitEater2(foodBowl: FoodBowl2[Fruit]) =
  s"Nice fruity ${foodBowl.contents.name}"

serveToFruitEater2(FoodBowl2[Fruit](fuji))
serveToFruitEater2(FoodBowl2(fuji))

// serveToFruitEater2(FoodBowl2(alpen))
// serveToFruitEater2(FoodBowl2[Food](fuji))


case class FoodBowl3[+T <: Fruit](f: T){
  override def toString: String = s"mmm, these ${f.name}s very tasty"
  def contents =  f
}

val fruitBowl4 = FoodBowl3(fuji)
val fruitBowl5 = FoodBowl3[Fruit](fuji)
val fruitBowl6 = FoodBowl3[Apple](fuji)
//val cerealBowl4 = FoodBowl3[Cereal](alpen)
//val cerealBowl5 = FoodBowl3[Fruit](alpen)

def serveToFruitEater3(fruitbowl: FoodBowl3[Apple]) ={
  s"nice tasty ${fruitBowl.contents.name}"
}

serveToFruitEater3(fruitBowl4)
//serveToFruitEater3(fruitBowl5)
serveToFruitEater3(fruitBowl6)













