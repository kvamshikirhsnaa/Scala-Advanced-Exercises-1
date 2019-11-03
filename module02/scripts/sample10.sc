// lower - bounds

abstract class Food extends Product with Serializable {
  val name: String
}

abstract class Fruit extends Food

case class Banana(name: String) extends Fruit
case class Apple(name: String) extends Fruit

abstract class Cereal extends Food

case class Muesli(name: String) extends Cereal
case class Granola(name: String) extends Cereal

val fuji = Apple("Fuji")
val alpen = Muesli("Alpen")


trait CombineWith[T] {
  val item: T
  def combineWith(other: T): T
}

case class CombineWithInt(item: Int) extends CombineWith[Int] {
   def combineWith(other: Int): Int = item + other
}

val cwi: CombineWith[Int] = CombineWithInt(10)
cwi.item
cwi.combineWith(5)



/* invariant behavior

val cwo: CombineWith[Any] = CombineWithInt(10)
cwo.combineWith("ten")

*/


// covariant

trait CombineWith2[+T] {
  val item: T
  def combineWith(another: T): T
}

case class CombineWithInt2(item: Int) extends CombineWith2[Int] {
  override def combineWith(other: Int): Int = item + other
}


val cwo: CombineWith2[Any] = CombineWithInt2(10)
cwo.combineWith("ten")

object CombineWithInt3 extends CombineWith[Int] {
  val item = 10
  def combineWith(other: Int): Int = item + other
}






