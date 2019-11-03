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
eat(new Food { val name = "mango"})


case class Food2[T <: Food](contents: T) {
  override def toString: String = s"yummy ${contents.name}"
}

class Food3 extends Food2(new Fruit {
  override val name: String = "mango"
})

class Food4 extends Food2(fuji)
class Food5 extends Food2[Fruit](fuji)
class Food6 extends Food2[Apple](fuji)
class Food7 extends Food2[Food](fuji)

def eat2(f: Food2[Fruit]): String = s"${f.contents.name} eaten"

eat2(Food2[Fruit](fuji))

//eat2(Food2[Apple](fuji))
eat2(new Food3)
//eat2(new Food4)
eat2(new Food5)

//eat2(new Food6)

//eat(new Food7)

case class bowl(f: Food) {
  override def toString: String = s"a yummy bowl of ${f.name}"
  def contents = f
}

val fruitBowl = bowl(fuji)
val cerealBowl = bowl(alpen)

fruitBowl.contents  // here both returns Food type
cerealBowl.contents  //here both returns Food type only
                     // cuz we explicitly gave Food type
                     // in case class so it won't give
// whether they are Apple or Muesli

// using generics

case class bowl2[T](contents: T) {
  override def toString: String = s"a yummy bowl of ${contents}s"
}
// now we can't call name on contents cuz we can pass any type here


val fruitBowl2 = bowl2(fuji)
val cerealBowl2 = bowl2(alpen)
val notFoodBowl = bowl2("it's not Food bowl")

val fruitBowl4 = bowl2[Food](fuji)
val fruitBowl5 = bowl2[Apple](fuji)
//val fruitBowl6 = bowl2[Muesli](fuji)


val fruitBowl7 = bowl2[Any](fuji)
val fruitBowl8 = bowl2[AnyRef](fuji)
//val fruitBowl9 = bowl2[String](fuji)
//val fruitBowl9 = bowl2[AnyVal](fuji)

// using upper bounds

case class bowl3[T <: Food](contents: T) {
  override def toString: String = s"a yummy bowl of ${contents.name}s"
}

val appleBowl3 = bowl3(fuji)
val cerealBowl4 = bowl3(alpen)
//val notFoodBowl2 = bowl3("it's not Food bowl") // it throws error
// allows only Food type or subtype of Food
// still we don't lost type system

val foodBowl5: bowl3[Food] = bowl3[Food](fuji)
val foodBowl6: bowl3[Food] = bowl3[Food](alpen)

// val foodBowl7 = bowl3[Any](alpen)
// will throw error accept only Food or subtype of Food
// cuz of upperbound


abstract class Animal {
  val name: String
  override def toString: String = s"Animal -> $name"
}

case class Dog(name: String) extends Animal {
  val color = "white"
}

case class NewBowl[T](f: T){
  override def toString: String = s"a yummy bowl of ${f}s"
}

val dottie = Dog("Dottie")
dottie.color

val dogBowl = NewBowl(dottie)
//dogBowl.color

case class FoodBowl[T <: Food](f: T) {
  override def toString: String = s"a yummy bowl of ${f.name}s"
  def contents = f
}

val foodBowl = FoodBowl(fuji)
foodBowl.contents
foodBowl.contents.name


// val dogBowl2 = FoodBowl(dottie) // it throws error
                                   // bcuz it expecting Food type
                                  // we are passing Dog type so



val foodBowl1 = FoodBowl(fuji)
val foodBowl2 = FoodBowl(alpen)

val newFruitBowl = FoodBowl[Fruit](fuji)
val newCerealBowl = FoodBowl[Cereal](alpen)
