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

eat(fuji)

eat(alpen)

eat(new Food{
  val name = "buvva"})

case class Bowl(food: Food) {
  override def toString = s"A bowl of yummy ${food.name}s"
  def contents = food
}

val fruitBowl = Bowl(fuji)
val cerealBowl = Bowl(alpen)
fruitBowl.contents
cerealBowl.contents

Bowl(new Food {
  override val name: String = "annam"
})

Bowl(Apple("kashmir"))

// here fruitBowl and cerealBowl both are Bowl types
// contents are food type cuz of in case class
// we are explicitely defining the argument type
// is Food so it lost whether it is Apple
// type or cereal type
// for this we need to use generic to avoid losting types

case class Bowl2[F](contents: F) {
  override def toString: String = s"A yummy bowl of ${contents}s"
}

// Invariant behaviour
// as we know in invariant if typed parameter is super
// all subtypes of that type allows

val appleBowl = Bowl2(fuji)
val muesliBowl = Bowl2(alpen)

appleBowl.contents
muesliBowl.contents

val foodBowl1 = Bowl2[Food](fuji)
val foodBowl2 = Bowl2[Food](alpen)

val newFruitBowl = Bowl2[Fruit](fuji)
val newCerealBowl = Bowl2[Cereal](alpen)

//Bowl2[Fruit](alpen)

Bowl2("hello")  // cuz of generic there is no restriction to pass Food as argument

// but this won't work
//case class Bowl3[F](contents: F) {
//  override def toString: String = s"A yummy bowl of ${contents.name}s"
//}

// this is generic feature bcuz of generic we don't lost the type
// but here we are lacking something,
// even if we pass Food type we can't use Food class arguments(contents.name) in side this
// generic class implementation cuz we may pass deifferent values that doesn't contain the
// argument we passed so
// we have to use upper bound concept
// whether if we passing Food then it will be accessed Food elements
