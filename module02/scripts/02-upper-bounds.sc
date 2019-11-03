
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

def eat(f: Food): String = s"${f.name} eaten"

case class Bowl[F](contents: F) {
  override def toString: String = s"A yummy bowl of ${contents}s"
}

abstract class Animal {
  val name: String
  override def toString: String = s"Animal - $name"
}
case class Dog(name: String) extends Animal
val dottie = Dog("Dottie")
val dogBowl = Bowl(dottie)  // noooo - don't eat my dog

Bowl(fuji)
Bowl[Fruit](fuji)

case class FoodBowl[F <: Food](contents: F) {
  override def toString: String = s"A yummy bowl of ${contents.name}s"
}

val appleBowl = FoodBowl(fuji)
val appleBowl2 = FoodBowl[Fruit](fuji)
//val appleBowl3 = FoodBowl[Apple](alpen)

// val dogBowl2 = FoodBowl(dottie) // throws error expected Food type
// but gave Dog type, still cuz of generics we don't lost
// Food type, FoodBowl[Apple] it don't show simply Food


case class Tiger(name: String) extends Animal
val tiger = Tiger("cheeta")