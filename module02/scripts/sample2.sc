abstract class Food { val name: String }

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

// invarience

def serveToFruitEater(fruitbowl: FoodBowl[Fruit]) = {
  s"mmm, those ${fruitbowl.contents.name}s were very good"
}

val fruitBowl = FoodBowl[Fruit](fuji)
val cerealBowl = FoodBowl[Cereal](alpen)

serveToFruitEater(fruitBowl)
//serveToFruitEater(cerealBowl) // it won't compile cuz it's expecting
                                // FoodBowl[Fruit] not FoodBowl[Cereal]

val appleBowl = FoodBowl[Apple](fuji)
val appleBowl2 = FoodBowl(fuji)
// the above both are same


val muesliBowl = FoodBowl[Muesli](alpen)
val muesliBowl2 = FoodBowl(alpen)
// the above both are same


//serveToFruitEater(appleBowl)
// serveToFruitEater(muesliBowl) //  it won't compile cuz it's expecting
                                // FoodBowl[Fruit] not FoodBowl[Muesli]


serveToFruitEater(FoodBowl(new Fruit {
  override val name: String = "grapes"
}))

class FruitBowl1 extends FoodBowl[Food](fuji)

class FruitBowl2 extends FoodBowl[Fruit](fuji)

class FruitBowl3 extends FoodBowl[Apple](fuji)

def serveToFruitEater2(fruitbowl: FoodBowl[Fruit]) = {
  s"mmm, those ${fruitbowl.contents.name}s were very good"
}

//serveToFruitEater2(new FruitBowl1)
serveToFruitEater2(new FruitBowl2)
//serveToFruitEater2(new FruitBowl3)


def serverToFoodEater(foodbowl: FoodBowl[Food]) = {
  s"mmm, i really like those ${foodbowl.contents.name}"
}

val foodBowl1 = FoodBowl[Food](fuji)
val foodBowl2 = FoodBowl[Food](alpen)

serverToFoodEater(foodBowl1)
serverToFoodEater(foodBowl2)

// serverToFoodEater(appleBowl)  // expecting FoodBowl[Food] actual FoodBowl[Apple]
//serverToFoodEater(muesliBowl)



val appleBowl1 = FoodBowl(fuji)
val muesliBowl1 = FoodBowl(alpen)

appleBowl1 == FoodBowl[Apple](fuji)
appleBowl1 == FoodBowl[Fruit](fuji)
appleBowl1 == FoodBowl[Food](fuji)

muesliBowl1 == FoodBowl[Muesli](alpen)
muesliBowl1 == FoodBowl[Cereal](alpen)
muesliBowl1 == FoodBowl[Food](alpen)




